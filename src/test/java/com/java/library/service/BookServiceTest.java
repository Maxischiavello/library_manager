package com.java.library.service;

import com.java.library.repository.BookRepository;
import com.java.library.model.Book;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void testGetAllBooks() {
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("The Great Gatsby");
        book1.setAuthor("F. Scott Fitzgerald");
        book1.setCategory("Classic");
        book1.setAvailability(true);

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("1984");
        book2.setAuthor("George Orwell");
        book2.setCategory("Dystopian");
        book2.setAvailability(true);

        List<Book> mockBooks = Arrays.asList(book1, book2);

        when(bookRepository.findAll()).thenReturn(mockBooks);

        List<Book> result = bookService.getAllBooks();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("The Great Gatsby", result.get(0).getTitle());
        assertEquals("1984", result.get(1).getTitle());
    }

    @Test
    void testGetBook() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("The Great Gatsby");
        book.setAuthor("F. Scott Fitzgerald");
        book.setCategory("Classic");
        book.setAvailability(true);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book result = bookService.getBook(1L).orElse(null);
        assertNotNull(result);
        assertEquals("The Great Gatsby", result.getTitle());
        assertEquals("F. Scott Fitzgerald", result.getAuthor());
        assertEquals("Classic", result.getCategory());
        assertEquals(true, result.getAvailability());
    }
}
