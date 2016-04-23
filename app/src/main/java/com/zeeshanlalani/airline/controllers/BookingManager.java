package com.zeeshanlalani.airline.controllers;

import com.zeeshanlalani.airline.helpers.APIResponseCallable;
import com.zeeshanlalani.airline.helpers.WebService;
import com.zeeshanlalani.airline.models.Booking;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzlal on 12/6/2015.
 */
public class BookingManager {

    WebService ws;

    public BookingManager() {
        ws = WebService.getInstance();
    }

    public void getBookings(String userId, APIResponseCallable callback) {
        ws.getData("booking/" + userId, callback);

    }

    public List<Booking> populateBookings(JSONArray bookingArray) {
        List<Booking> bookings = new ArrayList<Booking>();
        for (int i = 0; i < bookingArray.length(); i++) {
            try {
                Booking b = new Booking(bookingArray.getJSONObject(i));
                bookings.add(b);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return bookings;
    }
}
