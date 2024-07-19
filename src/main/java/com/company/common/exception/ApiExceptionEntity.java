package com.company.common.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ApiExceptionEntity {
    private String errorMessage;

    @Builder
    public ApiExceptionEntity(String errorMessage){
        this.errorMessage = errorMessage;
    }
}