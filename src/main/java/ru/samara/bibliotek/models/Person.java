package ru.samara.bibliotek.models;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "fullname")
    @NotEmpty(message = "Not empty, please")
    @Size(min = 1, max = 200, message = "between 1 and 200 characters")
    private String fullName;
    @Column(name = "yearbirth")
    @Size(min = 1920, max = 2100, message = "between 1920 and 2100 characters")
    private int yearBirth;


    //Конструктор Person по умолчанию для Spring

    public Person(){

    }
    public Person(int id, String fullName, int yearBirth){
        this.id = id;
        this.fullName=fullName;
        this.yearBirth=yearBirth;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYearBirth() {
        return yearBirth;
    }

    public void setYearBirth(int yearBirth) {
        this.yearBirth = yearBirth;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", yearBirth=" + yearBirth +
                '}';
    }
}
