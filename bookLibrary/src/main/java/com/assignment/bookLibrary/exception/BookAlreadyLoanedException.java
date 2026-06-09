package com.assignment.bookLibrary.exception;

public class BookAlreadyLoanedException extends RuntimeException {

    public BookAlreadyLoanedException(String message) {
        super(message);
    }
}
