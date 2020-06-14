package com.ednach.dto.responce;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * DTO (Data transfer object) class that serves as the body for the response to exception
 */
@Setter
@Getter
public class ErrorResponseDto {

    private HttpStatus httpStatus;

    private String message;

    public ErrorResponseDto() {
    }

    public ErrorResponseDto(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
