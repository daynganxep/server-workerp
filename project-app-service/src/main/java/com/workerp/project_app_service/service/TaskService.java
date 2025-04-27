package com.workerp.project_app_service.service;

import com.workerp.common_lib.dto.project_app_service.request.AssigneeUpdateTaskRequest;
import com.workerp.common_lib.dto.project_app_service.request.CommentRequest;
import com.workerp.common_lib.dto.project_app_service.request.TaskRequest;
import com.workerp.common_lib.dto.project_app_service.response.CommentResponse;
import com.workerp.common_lib.dto.project_app_service.response.TaskResponse;
import com.workerp.common_lib.enums.project_app_service.TaskPriority;
import com.workerp.common_lib.enums.project_app_service.TaskStatus;
import com.workerp.common_lib.exception.AppException;
import com.workerp.common_lib.util.SecurityUtil;
import com.workerp.project_app_service.mapper.TaskMapper;
import com.workerp.project_app_service.model.Comment;
import com.workerp.project_app_service.model.Task;
import com.workerp.project_app_service.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskResponse createTask(String projectId, TaskRequest request) {
        Task task = taskMapper.toTask(request);
        task.setProjectId(projectId);
        task.setStatus(TaskStatus.TO_DO);
        taskRepository.save(task);
        return taskMapper.toTaskResponse(task);
    }

    public TaskResponse updateTask(String taskId, TaskRequest request) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Task not found", "project_app-task-f-02-01"));
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setStartDate(request.getStartDate());
        task.setDueDate(request.getDueDate());
        task.setAssignees(request.getAssignees());
        task.setTags(request.getTags());
        taskRepository.save(task);
        return taskMapper.toTaskResponse(task);
    }

    public void deleteTask(String taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Task not found", "project_app-task-f-03-01"));
        taskRepository.delete(task);
    }

    public List<TaskResponse> getTasksByProjectId(String projectId, TaskStatus status, TaskPriority priority, String sortBy, String order) {
        sortBy = sortBy != null ? sortBy : "dueDate";
        order = order != null ? order : "asc";
        Sort sort = Sort.by(order.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        List<Task> rootTasks = taskRepository.findByProjectIdAndParentTaskIdIsNull(projectId, sort);
        List<TaskResponse> taskResponses = buildTaskTree(rootTasks, sort, projectId);

        if (status != null) {
            taskResponses = filterByStatus(taskResponses, status);
        }
        if (priority != null) {
            taskResponses = filterByPriority(taskResponses, priority);
        }
        return taskResponses;
    }

    public List<TaskResponse> getMyTasks(String projectId, String sortBy, String order) {
        String employeeId = SecurityUtil.getEmployeeId();
        sortBy = sortBy != null ? sortBy : "dueDate";
        order = order != null ? order : "asc";
        Sort sort = Sort.by(order.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        List<Task> rootTasks = taskRepository.findByProjectIdAndAssigneesContainingAndParentTaskIdIsNull(projectId, employeeId, sort);
        return buildTaskTree(rootTasks, sort, null);
    }

    public TaskResponse updateTaskStatus(String taskId, TaskStatus status) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Task not found", "project_app-task-f-06-01"));
        String currentEmployeeId = SecurityUtil.getEmployeeId();
        if(task.getAssignees() == null){
            task.setAssignees(List.of());
        }
        boolean isAssignee = task.getAssignees().contains(currentEmployeeId);
        if (!isAssignee) {
            throw new AppException(HttpStatus.FORBIDDEN, "You can only update status of your tasks", "project_app-task-f-06-02");
        }
        task.setStatus(status);
        taskRepository.save(task);
        return taskMapper.toTaskResponse(task);
    }

    public TaskResponse getTaskById(String taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Task not found", "project_app-task-f-07-01"));
        TaskResponse taskResponse = taskMapper.toTaskResponse(task);
        if (task.getParentTaskId() == null) {
            Sort sort = Sort.by(Sort.Direction.ASC, "dueDate");
            List<Task> subTasks = taskRepository.findByParentTaskId(taskId, sort);
            taskResponse.setSubTasks(taskMapper.toTaskResponseList(subTasks));
        }
        return taskResponse;
    }

    public TaskResponse createSubtask(String taskId, TaskRequest request) {
        Task parentTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Task not found", "project_app-task-f-08-01"));
        String currentEmployeeId = SecurityUtil.getEmployeeId();
        boolean isAssignee = parentTask.getAssignees().contains(currentEmployeeId);
        if (!isAssignee) {
            throw new AppException(HttpStatus.FORBIDDEN, "You can only create subtasks for your tasks", "project_app-task-f-08-02");
        }
        if (parentTask.getParentTaskId() != null) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Subtasks can only be created for root tasks (2 levels max)", "project_app-task-f-08-03");
        }
        Task subtask = taskMapper.toTask(request);
        subtask.setParentTaskId(taskId);
        subtask.setProjectId(parentTask.getProjectId());
        subtask.setStatus(TaskStatus.TO_DO);
        taskRepository.save(subtask);
        return taskMapper.toTaskResponse(subtask);
    }

    private List<TaskResponse> buildTaskTree(List<Task> rootTasks, Sort sort, String projectId) {
        List<TaskResponse> taskResponses = taskMapper.toTaskResponseList(rootTasks);
        if (!rootTasks.isEmpty()) {
            List<Task> allSubTasks = projectId != null
                    ? taskRepository.findByProjectIdAndParentTaskIdIsNotNull(projectId, sort)
                    : taskRepository.findByParentTaskIdIn(rootTasks.stream().map(Task::getId).toList(), sort);
            Map<String, List<Task>> subTaskMap = allSubTasks.stream()
                    .collect(Collectors.groupingBy(Task::getParentTaskId));

            for (TaskResponse taskResponse : taskResponses) {
                List<Task> subTasks = subTaskMap.getOrDefault(taskResponse.getId(), Collections.emptyList());
                taskResponse.setSubTasks(taskMapper.toTaskResponseList(subTasks));
            }
        }
        return taskResponses;
    }

    private List<TaskResponse> filterByStatus(List<TaskResponse> tasks, TaskStatus status) {
        return tasks.stream()
                .filter(t -> t.getStatus() == status)
                .peek(t -> t.setSubTasks(t.getSubTasks().stream().filter(st -> st.getStatus() == status).toList()))
                .toList();
    }

    private List<TaskResponse> filterByPriority(List<TaskResponse> tasks, TaskPriority priority) {
        return tasks.stream()
                .filter(t -> t.getPriority() == priority)
                .peek(t -> t.setSubTasks(t.getSubTasks().stream().filter(st -> st.getPriority() == priority).toList()))
                .toList();
    }

    public TaskResponse addDependency(String taskId, String dependencyId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Task not found", "project_app-task-f-09-01"));
        taskRepository.findById(dependencyId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Dependency task not found", "project_app-task-f-09-02"));
        if (!task.getDependencies().contains(dependencyId)) {
            task.getDependencies().add(dependencyId);
            taskRepository.save(task);
        }
        return taskMapper.toTaskResponse(task);
    }

    public TaskResponse removeDependency(String taskId, String dependencyId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Task not found", "project_app-task-f-10-01"));
        task.getDependencies().remove(dependencyId);
        taskRepository.save(task);
        return taskMapper.toTaskResponse(task);
    }

    public CommentResponse addComment(String taskId, CommentRequest request) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Task not found", "project_app-task-f-11-01"));
        Comment comment = Comment.builder()
                .id(UUID.randomUUID().toString())
                .content(request.getContent())
                .createdBy(SecurityUtil.getEmployeeId())
                .createdAt(new Date())
                .build();
        if (task.getComments() == null) {
            task.setComments(new ArrayList<>());
        }
        task.getComments().add(comment);
        taskRepository.save(task);
        return taskMapper.toCommentResponse(comment);
    }

    public CommentResponse updateComment(String taskId, String commentId, CommentRequest request) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Task not found", "project_app-task-f-12-01"));
        Comment comment = task.getComments().stream()
                .filter(c -> c.getId().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Comment not found", "project_app-task-f-12-02"));
        String currentEmployeeId = SecurityUtil.getEmployeeId();
        if (!comment.getCreatedBy().equals(currentEmployeeId)) {
            throw new AppException(HttpStatus.FORBIDDEN, "You can only edit your comments", "project_app-task-f-12-03");
        }
        comment.setContent(request.getContent());
        taskRepository.save(task);
        return taskMapper.toCommentResponse(comment);
    }

    public void deleteComment(String taskId, String commentId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Task not found", "project_app-task-f-13-01"));
        Comment comment = task.getComments().stream()
                .filter(c -> c.getId().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Comment not found", "project_app-task-f-13-02"));
        String currentEmployeeId = SecurityUtil.getEmployeeId();
        if (!comment.getCreatedBy().equals(currentEmployeeId)) {
            throw new AppException(HttpStatus.FORBIDDEN, "You can only delete your comments", "project_app-task-f-13-03");
        }
        task.getComments().removeIf(c -> c.getId().equals(commentId));
        taskRepository.save(task);
    }

    public void assigneeUpdateTask(String taskId, AssigneeUpdateTaskRequest request) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Task not found", "project_app-task-f-14-01"));
        taskMapper.assignUpdateTask(request, task);
        taskRepository.save(task);
    }
}