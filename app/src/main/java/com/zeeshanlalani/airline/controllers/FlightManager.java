package com.zeeshanlalani.airline.controllers;

import com.zeeshanlalani.airline.helpers.APIResponseCallable;
import com.zeeshanlalani.airline.helpers.WebService;
import com.zeeshanlalani.airline.models.Airport;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzlal on 12/6/2015.
 */
public class FlightManager {

    List<Airport> airports;
    WebService ws;

    public FlightManager () {
        ws = new WebService();
    }

    public void getAirports (APIResponseCallable callback) {

        ws.getData("airport", callback);
    }

    public void populateAirports(JSONArray data) {
        airports = new ArrayList<Airport>();

        for (int i = 0; i < data.length(); i++) {
            try {
                JSONObject airportData = data.getJSONObject(i);
                Airport a = new Airport(airportData);
                airports.add(a);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public List<Airport> getAirports () {
        return airports;
    }

    public void searchFlights(String params, APIResponseCallable callback) {
        ws.postData("flight/search", params, callback);
    }

}