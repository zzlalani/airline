package com.zeeshanlalani.airline.controllers;

import com.zeeshanlalani.airline.helpers.APIResponseCallable;
import com.zeeshanlalani.airline.helpers.WebService;
import com.zeeshanlalani.airline.models.Airport;
import com.zeeshanlalani.airline.models.Flight;

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

    private static FlightManager instance;

    private FlightManager () {
        ws = WebService.getInstance();
    }

    public static FlightManager getInstance() {
        if ( instance == null )
            instance = new FlightManager();
        return instance;
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

    public List<Flight> populateFlights(JSONObject searchData) {
        List<Flight> flights = new ArrayList<Flight>();
        try {
            JSONArray searchArray = searchData.getJSONArray("data");
            for (int i = 0; i < searchArray.length(); i++) {
                Flight f = new Flight(searchArray.getJSONObject(i));
                flights.add(f);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return flights;
    }

    public JSONObject getSearchObject (JSONObject searchData) {
        JSONObject searchArray;
        try {
            searchArray = searchData.getJSONObject("search");
            return searchArray;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void getFlight(String id, APIResponseCallable callback) {
        ws.getData("flight/" + id, callback);
    }

    public Flight populateFlight(JSONObject flightData) {
        Flight f = new Flight(flightData);
        return f;
    }

    public void bookFlight(String flightId, String userId, String person, String type, String date, APIResponseCallable callback) {
        String params = "";
        params += "flightId="+flightId;
        params += "&userId="+userId;
        params += "&person="+person;
        params += "&type="+type;
        params += "&bookingDate"+date;

        ws.postData("booking",params,callback);
    }

}
