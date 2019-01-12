package com.archtiger.exam.web;

import com.archtiger.exam.ExamError;
import com.archtiger.exam.ExamException;
import com.archtiger.exam.contract.ExamErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExamExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ExamException.class)
    public ResponseEntity<Object> handleExamException(final ExamException ex,
                                                      final WebRequest request) {
        ExamError error = ex.getExamError();
        ExamErrorResponse response = new ExamErrorResponse(LocalDateTime.now(),
                error.getHttpStatus(),
                error.getCode(),
                error.getDescription(),
                "Error occurred.");
        return handleExceptionInternal(ex, response, new HttpHeaders(), error.getHttpStatus(), request);
    }

}
