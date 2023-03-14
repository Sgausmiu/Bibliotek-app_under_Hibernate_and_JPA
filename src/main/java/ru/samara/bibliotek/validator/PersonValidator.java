package ru.samara.bibliotek.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.samara.bibliotek.models.Person;
import ru.samara.bibliotek.services.PersonService;

@Component
public class PersonValidator implements Validator {

    private final PersonService personService;

    @Autowired
    public PersonValidator(PersonService personService) {
        this.personService = personService;
    }
    @Override
    public boolean supports(Class<?> suppClass) {
        return Person.class.equals(suppClass);
    }
    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        if (personService.findByFullName(person.getFullName()).isPresent())
            errors.rejectValue("fullName", "", "Найден читатель с таким ФИО! Не создан.");
    }
}
