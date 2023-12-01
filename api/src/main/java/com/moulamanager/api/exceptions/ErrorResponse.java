package com.moulamanager.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class ErrorResponse {
    private Date timestamp;
    private String message;
    private String details;
}
