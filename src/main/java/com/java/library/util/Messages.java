package com.java.library.util;

public class Messages {

    private static final String USER_NOT_FOUND = "User not found with id: ";
    private static final String BOOK_NOT_FOUND = "Book not found with id: ";
    private static final String LOAN_NOT_FOUND = "Loan not found with id: ";

    public Messages() {
    }

    public static String USER_NOT_FOUND() {
        return USER_NOT_FOUND;
    }

    public static String BOOK_NOT_FOUND() {
        return BOOK_NOT_FOUND;
    }

    public static String LOAN_NOT_FOUND() {
        return LOAN_NOT_FOUND;
    }

}
