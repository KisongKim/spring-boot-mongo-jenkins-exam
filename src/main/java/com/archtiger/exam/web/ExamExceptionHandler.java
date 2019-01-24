package com.archtiger.exam.web;

import com.archtiger.exam.ExamError;
import com.archtiger.exam.ExamException;
import com.archtiger.exam.contract.ExamErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExamExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ExamException.class)
    public ResponseEntity<Object> handleExamException(final ExamException ex,
                                                      final WebRequest request) {
        ExamError examError = ex.getExamError();
        ExamErrorResponse response = examErrorResponse(
                examError,
                ex.getLocalizedMessage(),
                "Service runtime exception occurred. see the examErrorCode.",
                request.getDescription(false));
        return handleExceptionInternal(ex, response, new HttpHeaders(), examError.getHttpStatus(), request);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                   WebRequest request) {
        ExamError examError = ExamError.INVALID_PARAMETER;
        String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();
        ExamErrorResponse response = examErrorResponse(
                examError,
                ex.getLocalizedMessage(),
                error,
                request.getDescription(false));
        return handleExceptionInternal(ex, response, new HttpHeaders(), response.getHttpStatus(), request);
    }

    // This exception is thrown when argument annotated with @Valid failed validation
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ExamError examError = ExamError.INVALID_PARAMETER;
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        ExamErrorResponse response = examErrorResponse(
                examError,
                ex.getLocalizedMessage(),
                errors,
                request.getDescription(false));
        return handleExceptionInternal(ex, response, new HttpHeaders(), response.getHttpStatus(), request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex,
                                                            WebRequest request) {
        ExamError examError = ExamError.INVALID_PARAMETER;
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " +
                    violation.getPropertyPath() + ": " + violation.getMessage());
        }
        ExamErrorResponse response = examErrorResponse(
                examError,
                ex.getLocalizedMessage(),
                errors,
                request.getDescription(false));
        return handleExceptionInternal(ex, response, new HttpHeaders(), response.getHttpStatus(), request);
    }

    private ExamErrorResponse examErrorResponse(ExamError examError,
                                                String localizedMessage,
                                                String error,
                                                String description) {
        return new ExamErrorResponse(LocalDateTime.now(),
                examError.getHttpStatus(),
                examError.getCode(),
                localizedMessage,
                error,
                description);
    }

    private ExamErrorResponse examErrorResponse(ExamError examError,
                                                String localizedMessage,
                                                List<String> errors,
                                                String description) {
        return new ExamErrorResponse(LocalDateTime.now(),
                examError.getHttpStatus(),
                examError.getCode(),
                localizedMessage,
                errors,
                description);
    }

}
