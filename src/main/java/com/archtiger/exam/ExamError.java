package com.archtiger.exam;

import org.springframework.http.HttpStatus;

public enum ExamError {

    INVALID_PARAMETER (HttpStatus.BAD_REQUEST, 40000, "Bad request parameter."),

    INTERNAL_SERVER_ERROR (HttpStatus.INTERNAL_SERVER_ERROR, 50001, "Internal server error.");

    private HttpStatus httpStatus;

    private int code;

    private String description;

    ExamError(HttpStatus httpStatus, int code, String description) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.description = description;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
