package com.java.library.exception;


public class BookUpdateException extends RuntimeException {
    public BookUpdateException(String message) {
        super(message);
    }
}