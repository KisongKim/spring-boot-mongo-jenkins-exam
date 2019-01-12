package com.archtiger.exam;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class ExamExceptionMatcher extends TypeSafeMatcher<ExamException> {

    private int foundErrorCode;

    private int expectedErrorCode;

    // private constructor
    private ExamExceptionMatcher(final int expectedErrorCode) {
        this.expectedErrorCode = expectedErrorCode;
    }

    public static ExamExceptionMatcher is(final int expectedErrorCode) {
        return new ExamExceptionMatcher(expectedErrorCode);
    }

    @Override
    protected boolean matchesSafely(final ExamException exception) {
        this.foundErrorCode = exception.getExamError().getCode();
        return this.foundErrorCode == expectedErrorCode;
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(foundErrorCode).appendText(" was found instead of ").appendValue(expectedErrorCode);
    }

}
