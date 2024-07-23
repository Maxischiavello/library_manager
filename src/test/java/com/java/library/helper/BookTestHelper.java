package com.java.library.helper;

import com.java.library.model.Book;

import java.util.Arrays;
import java.util.List;

public class BookTestHelper {

    public static Book createBook(Long id, String title, String author, String category, boolean availability) {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setCategory(category);
        book.setAvailability(availability);
        return book;
    }

    public static List<Book> createMockBooks() {
        Book book1 = createBook(1L, "The Great Gatsby", "F. Scott Fitzgerald", "Classic", true);
        Book book2 = createBook(2L, "1984", "George Orwell", "Dystopian", true);
        Book book3 = createBook(3L, "Great Expectations", "Charles Dickens", "Classic", true);
        return Arrays.asList(book1, book2, book3);
    }
}
