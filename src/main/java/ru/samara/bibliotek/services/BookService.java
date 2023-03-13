package ru.samara.bibliotek.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.samara.bibliotek.models.Book;
import ru.samara.bibliotek.models.Person;
import ru.samara.bibliotek.repositories.BookRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> searchByQuery(String query){
        return bookRepository.findByNamedStartingWith(query);
    }
    public List<Book> findALL (boolean sortByYearBook) {
        if (sortByYearBook)
            return bookRepository.findAll(Sort.by("yearbook"));
        else
            return bookRepository.findAll();
    }

    public Book findOne (int id) {
        Optional<Book> foundBook = bookRepository.findById(id);
        return foundBook.orElse(null);
    }
    public Person getBookOwner(int id){
        return bookRepository.findById(id).map(Book::getOwner).orElse(null);//eager загрузка, без hibernate.initialize()
    }
    @Transactional
    public void save (Book book) {
        bookRepository.save(book);
    }
    @Transactional
    public void update(int id, Book updatedBook) {
        Book toBeUpdatedBook = (Book) bookRepository.findById(id).get();
        //использую save(), метода нет в persistence context
        updatedBook.setId(id);
        updatedBook.setAuthor(toBeUpdatedBook.getAuthor());//чтоб не терялась связь при обновлении
        bookRepository.save(updatedBook);

    }
    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }
    //возвращение книги в библиотеку
    @Transactional
    public void toFree (int id) {
        bookRepository.findById(id).ifPresent(book -> {
            book.setOwner(null);
            book.setTakenAt(null);
        });
    }
    //взятие книги из библиотеки
    @Transactional
    public  void toAppoint (int id, Person selectedPerson) {
        bookRepository.findById(id).ifPresent(book  -> {
                    book.setOwner(selectedPerson);
                    book.setTakenAt(new Date());
                });
    }




}
