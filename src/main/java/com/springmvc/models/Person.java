package com.springmvc.models;

import javax.validation.constraints.*;

public class Person {
    private int id;

    @NotEmpty(message = "Not empty")
    @Size(min = 2, max = 32, message = "Name length between 2 to 32")
    private String name;

    @Email(message = "Not correct email")
    @NotEmpty(message = "Not empty email")
    private String email;

    @Min(value = 10, message = "Age > 10")
    @Max(value = 120, message = "Age < 120")
    private int age;

    public Person() {
    }

    public Person(int id, String name, String email, int age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
