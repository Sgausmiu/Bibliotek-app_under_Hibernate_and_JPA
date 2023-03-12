package ru.samara.bibliotek.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "named")
    @NotEmpty(message = "Should not be empty")
    @Size(min = 2, max = 200, message = "2<Lenght<100 ")
    private String named;

    @Column(name = "author")
    @NotEmpty(message = "name authors should not be empty")
    @Size(min = 2, max = 130, message = "2<Lenght<30 ")
    private String author;
    //@NotEmpty(message = "Year of the Book should not be empty")
    @Min(value = 1900, message = "Min year for the book")
    @Column(name = "yearbook")
    private int yearBook;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;

    @Transient//чтоб hibernate не видел этого поля
    public boolean expired;


    //Конструктор по умолчанию нужен для Spring
    public Book() {

    }
    public Book(int id, String named, String author, int yearBook){
        this.id=id;
        this.named=named;
        this.author=author;
        this.yearBook=yearBook;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamed() {
        return named;
    }

    public void setNamed(String named) {
        this.named = named;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearBook() {
        return yearBook;
    }

    public void setYearBook(int yearBook) {
        this.yearBook = yearBook;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Date getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}
