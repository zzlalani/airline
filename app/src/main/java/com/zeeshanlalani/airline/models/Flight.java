package com.zeeshanlalani.airline.models;

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

    public Flight(String timeArrive, String id, String name, String fullName, String seatsbc, String seatsfc, String seatsec, String pricebc, String pricefc, String priceec, Airport from, Airport to, String timeDepart) {
        this.timeArrive = timeArrive;
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
