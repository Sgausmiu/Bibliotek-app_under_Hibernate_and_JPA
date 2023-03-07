package ru.samara.bibliotek.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.samara.bibliotek.models.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository {
    List<Book> findByNamed(String named);
}
