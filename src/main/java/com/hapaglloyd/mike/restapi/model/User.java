package com.hapaglloyd.mike.restapi.model;


import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("USERS")
public class User {

    @Id
    private int id;

    @NotEmpty
    private String username;
    private String gender;
    private int age;
    private LocalDateTime timestamp;

    public User() {}

    public User(int id, String username, String gender, int age, LocalDateTime timestamp) {
        this.timestamp = timestamp;
        this.age = age;
        this.gender = gender;
        this.username = username;
        this.id = id;
    }

    public User(int id, String username, String gender, int age) {
        this.id = id;
        this.username = username;
        this.gender = gender;
        this.age = age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
