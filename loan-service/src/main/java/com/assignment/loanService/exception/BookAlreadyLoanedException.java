package com.assignment.loanService.exception;

public class BookAlreadyLoanedException extends RuntimeException {

    public BookAlreadyLoanedException(String message) {
        super(message);
    }
}
