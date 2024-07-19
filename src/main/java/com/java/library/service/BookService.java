package com.java.library.service;

import com.java.library.model.Book;
import com.java.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
//
//    public List<BookDTO> getAllBooks() {
//        return bookRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
//    }
//
//    public List<BookDTO> searchBooksByTitle(String title) {
//        return bookRepository.findByTitleContaining(title).stream().map(this::convertToDTO).collect(Collectors.toList());
//    }
//
//    public List<BookDTO> searchBooksByAuthor(String author) {
//        return bookRepository.findByAuthorContaining(author).stream().map(this::convertToDTO).collect(Collectors.toList());
//    }
//
//    private Book convertToEntity(BookDTO bookDTO) {
//        Book book = new Book();
//        book.setId(bookDTO.getId());
//        book.setTitle(bookDTO.getTitle());
//        book.setAuthor(bookDTO.getAuthor());
//        book.setCategory(bookDTO.getCategory());
//        book.setAvailability(bookDTO.getAvailability());
//        return book;
//    }
//
//    private BookDTO convertToDTO(Book book) {
//        BookDTO bookDTO = new BookDTO();
//        bookDTO.setId(book.getId());
//        bookDTO.setTitle(book.getTitle());
//        bookDTO.setAuthor(book.getAuthor());
//        bookDTO.setCategory(book.getCategory());
//        bookDTO.setAvailability(book.getAvailability());
//        return bookDTO;
//    }
}
