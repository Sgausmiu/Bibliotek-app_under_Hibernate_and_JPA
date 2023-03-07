package ru.samara.bibliotek.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.samara.bibliotek.models.Book;
import ru.samara.bibliotek.models.Person;
import ru.samara.bibliotek.repositories.BookRepository;

import java.util.List;

@Service
@Transactional
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findByNamed(String named){
        return bookRepository.findByNamed(named);
    }

}
