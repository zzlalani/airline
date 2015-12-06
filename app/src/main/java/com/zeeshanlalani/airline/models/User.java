package com.zeeshanlalani.airline.models;

import org.json.JSONException;
import org.json.JSONObject;

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

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(JSONObject obj) {
        try {
            this.id = obj.getString("_id");
            this.firstName = obj.getString("firstName");
            this.lastName = obj.getString("lastName");
            this.username = obj.getString("username");
            this.password = obj.getString("password");
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

        if (firstName != null) {
            params += "firstName="+firstName+"&";
        }

        if (lastName != null) {
            params += "lastName="+lastName+"&";
        }

        if (username != null) {
            params += "username="+username+"&";
        }

        if (password != null) {
            params += "password="+password+"&";
        }

        if (id != null) {
            params += "id="+id+"&";
        }

        return params;
    }
}
