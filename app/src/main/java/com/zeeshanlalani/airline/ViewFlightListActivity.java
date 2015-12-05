package com.zeeshanlalani.airline;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.zeeshanlalani.airline.controllers.FlightManager;
import com.zeeshanlalani.airline.helpers.FlightListAdapter;
import com.zeeshanlalani.airline.models.Airport;
import com.zeeshanlalani.airline.models.Flight;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;

public class ViewFlightListActivity extends AppCompatActivity {

    FlightManager flightManager;

    List<Flight> flights;

    ListView listView;
    Context context;

    String fromText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flight_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        flightManager = new FlightManager();
        JSONObject searchObj = null;

        try {
            JSONObject searchData = new JSONObject(getIntent().getStringExtra("searchData"));
            flights = flightManager.populateFlights(searchData);
            searchObj = flightManager.getSearchObject(searchData);

            if (flights.size() > 0) {
                Flight f = flights.get(0);
                Airport a = f.getTo();
                fromText = a.getName();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        context = this;

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new FlightListAdapter(this, flights, searchObj));

        TextView search_detail = (TextView) findViewById(R.id.search_detail);
        search_detail.setText("Search a flight to "+fromText+"\nPrices one-way per person");
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
