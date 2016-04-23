package com.zeeshanlalani.airline;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.zeeshanlalani.airline.controllers.BookingManager;
import com.zeeshanlalani.airline.helpers.APIResponseCallable;
import com.zeeshanlalani.airline.helpers.BookingListAdapter;
import com.zeeshanlalani.airline.models.Booking;
import com.zeeshanlalani.airline.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ViewBookingListActivity extends AppCompatActivity {

    ListView listView;
    Context context;
    User user;

    List<Booking> bookingList;

    BookingManager bookingManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booking_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(findViewById(R.id.activity_view_booking_list), iconFont);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = settings.edit();
        user = null;
        try {
            user = new User(new JSONObject(settings.getString("user",null)));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        bookingManager = BookingManager.getInstance();

        getBookings();
        context = this;

        listView = (ListView) findViewById(R.id.listView);
    }

    void getBookings(){
        final ProgressDialog progressDialog = new ProgressDialog(ViewBookingListActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        final Context ctx = ViewBookingListActivity.this;

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
                                bookingList = bookingManager.populateBookings(response.getJSONArray("data"));
                                listView.setAdapter(new BookingListAdapter(ViewBookingListActivity.this, bookingList));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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

        bookingManager.getBookings(user.getId(), callback);
    }

}
