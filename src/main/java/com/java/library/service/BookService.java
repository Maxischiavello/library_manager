package com.java.library.service;

import com.java.library.exception.BookNotFoundException;
import com.java.library.model.Book;
import com.java.library.repository.BookRepository;
import com.java.library.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    private final Messages messages = new Messages();

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBook(Long id) {
        return bookRepository.findById(id);
    }

    public List<Book> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContaining(title);
    }

    public List<Book> searchBooksByAuthor(String author) {
        return bookRepository.findByAuthorContaining(author);
    }

    public List<Book> searchBooksByCategory(String category) {
        return bookRepository.findByCategoryContaining(category);
    }

    public Book updateBook(Long id, Book updatedBook) {
        return bookRepository.findById(id).map(book -> {
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setCategory(updatedBook.getCategory());
            book.setAvailability(updatedBook.getAvailability());

            return bookRepository.save(book);
        }).orElseThrow(() -> new BookNotFoundException(messages.BOOK_NOT_FOUND() + id));
    }

    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(messages.BOOK_NOT_FOUND() + id));
        bookRepository.delete(book);
    }
}
