package com.workerp.common_lib.dto.hr_app_service.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
public class PositionResponse {
    private String id;
    private String name;
    private String description;
}
