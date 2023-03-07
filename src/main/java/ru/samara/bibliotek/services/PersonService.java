package ru.samara.bibliotek.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.samara.bibliotek.models.Person;
import ru.samara.bibliotek.repositories.PersonRepository;

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

    public Optional<Person> findByFullName(String fullname){
        return personRepository.findByFullName(fullname);
    }

}
