package com.zeeshanlalani.airline.helpers;

import org.json.JSONObject;

import java.util.concurrent.Callable;

/**
 * Created by zzlal on 12/5/2015.
 */

public interface APIResponseCallable extends Callable<String> {
    /**
     * Items reported by server
     *
     * @param response
     *            Items reported by server
     */
    public void setResponse(JSONObject response);

    /**
     * exception occurred
     *
     * @param message
     *            Cause of expection
     */
    public void terminate(String message);

}