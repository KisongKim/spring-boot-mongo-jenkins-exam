package com.archtiger.exam.contract;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Error response.
 */
@JsonAutoDetect
@NoArgsConstructor
@ToString
@Getter
public class ExamErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Asia/Seoul")
    @JsonProperty(required = true)
    private LocalDateTime timestamp;

    @JsonProperty(required = true)
    private HttpStatus httpStatus;

    @JsonProperty(required = true, value = "examErrorCode")
    private int code;

    private String message;

    private List<String> errors;

    private String path;

    public ExamErrorResponse(LocalDateTime timestamp,
                             HttpStatus httpStatus,
                             int code,
                             String message,
                             List<String> errors,
                             String path) {
        this.timestamp = timestamp;
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
        this.errors = errors;
        this.path = path;
    }

    public ExamErrorResponse(LocalDateTime timestamp,
                             HttpStatus httpStatus,
                             int code,
                             String message,
                             String error,
                             String path) {
        this.timestamp = timestamp;
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
        this.errors = Arrays.asList(error);
        this.path = path;
    }

}
