package com.example.review.exception;

public class ReviewRegistrationException extends RuntimeException {


    public ReviewRegistrationException(Throwable cause) {
        super(cause);
    }

    public ReviewRegistrationException(String message) {
        super(message);
    }
}
