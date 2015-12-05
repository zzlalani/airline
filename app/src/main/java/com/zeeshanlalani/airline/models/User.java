package com.zeeshanlalani.airline.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzlal on 12/1/2015.
 */
public class User {

    String id;
    String firstName;
    String lastName;
    String username;
    String password;

    public User(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public User(String id, String firstName, String lastName, String username, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String toPostData () {
        String params = "";

        params += "firstName="+firstName;
        params += "&lastName="+lastName;
        params += "&username="+username;
        params += "&password="+password;

        if (id != null) {
            params += "&id="+id;
        }

        return params;
    }
}
