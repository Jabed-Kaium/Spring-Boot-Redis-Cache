package com.example.rediscache.services;

import com.example.rediscache.models.Book;
import com.example.rediscache.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @CacheEvict(value = "books", allEntries = true)
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Cacheable(value = "books", key = "#id")
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Cacheable(value = "books")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Caching(
            evict = @CacheEvict(value = "books", allEntries = true),
            put = @CachePut(value = "books", key = "#book.id")
    )
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    @Caching(
            evict = {@CacheEvict(value = "books", allEntries = true), @CacheEvict(value = "books", key = "#id")}
    )
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
