package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private Integer age;
    private String gender;
    private Double salary;


    public User() {
    }

    public User(String name, String surname, Integer age, String gender, Double salary) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public Double getSalary() {
        return salary;
    }

    public Integer getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

}
