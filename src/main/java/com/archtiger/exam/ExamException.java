package com.archtiger.exam;

public class ExamException extends RuntimeException {

    private final ExamError examError;

    public ExamException(ExamError examError, String message) {
        super(message);
        this.examError = examError;
    }

    public ExamException(ExamError examError, Throwable cause) {
        super(cause);
        this.examError = examError;
    }

    public ExamError getExamError() {
        return examError;
    }

}
