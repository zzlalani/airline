package com.zeeshanlalani.airline.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zzlal on 12/5/2015.
 */
public class Airport {

    String id;
    String name;
    String code;

    public Airport(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public Airport(String id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public Airport(JSONObject obj) {
        try {
            this.id = obj.getString("_id");
            this.name = obj.getString("name");
            this.code = obj.getString("code");

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

    public String getCode() {
        return code;
    }

}
