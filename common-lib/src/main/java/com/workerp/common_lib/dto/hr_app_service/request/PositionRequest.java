package com.workerp.common_lib.dto.hr_app_service.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PositionRequest {
    @NotBlank
    private String name;
    private String description;
}
