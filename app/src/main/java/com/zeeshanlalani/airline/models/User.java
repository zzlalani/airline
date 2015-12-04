package com.zeeshanlalani.airline.models;

import java.util.Date;

/**
 * Created by zzlal on 12/1/2015.
 */
public class User {

    String id;
    String firstName;
    String lastName;
    String email;
    String password;
    Date date;

    public User(String id, String firstName, String lastName, String email, String password, Date date) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
