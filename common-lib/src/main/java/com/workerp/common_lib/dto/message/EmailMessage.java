package com.workerp.common_lib.dto.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class EmailMessage {
    private String to;
    private String type;
    private Map<String, String> values;

    // Default constructor
    public EmailMessage() {}

    // Constructor with parameters
    @JsonCreator
    public EmailMessage(
            @JsonProperty("to") String to,
            @JsonProperty("type") String type,
            @JsonProperty("values") Map<String, String> values) {
        this.to = to;
        this.type = type;
        this.values = values;
    }
}