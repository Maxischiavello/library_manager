package com.java.library.service;

import com.java.library.exception.BookNotFoundException;
import com.java.library.helper.BookTestHelper;
import com.java.library.repository.BookRepository;
import com.java.library.model.Book;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        List<Book> mockBooks = BookTestHelper.createMockBooks();

        when(bookRepository.findAll()).thenReturn(mockBooks);

        List<Book> result = bookService.getAllBooks();
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("The Great Gatsby", result.get(0).getTitle());
        assertEquals("1984", result.get(1).getTitle());
        assertEquals("Great Expectations", result.get(2).getTitle());
    }

    @Test
    void testGetBook() {
        List<Book> mockBooks = BookTestHelper.createMockBooks();
        Book book = mockBooks.get(0);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book result = bookService.getBook(1L).orElse(null);
        assertNotNull(result);
        assertEquals("The Great Gatsby", result.getTitle());
        assertEquals("F. Scott Fitzgerald", result.getAuthor());
        assertEquals("Classic", result.getCategory());
        assertEquals(true, result.getAvailability());
    }

    @Test
    void testSearchBooksByTitle() {
        String title = "Great";
        List<Book> mockBooks = BookTestHelper.createMockBooks();

        when(bookRepository.findByTitleContaining(title)).thenReturn(mockBooks.subList(0, 2));

        List<Book> result = bookService.searchBooksByTitle(title);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("The Great Gatsby", result.get(0).getTitle());
        assertEquals("1984", result.get(1).getTitle());
    }

    @Test
    void testUpdateBook() {
        Long bookId = 1L;
        Book existingBook = BookTestHelper.createBook(bookId, "The Great Gatsby", "F. Scott Fitzgerald", "Classic", true);
        Book updatedBook = BookTestHelper.createBook(bookId, "1984", "George Orwell", "Dystopian", true);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(existingBook)).thenReturn(updatedBook);

        Book result = bookService.updateBook(bookId, updatedBook);

        assertNotNull(result);
        assertEquals("1984", result.getTitle());
        assertEquals("George Orwell", result.getAuthor());
        assertEquals("Dystopian", result.getCategory());
        assertEquals(true, result.getAvailability());

        verify(bookRepository).save(existingBook);
    }

    @Test
    void testUpdateBookNotFound() {
        Long bookId = 1L;
        Book updatedBook = BookTestHelper.createBook(bookId, "1984", "George Orwell", "Dystopian", true);

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> {
            bookService.updateBook(bookId, updatedBook);
        });
    }

    @Test
    void testDeleteBook() {
        Long bookId = 1L;
        Book existingBook = BookTestHelper.createBook(bookId, "The Great Gatsby", "F. Scott Fitzgerald", "Classic", true);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        doNothing().when(bookRepository).delete(existingBook);

        bookService.deleteBook(bookId);

        verify(bookRepository).delete(existingBook);
    }

    @Test
    void testDeleteBookNotFound() {
        Long bookId = 1L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> {
            bookService.deleteBook(bookId);
        });
    }
}
