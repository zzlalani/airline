package com.zeeshanlalani.airline.controllers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;

import com.zeeshanlalani.airline.RegisterUserActivity;
import com.zeeshanlalani.airline.helpers.APIResponseCallable;
import com.zeeshanlalani.airline.helpers.WebService;
import com.zeeshanlalani.airline.models.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zzlal on 12/5/2015.
 */
public class UserManager {

    WebService ws;

    public UserManager () {
        ws = new WebService();
    }

    public void registerUser (String firstName, String lastName, String username, String password, APIResponseCallable callback) {

        User user = new User(firstName, lastName, username, password);
        String data = user.toPostData();

        ws.postData("register", data, callback);
    }

    public  void loginUser (String username, String password, APIResponseCallable callback) {
        User user = new User(username, password);
        String data = user.toPostData();

        ws.postData("login", data, callback);
    }

}
