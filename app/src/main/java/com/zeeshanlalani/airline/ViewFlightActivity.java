package com.zeeshanlalani.airline;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zeeshanlalani.airline.controllers.FlightManager;
import com.zeeshanlalani.airline.helpers.APIResponseCallable;
import com.zeeshanlalani.airline.models.Airport;
import com.zeeshanlalani.airline.models.Flight;
import com.zeeshanlalani.airline.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ViewFlightActivity extends AppCompatActivity {

    FlightManager flightManager;

    Flight flight;
    String flightId;
    JSONObject searchObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flight);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        flightManager = new FlightManager();
        searchObj = null;

        try {
            searchObj = new JSONObject(getIntent().getStringExtra("search"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        getFlight();

        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(findViewById(R.id.activity_view_flight), iconFont);

        Button btn_book = (Button) findViewById(R.id.btn_book);
        btn_book.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog = new ProgressDialog(ViewFlightActivity.this);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();

                final Context ctx = ViewFlightActivity.this;

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

                                                        Intent bookingListIntent = new Intent(ViewFlightActivity.this, ViewBookingListActivity.class);
                                                        ViewFlightActivity.this.startActivity(bookingListIntent);
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

                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = settings.edit();
                User user = null;
                try {
                    user = new User(new JSONObject(settings.getString("user",null)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    flightManager.bookFlight(flightId, user.getId(), searchObj.getString("person"), searchObj.getString("type"), searchObj.getString("departure"), callback);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    void getFlight() {

        final ProgressDialog progressDialog = new ProgressDialog(ViewFlightActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        final Context ctx = ViewFlightActivity.this;

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
                                flight = flightManager.populateFlight(response.getJSONObject("data"));
                                fillFlight();
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
        flightId = getIntent().getStringExtra("id");

        flightManager.getFlight(flightId, callback);
    }

    void fillFlight() {
        TextView text_dates, text_people, text_type, text_name, text_price, text_from, text_to, text_date, text_time, text_name_full,
                text_time_depart, text_from_short, text_from_airport, text_time_arrive, text_to_short, text_to_airport, text_seats_available;

        text_dates = (TextView) findViewById(R.id.text_dates);
        text_people = (TextView) findViewById(R.id.text_people);
        text_type = (TextView) findViewById(R.id.text_type);
        text_name = (TextView) findViewById(R.id.text_name);
        text_price = (TextView) findViewById(R.id.text_price);
        text_from = (TextView) findViewById(R.id.text_from);
        text_to = (TextView) findViewById(R.id.text_to);
        text_date = (TextView) findViewById(R.id.text_date);
        text_time = (TextView) findViewById(R.id.text_time);
        text_name_full = (TextView) findViewById(R.id.text_name_full);
        text_time_depart = (TextView) findViewById(R.id.text_time_depart);
        text_from_short = (TextView) findViewById(R.id.text_from_short);
        text_from_airport = (TextView) findViewById(R.id.text_from_airport);
        text_time_arrive = (TextView) findViewById(R.id.text_time_arrive);
        text_to_short = (TextView) findViewById(R.id.text_to_short);
        text_to_airport = (TextView) findViewById(R.id.text_to_airport);
        text_seats_available = (TextView) findViewById(R.id.text_seats_available);

        try {

            text_dates.setText(searchObj.getString("departure") + " - " + searchObj.getString("return"));
            text_people.setText(searchObj.getString("person"));

            String typeText = "";
            String priceText = "";
            String seatsText = "";

            if ( searchObj.getString("type") == "Business Class" ) {
                typeText = "Business";
                priceText = flight.getPricebc();
                seatsText = flight.getSeatsbc();
            } else if (searchObj.getString("type") == "First Class") {
                typeText = "First";
                priceText = flight.getPricefc();
                seatsText = flight.getSeatsfc();
            } else {
                typeText = "Economy";
                priceText = flight.getPriceec();
                seatsText = flight.getSeatsec();
            }
            text_type.setText(typeText);

            text_name.setText(flight.getName());
            text_price.setText("Rs. "+priceText+"/-");

            text_from.setText(flight.getFrom().getCode());
            text_to.setText(flight.getTo().getCode());

            text_date.setText(searchObj.getString("departure"));
            text_time.setText("Non Stop - " + flight.getTimeDepart());

            text_name_full.setText(flight.getFullName());
            text_time_depart.setText(flight.getTimeDepart());
            text_time_arrive.setText(flight.getTimeArrive());
            text_from_short.setText(flight.getFrom().getCode());
            text_to_short.setText(flight.getTo().getCode());
            text_from_airport.setText(flight.getFrom().getName());
            text_to_airport.setText(flight.getTo().getName());

            text_seats_available.setText(seatsText + " seats available");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // flight;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (id) {
            case R.id.action_bookings:
                // action
                break;
            case R.id.action_search:
                // action
                break;
            case R.id.action_profile:
                // action
                break;
            case R.id.action_logout:
                // action
                break;
            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }

}
