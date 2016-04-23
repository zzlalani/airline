package com.zeeshanlalani.airline.helpers;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zzlal on 12/5/2015.
 * Ref: https://community.particle.io/t/example-android-application-post-get/9355 (Primary)
 * Ref: http://www.ssaurel.com/blog/learn-to-consume-a-rest-web-service-and-parse-json-result-in-android/
 */
public class WebService {

    public static WebService instance;

    private WebService() {

    }

    public static WebService getInstance() {
        if (instance == null)
            instance = new WebService();
        return instance;
    }

    // private static final String SERVICE_URL = "http://192.168.10.6/api/";
    //private static final String SERVICE_URL = "http://172.20.10.2/api/";
    private static final String SERVICE_URL = "http://10.0.2.2/api/";

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

                    con.setReadTimeout(10000);
                    con.setConnectTimeout(10000);

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

                    callback.terminate(e.toString());
                }

            }
        });
    }

    /**
     * Executes the HTTP request. To post data
     *
     * @param request
     *            Request to be executed.
     * @param callback
     *            Execute callback on request completion
     */
    public void getData (final String request, final APIResponseCallable callback) {

        Log.d("API", "Sending get request to URL (" + SERVICE_URL + request + ")");

        executorService.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url = new URL(SERVICE_URL + request);
                    // Open a connection using HttpURLConnection
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("GET");
                    con.setReadTimeout(10000);
                    con.setConnectTimeout(10000);

                    // Send
                    InputStream in = con.getInputStream();

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
