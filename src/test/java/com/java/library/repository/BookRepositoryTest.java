package com.java.library.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.java.library.model.Book;
import com.java.library.repository.BookRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testFindByTitleContaining() {
        Book book = new Book();
        book.setTitle("The Great Gatsby");
        book.setAuthor("F. Scott Fitzgerald");
        book.setCategory("Classic");

        bookRepository.save(book);

        List<Book> books = bookRepository.findByTitleContaining("Gatsby");
        assertThat(books).isNotEmpty();
        assertThat(books.get(0).getTitle()).isEqualTo("The Great Gatsby");
    }

    @Test
    public void testFindByAuthorContaining() {
        Book book = new Book();
        book.setTitle("1984");
        book.setAuthor("George Orwell");
        book.setCategory("Dystopian");

        bookRepository.save(book);

        List<Book> books = bookRepository.findByAuthorContaining("Orwell");
        assertThat(books).isNotEmpty();
        assertThat(books.get(0).getAuthor()).isEqualTo("George Orwell");
    }

    @Test
    public void testFindByCategoryContaining() {
        Book book = new Book();
        book.setTitle("Brave New World");
        book.setAuthor("Aldous Huxley");
        book.setCategory("Dystopian");

        bookRepository.save(book);

        List<Book> books = bookRepository.findByCategoryContaining("Dystopian");
        assertThat(books).isNotEmpty();
        assertThat(books.get(0).getCategory()).isEqualTo("Dystopian");
    }
}
