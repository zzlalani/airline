package com.zeeshanlalani.airline.helpers;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zzlal on 12/5/2015.
 * Ref: http://www.ssaurel.com/blog/learn-to-consume-a-rest-web-service-and-parse-json-result-in-android/
 */
public class WebService {
    private static final String SERVICE_URL = "http://192.168.10.4/api/";

    ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * Executes the HTTP request. To post data
     *
     * @param request
     *            Request to be executed.
     * @param param
     *            Data to be posted
     * @param callback
     *            Execute callback on request completion
     */
    public void postData (final String request, final String param, final APIResponseCallable callback) {

        Log.d("API", "Sending post request to URL (" + SERVICE_URL + request + ")");
        Log.d("POST DATA", param);

        executorService.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url = new URL(SERVICE_URL + request);
                    // Open a connection using HttpURLConnection
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.setInstanceFollowRedirects(false);
                    con.setRequestMethod("POST");

                    // Send
                    OutputStreamWriter writer = new OutputStreamWriter(
                            con.getOutputStream());
                    writer.write(param);
                    writer.close();

                    con.connect();

                    BufferedReader br = null;
                    if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));

                    } else {
                        br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    }

                    JSONObject resp = bufferToJson(br);

                    callback.setResponse(resp);
                    callback.call();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    JSONObject bufferToJson (BufferedReader br) {
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            JSONObject json = new JSONObject(sb.toString());
            return json;
        } catch (IOException e) {
            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}
