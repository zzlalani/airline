package com.zeeshanlalani.airline;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zeeshanlalani.airline.controllers.UserManager;
import com.zeeshanlalani.airline.helpers.APIResponseCallable;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginUserActivity extends AppCompatActivity {

    UserManager userController;

    EditText txtUsername, txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userController = new UserManager();

        TextView link_register = (TextView) findViewById(R.id.link_register);
        link_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginUserActivity.this, RegisterUserActivity.class);
                LoginUserActivity.this.startActivity(registerIntent);
            }
        });

        txtUsername = (EditText) findViewById(R.id.input_username);
        txtPassword = (EditText) findViewById(R.id.input_password);

        Button btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {

                    final ProgressDialog progressDialog = new ProgressDialog(LoginUserActivity.this);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();

                    String username = txtUsername.getText().toString();
                    String password = txtPassword.getText().toString();
                    final Context ctx = LoginUserActivity.this;

                    APIResponseCallable callback = new APIResponseCallable() {

                        private JSONObject response;

                        @Override
                        public void setResponse(JSONObject _response) {
                            // TODO Auto-generated method stub
                            response = _response;
                        }

                        @Override
                        public String call() throws Exception {
                            Log.d("RESP", response.toString());
                            if (response.getString("status").equals("OK")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        Intent searchIntent = new Intent(LoginUserActivity.this, SearchFlightActivity.class);
                                        LoginUserActivity.this.startActivity(searchIntent);
                                        progressDialog.dismiss();

                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        try {
                                            new AlertDialog.Builder(ctx)
                                                    .setTitle("Error!")
                                                    .setMessage(response.getString("message"))
                                                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // continue with delete
                                                            dialog.cancel();
                                                        }
                                                    })
                                                    .show();
                                            progressDialog.dismiss();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                            }
                            return null;
                        }
                    };

                    userController.loginUser(username, password, callback);
                }
            }
        });
    }

    public boolean validate() {
        boolean valid = true;

        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();

        if (username.isEmpty()) {
            txtUsername.setError("please enter username");
            valid = false;
        } else {
            txtUsername.setError(null);
        }

        if (password.isEmpty()) {
            txtPassword.setError("please enter password");
            valid = false;
        } else {
            txtPassword.setError(null);
        }

        return valid;
    }
}
