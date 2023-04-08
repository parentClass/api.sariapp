package com.dnc.sariapi.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SariExceptionResponse {
    private Integer status;
    private String error;
    private String message;
    private String timestamp;
}
