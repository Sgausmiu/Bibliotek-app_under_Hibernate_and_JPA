package ru.samara.bibliotek.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    //поиск книг по начальным буквам
    public List<Book> searchByQuery(String query){
        return bookRepository.findByNamedStartingWith(query);
    }

    //поиск книг сортированный
    public List<Book> findALL (boolean sortByYearBook) {
        if (sortByYearBook)
            return bookRepository.findAll(Sort.by("yearbook"));
        else
            return bookRepository.findAll();
    }

    //пагинация страниц сортированных книг
    public List<Book> findWithPagination(Integer page, Integer booksPerPage, boolean sortByYearBook) {
        if(sortByYearBook)
            return bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("yearbook"))).getContent();
        else
            return bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();

    }

    //найти одну книгу
    public Book findOne (int id) {
        Optional<Book> foundBook = bookRepository.findById(id);
        return foundBook.orElse(null);
    }
    //eager загрузка, без hibernate.initialize() получить читателя книги
    public Person getBookOwner(int id){
        return bookRepository.findById(id).map(Book::getOwner).orElse(null);
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
