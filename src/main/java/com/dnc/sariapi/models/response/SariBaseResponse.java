package com.dnc.sariapi.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SariBaseResponse {
    private Integer status;
    private Object body;
    @Builder.Default
    private String timestamp = Instant.now().toString();
}