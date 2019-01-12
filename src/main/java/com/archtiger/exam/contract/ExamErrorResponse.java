package com.archtiger.exam.contract;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Error response.
 */
@JsonAutoDetect
public class ExamErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Asia/Seoul")
    @JsonProperty(required = true)
    private final LocalDateTime timestamp;

    @JsonProperty(required = true)
    private final HttpStatus httpStatus;

    @JsonProperty(required = true)
    private final int code;

    private final String message;

    private final List<String> errors;

    public ExamErrorResponse(LocalDateTime timestamp, HttpStatus httpStatus, int code, String message, List<String> errors) {
        this.timestamp = timestamp;
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    public ExamErrorResponse(LocalDateTime timestamp, HttpStatus httpStatus, int code, String message, String error) {
        this.timestamp = timestamp;
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
        this.errors = Arrays.asList(error);
    }

}
