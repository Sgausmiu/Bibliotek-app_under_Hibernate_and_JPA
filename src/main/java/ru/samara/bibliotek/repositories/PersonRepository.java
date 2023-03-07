package ru.samara.bibliotek.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.samara.bibliotek.models.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository <Person, Integer> {
    Optional<Person> findByFullName(String fullname);
    List<Person> findByFullNameOrderByYearBirth(String fullname);

}
