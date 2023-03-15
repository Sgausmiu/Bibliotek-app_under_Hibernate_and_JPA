package ru.samara.bibliotek.services;

//import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.samara.bibliotek.models.Book;
import ru.samara.bibliotek.models.Person;
import ru.samara.bibliotek.repositories.PersonRepository;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {

        this.personRepository = personRepository;
    }

    //вывести читателя
    public Optional<Person> findByFullName(String fullname){
        return personRepository.findByFullName(fullname);
    }
    //получить читателей
    public List<Person> findALL(){
        return personRepository.findAll();
    }

    //получить одного читателя
    public Person findOne(int id) {
        Optional<Person> foundPerson = personRepository.findById(id);
        return foundPerson.orElse(null);
    }
    @Transactional
    public void save (Person person) {
         personRepository.save(person);
    }

    @Transactional
    public void update (int id, Person updatedPerson){
        updatedPerson.setId(id);
        personRepository.save(updatedPerson);
    }

    @Transactional
    public void  delete(int id) {
        personRepository.deleteById(id);
    }

    //получение человека с книгой, с проверкой книги на просрочку > 10 суток
    //Hibernate.initialize раскомментировать для Lazy load
    public List<Book> getBooksByPersonId(int id) {
        Optional<Person> getPerson = personRepository.findById(id);

        if(getPerson.isPresent()){
            /*Hibernate.initialize*/getPerson.get().getBooks();

            getPerson.get().getBooks().forEach(book -> {
                long millSecond = Math.abs(book.getTakenAt().getTime() - new Date().getTime());//получим время выдачи книги
                //864000000 - 10 суток по ТЗ
                if (millSecond > 864000000)
                    book.setExpired(true); //флаг, что не сдал после 10 суток книгу
            });

            return getPerson.get().getBooks();
        }
        else{
            return Collections.emptyList();
        }
    }
}
