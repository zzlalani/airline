package com.zeeshanlalani.airline;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.ArrayMap;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.zeeshanlalani.airline.controllers.FlightManager;
import com.zeeshanlalani.airline.helpers.APIResponseCallable;
import com.zeeshanlalani.airline.models.Airport;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchFlightActivity extends AppCompatActivity {

    FlightManager flightManager;
    List<Airport> airports;

    Spinner spinner_from, spinner_to, spinner_class;
    RadioButton radio_oneway, radio_round;
    RadioGroup radio_trip;

    EditText input_departure, input_return, input_person;

    String type, from, to, trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        flightManager = new FlightManager();
        getAirports();

        spinner_from = (Spinner) findViewById(R.id.spinner_from);
        spinner_to = (Spinner) findViewById(R.id.spinner_to);
        spinner_class = (Spinner) findViewById(R.id.spinner_class);

        spinner_from.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Airport a = airports.get(position);
                from = a.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_to.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Airport a = airports.get(position);
                to = a.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_class.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] types = getResources().getStringArray(R.array.flight_class);
                type = types[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        radio_oneway = (RadioButton) findViewById(R.id.radio_oneway);
        radio_round =  (RadioButton) findViewById(R.id.radio_round);

        radio_trip = (RadioGroup) findViewById(R.id.radio_trip);
        radio_trip.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_oneway) {
                    trip = "oneway";
                } else {
                    trip = "round";
                }
            }
        });

        input_departure = (EditText) findViewById(R.id.input_departure);
        input_return = (EditText) findViewById(R.id.input_return);
        input_person = (EditText) findViewById(R.id.input_person);

        Button btn_find = (Button) findViewById(R.id.btn_find);
        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate()) {
                    final ProgressDialog progressDialog = new ProgressDialog(SearchFlightActivity.this);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();

                    final Context ctx = SearchFlightActivity.this;


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

                                        Intent flightsIntent = new Intent(SearchFlightActivity.this, ViewFlightListActivity.class);
                                        flightsIntent.putExtra("searchData", response.toString());
                                        SearchFlightActivity.this.startActivity(flightsIntent);

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
                    String params = generateSearchData();
                    flightManager.searchFlights(params, callback);
                }
            }
        });
    }

    String generateSearchData () {
        String param = "";

        param += "type="+type;
        param += "&to="+to;
        param += "&from="+from;
        param += "&trip="+trip;
        param += "&departure="+input_departure.getText().toString();
        param += "&return="+input_return.getText().toString();
        param += "&person="+input_person.getText().toString();

        return param;
    }

    public boolean validate() {
        boolean valid = true;

        String departure = input_departure.getText().toString();
        String returns = input_return.getText().toString();
        String persons = input_person.getText().toString();

        if (type.isEmpty()) {
            radio_round.setError("Please select trip type");
            valid = false;
        } else {
            radio_round.setError(null);
        }

        if (to.isEmpty()) {
            valid = false;
        } else {

        }

        if (from.isEmpty()) {
            valid = false;
        } else {

        }

        if (departure.isEmpty()) {
            input_departure.setError("departure date cannot be empty");
            valid = false;
        } else {
            input_departure.setError(null);
        }

        if (returns.isEmpty() && trip.equals("round")) {
            input_return.setError("return date cannot be empty");
            valid = false;
        } else {
            input_return.setError(null);
        }

        if (persons.isEmpty()) {
            input_person.setError("please enter number to person going to travel");
            valid = false;
        } else {
            input_person.setError(null);
        }

        return valid;
    }

    void getAirports () {

        final ProgressDialog progressDialog = new ProgressDialog(SearchFlightActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        final Context ctx = SearchFlightActivity.this;

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
                                flightManager.populateAirports(response.getJSONArray("data"));
                                airports = flightManager.getAirports();
                                fillAirportSpinners();

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

        flightManager.getAirports(callback);
    }

    void fillAirportSpinners () {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < airports.size(); i++) {
            Airport a = airports.get(i);
            list.add(a.getName() + " " + a.getCode());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_from.setAdapter(adapter);
        spinner_to.setAdapter(adapter);

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
