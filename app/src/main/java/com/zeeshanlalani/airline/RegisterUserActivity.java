package com.zeeshanlalani.airline;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zeeshanlalani.airline.controllers.UserManager;
import com.zeeshanlalani.airline.helpers.APIResponseCallable;
import com.zeeshanlalani.airline.models.User;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterUserActivity extends AppCompatActivity {

    UserManager userController;

    EditText txtFirstName, txtLastName, txtUsername, txtPassword, txtCpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userController = UserManager.getInstance();

        TextView link_login = (TextView) findViewById(R.id.link_login);
        link_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtFirstName = (EditText) findViewById(R.id.input_firstName);
        txtLastName = (EditText) findViewById(R.id.input_lastName);
        txtUsername = (EditText) findViewById(R.id.input_username);
        txtPassword = (EditText) findViewById(R.id.input_password);
        txtCpassword = (EditText) findViewById(R.id.input_cpassword);

        Button btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (validate()) {

                    final ProgressDialog progressDialog = new ProgressDialog(RegisterUserActivity.this);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();

                    String firstName = txtFirstName.getText().toString();
                    String lastName = txtLastName.getText().toString();

                    String username = txtUsername.getText().toString();
                    String password = txtPassword.getText().toString();
                    final Context ctx = RegisterUserActivity.this;

                    APIResponseCallable callback = new APIResponseCallable() {

                        private JSONObject response;

                        @Override
                        public void setResponse(JSONObject _response) {
                            // TODO Auto-generated method stub
                            response = _response;
                        }

                        @Override
                        public void terminate(final String message) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    new AlertDialog.Builder(ctx)
                                            .setTitle("Error!")
                                            .setMessage(message)
                                            .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    // continue with delete
                                                    dialog.cancel();
                                                }
                                            })
                                            .show();
                                    progressDialog.dismiss();
                                }
                            });
                        }

                        @Override
                        public String call() throws Exception {
                            Log.d("RESP", response.toString());
                            if (response.getString("status").equals("OK")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        try {
                                            new AlertDialog.Builder(ctx)
                                                    .setTitle("Congratulation!")
                                                    .setMessage(response.getString("message"))
                                                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // continue with delete
                                                            finish();
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

                    userController.registerUser(firstName, lastName, username, password, callback);
                }

            }
        });

    }

    public boolean validate() {
        boolean valid = true;

        String firstName = txtFirstName.getText().toString();
        String lastName = txtLastName.getText().toString();

        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        String cpassword = txtCpassword.getText().toString();

        if (firstName.isEmpty()) {
            txtFirstName.setError("please enter your first name");
            valid = false;
        } else {
            txtFirstName.setError(null);
        }

        if (lastName.isEmpty()) {
            txtLastName.setError("please enter your last name");
            valid = false;
        } else {
            txtLastName.setError(null);
        }

        if (username.isEmpty()) {
            txtUsername.setError("please select a username");
            valid = false;
        } else {
            txtUsername.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            txtPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else if (!password.equals(cpassword)) {
            txtPassword.setError("password and confirm password must match");
            valid = false;
        } else {
            txtPassword.setError(null);
        }

        return valid;
    }

}
