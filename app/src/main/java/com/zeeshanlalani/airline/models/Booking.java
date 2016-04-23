package com.zeeshanlalani.airline.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zzlal on 12/6/2015.
 */
public class Booking {

    String id;
    User user;
    Flight flight;
    String person;
    String type;
    String bookingDate;
    String date;

    public Booking(String id, User user, Flight flight, String person, String type, String bookingDate, String date) {
        this.id = id;
        this.user = user;
        this.flight = flight;
        this.person = person;
        this.type = type;
        this.bookingDate = bookingDate;
        this.date = date;
    }

    public Booking(User user, Flight flight, String person, String type, String bookingDate) {
        this.user = user;
        this.flight = flight;
        this.person = person;
        this.type = type;
        this.bookingDate = bookingDate;
    }

    public Booking(JSONObject obj) {
        try {
            this.id = obj.getString("_id");
            this.user = new User(obj.getJSONObject("userId"));
            this.flight = new Flight(obj.getJSONObject("flightId"));
            this.person = obj.getString("person");
            this.type = obj.getString("type");
            this.bookingDate = obj.getString("bookingDate");
            this.date = obj.getString("date");

        } catch (JSONException e) {
e.printStackTrace();
        }
        }

public String getId() {
        return id;
        }

public User getUser() {
        return user;
        }

public Flight getFlight() {
        return flight;
        }

public String getPerson() {
        return person;
        }

public String getType() {
        return type;
        }

public String getBookingDate() {
        return bookingDate;
        }

public String getDate() {
        return date;
        }
        }
