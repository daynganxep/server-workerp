package com.workerp.common_lib.dto.jwt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JWTPayload {
    String id;
    String scope;

    @JsonCreator
    public JWTPayload(@JsonProperty("id") String id, @JsonProperty("scope") String scope) {
        this.id = id;
        this.scope = scope;
    }
}
