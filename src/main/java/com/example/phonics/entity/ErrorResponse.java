package com.example.phonics.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ErrorResponse
{
    private String message;
    private int code;

    public ErrorResponse(String message, int code) {
        this.message = message;
        this.code = code;
    }
}
