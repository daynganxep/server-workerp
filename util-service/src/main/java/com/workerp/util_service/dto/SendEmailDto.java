package com.workerp.util_service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SendEmailDto {
    private String to;
    private String subject;
    private String text;
}