package com.example.minifacebookapp;
/***
 *  CPEN 410 - Mobile, Web, and Internet Programming
 *
 *  This class downloads the JSON data from a web server.
 *
 */

import android.content.SharedPreferences;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

public class HttpHandler {

    // This for debugging
    private static final String TAG = com.example.minifacebookapp.HttpHandler.class.getSimpleName();

    /***
     *  Default constructor
     */
    public HttpHandler() {
    }


    /***
     *  This method downloads the JSON data from a Request URL
     *
     * @param reqUrl: Target URL
     * @return
     */
    public String makeServiceCallPost(String reqUrl, String email, String pass) {
        // HTTP Response
        String response = null;
        try {
            //Generate a URL object from the requested URL

            URL url = new URL(reqUrl);
            // Create a Http Connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Define Request POST
            conn.setRequestMethod("POST");

            //Define the parameters list
            String parameters="email="+email+"&password="+pass;

            //Establish the option for sending parameters using the POST method
            conn.setDoOutput(true);
            //Add the parameters list to the http request
            conn.getOutputStream().write(parameters.getBytes("UTF-8"));

            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            // Convert the InputStream in a Spring
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    /***
     *  This method requests the user information to display
     *
     * @param reqUrl: Target URL
     * @return
     */
    public String makeServiceCallGetUserDetailsPost(String reqUrl) {
        // HTTP Response
        String response = null;
        try {
            //Generate a URL object from the requested URL

            URL url = new URL(reqUrl);
            // Create a Http Connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Define Request POST
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Android");

            //Establish the option for sending parameters using the POST method
            conn.setDoOutput(false);

            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            // Convert the InputStream in a Spring
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    /***
     *  This method requests the user information to be updated
     *
     * @param reqUrl: Target URL
     * @param userInfo: HashMap containing user information
     * @return
     */
    public String makeServiceCallSendUserDetailsPost(String reqUrl, HashMap<String, String> userInfo) {
        // HTTP Response
        String response = null;
        try {
            //Generate a URL object from the requested URL

            URL url = new URL(reqUrl);
            // Create a Http Connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Define Request POST
            conn.setRequestMethod("POST");

            //Define the parameters list
            String parameters="fname=" + userInfo.get("fname") + "&lname=" + userInfo.get("lname")
                    + "&dob=" + userInfo.get("dob") + "&gender=" + userInfo.get("gender")
                    + "&country=" + userInfo.get("country") + "&street=" + userInfo.get("street")
                    + "&town=" + userInfo.get("town") + "&state=" + userInfo.get("state")
                    + "&fieldOfStudy=" + userInfo.get("fos") + "&degree=" + userInfo.get("degree")
                    + "&school=" + userInfo.get("school") + "&userUserId=" + userInfo.get("id");
            System.out.println("POSTING: " + parameters);

            //Establish the option for sending parameters using the POST method
            conn.setDoOutput(true);
            //Add the parameters list to the http request
            conn.getOutputStream().write(parameters.getBytes("UTF-8"));

            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            // Convert the InputStream in a Spring
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    /***
     *  This method sends a request for a list of users with a search query
     *
     * @param reqUrl: Target URL
     * @param query: search query
     * @return
     */
    public String makeServiceCallSearch(String reqUrl, String query) {
        // HTTP Response
        String response = null;
        try {
            //Generate a URL object from the requested URL

            URL url = new URL(reqUrl);
            // Create a Http Connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Define Request POST
            conn.setRequestMethod("POST");

            //Define the parameters list
            String parameters="search=" + query;

            //Establish the option for sending parameters using the POST method
            conn.setDoOutput(true);
            //Add the parameters list to the http request
            conn.getOutputStream().write(parameters.getBytes("UTF-8"));

            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            // Convert the InputStream in a Spring
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    /***
     *  This method sends a request to create an user
     *
     * @param reqUrl: Target URL
     * @param email: user email
     * @param fname: user first name
     * @param lname: user last name
     * @param pass: user password
     * @return
     */
    public String makeServiceCallSignupPost(String reqUrl, String email, String pass, String fname, String lname) {
        // HTTP Response
        String response = null;
        try {
            //Generate a URL object from the requested URL

            URL url = new URL(reqUrl);
            // Create a Http Connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Define Request POST
            conn.setRequestMethod("POST");

            //Define the parameters list
            String parameters="email="+email+"&password="+pass+"&fname="+fname+"&lname="+lname;

            //Establish the option for sending parameters using the POST method
            conn.setDoOutput(true);
            //Add the parameters list to the http request
            conn.getOutputStream().write(parameters.getBytes("UTF-8"));

            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            // Convert the InputStream in a Spring
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    /***
     *  This method downloads the JSON data from a Request URL
     *
     * @param reqUrl: Target URL
     * @return
     */
    public String makeServiceCall(String reqUrl) {
        // HTTP Response
        String response = null;
        try {
            //Generate a URL object from the requested URL
            URL url = new URL(reqUrl);
            // Create a Http Connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // Define Request GET
            conn.setRequestMethod("GET");
            System.out.println(conn.getRequestProperties().toString());
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            // Convert the InputStream in a Spring
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    /**
     *  This method generates a String  from a InputStream
     * @param is: InputStream
     * @return
     */
    private String convertStreamToString(InputStream is) {
        // Generate a BufferedReader from a InputStream
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        // Create a StringBuilder
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            // Traverse the inputStream and generate a String
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Return the String
        return sb.toString();
    }
}
