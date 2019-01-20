package com.archtiger.exam.web;

import com.archtiger.exam.ExamError;
import com.archtiger.exam.ExamException;
import com.archtiger.exam.contract.ExamErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
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
                "Service runtime exception occurred. see the examErrorCode.",
                request.getDescription(false));
        return handleExceptionInternal(ex, response, new HttpHeaders(), error.getHttpStatus(), request);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                   WebRequest request) {
        ExamError examError = ExamError.INVALID_PARAMETER;
        String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();
        ExamErrorResponse response = new ExamErrorResponse(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                examError.getCode(),
                ex.getLocalizedMessage(),
                error,
                request.getDescription(false));
        return handleExceptionInternal(ex, response, new HttpHeaders(), response.getHttpStatus(), request);
    }


}
