package com.workerp.util_service.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UploadService {
    private final Cloudinary cloudinary;

    @Value("${app.cloudinary.folder}")
    private String CLOUDINARY_FOLDER;

    // Determine folder for file storage
    private String getFolder(MultipartFile file) {
        String typeFolder = file.getContentType().startsWith("image/") ? "/images" : "/videos";
        return CLOUDINARY_FOLDER + typeFolder;
    }

    // Upload a single image
    public String uploadFile(MultipartFile image) throws IOException {
        String folder = getFolder(image);
        Map<String, Object> options = ObjectUtils.asMap("folder", folder);
        Map<String, Object> result = cloudinary.uploader().upload(image.getBytes(), options);
        return (String) result.get("secure_url");
    }

    // Upload multiple files
    public List<String> uploadFiles(MultipartFile[] images) throws IOException {
        List<String> urls = new ArrayList<>();
        for (MultipartFile image : images) {
            String folder = getFolder(image);
            Map<String, Object> options = ObjectUtils.asMap("folder", folder);
            Map<String, Object> result = cloudinary.uploader().upload(image.getBytes(), options);
            urls.add((String) result.get("secure_url"));
        }
        return urls;
    }

    // Upload a single video
    public String uploadVideo(MultipartFile video) throws IOException {
        String folder = getFolder(video);
        Map<String, Object> options = ObjectUtils.asMap("resource_type", "video", "folder", folder);
        Map<String, Object> uploadResult = cloudinary.uploader().upload(video.getBytes(), options);
        return (String) uploadResult.get("secure_url");
    }

    // Delete a file
    public void deleteFile(String url) throws IOException {
        String[] urlParts = url.split("/");
        if (urlParts.length < 5) {
            throw new IllegalArgumentException("Invalid URL format");
        }
        String fileName = urlParts[urlParts.length - 1]; // Get file name
        String type = urlParts[urlParts.length - 2]; // Get file type folder (images or videos)
        String publicId = CLOUDINARY_FOLDER + "/" + type + "/" + fileName.replaceFirst("\\.[^\\.]+$", ""); // Remove file extension

        if ("images".equals(type)) {
            System.out.println("Public ID to delete: " + publicId);
            Map<String, Object> result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            System.out.println("Delete result: " + result);
        } else if ("videos".equals(type)) {
            System.out.println("Public ID to delete: " + publicId);
            Map<String, Object> options = ObjectUtils.asMap("resource_type", "video");
            Map<String, Object> result = cloudinary.uploader().destroy(publicId, options);
            System.out.println("Delete result: " + result);
        } else {
            throw new IllegalArgumentException("Invalid file type: " + type);
        }
    }
}