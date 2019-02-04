package com.archtiger.exam;

import org.springframework.http.HttpStatus;

public enum ExamError {

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, 40000, "Bad request parameter or validation error"),

    STOCK_COUNT_MISMATCH(HttpStatus.BAD_REQUEST, 40001, "Your inventory stock does not match the server."),

    EMAIL_ALREADY_EXIST(HttpStatus.BAD_REQUEST, 40002, "Your Email already registered."),

    NOT_FOUND(HttpStatus.NOT_FOUND, 40400, "Resource not found."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 50001, "Internal server error.");

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
