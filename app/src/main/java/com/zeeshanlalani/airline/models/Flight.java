package com.zeeshanlalani.airline.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zzlal on 12/5/2015.
 */
public class Flight {

    String id;
    String name;
    String fullName;

    String seatsbc;
    String seatsfc;
    String seatsec;

    String pricebc;
    String pricefc;
    String priceec;

    Airport from;
    Airport to;

    String timeDepart;
    String timeArrive;

    public Flight(String name, String fullName, String seatsbc, String seatsfc, String seatsec, String pricebc, String pricefc, String priceec, Airport from, Airport to, String timeDepart, String timeArrive) {
        this.name = name;
        this.fullName = fullName;
        this.seatsbc = seatsbc;
        this.seatsfc = seatsfc;
        this.seatsec = seatsec;
        this.pricebc = pricebc;
        this.pricefc = pricefc;
        this.priceec = priceec;
        this.from = from;
        this.to = to;
        this.timeDepart = timeDepart;
        this.timeArrive = timeArrive;
    }

    public Flight(String id, String name, String fullName, String seatsbc, String seatsfc, String seatsec, String pricebc, String pricefc, String priceec, Airport from, Airport to, String timeDepart, String timeArrive) {

        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.seatsbc = seatsbc;
        this.seatsfc = seatsfc;
        this.seatsec = seatsec;
        this.pricebc = pricebc;
        this.pricefc = pricefc;
        this.priceec = priceec;
        this.from = from;
        this.to = to;
        this.timeDepart = timeDepart;
        this.timeArrive = timeArrive;
    }

    public Flight(JSONObject obj) {
        try {

            this.id = obj.getString("_id");
            this.name = obj.getString("name");
            this.fullName = obj.getString("fullName");

            this.seatsbc = obj.getString("seatsbc");
            this.seatsfc = obj.getString("seatsfc");
            this.seatsec = obj.getString("seatsec");

            this.pricebc = obj.getString("pricebc");
            this.pricefc = obj.getString("pricefc");
            this.priceec = obj.getString("priceec");

            this.from = new Airport(obj.getJSONObject("from"));
            this.to = new Airport(obj.getJSONObject("to"));

            this.timeDepart = obj.getString("timeDepart");
            this.timeArrive = obj.getString("timeArrive");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public String getSeatsbc() {
        return seatsbc;
    }

    public String getSeatsfc() {
        return seatsfc;
    }

    public String getSeatsec() {
        return seatsec;
    }

    public String getPricebc() {
        return pricebc;
    }

    public String getPricefc() {
        return pricefc;
    }

    public String getPriceec() {
        return priceec;
    }

    public Airport getFrom() {
        return from;
    }

    public Airport getTo() {
        return to;
    }

    public String getTimeDepart() {
        return timeDepart;
    }

    public String getTimeArrive() {
        return timeArrive;
    }
}
