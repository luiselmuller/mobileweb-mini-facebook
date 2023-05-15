package com.example.minifacebookapp;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    //Activity context
    public Activity activity;

    //Host address
    protected String hostAddress="192.168.0.2:8090";

    //Authentication Servlet name
    protected String servletName="/auth/login";

    SharedPreferences pref;

    Intent profileIntent;

    String firstName;
    String lastName;
    String dob;
    String gender;
    String country;
    String street;
    String town;
    String state;
    String fos;
    String degree;
    String school;

    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Define current context
        activity = this;

        pref = getSharedPreferences("user_details", MODE_PRIVATE);
        profileIntent = new Intent(MainActivity.this, ProfileActivity.class);

        EditText emailEdit = (EditText) findViewById(R.id.editEmail);
        EditText passwordEdit = (EditText) findViewById(R.id.editPassword);

        if(pref.contains("email") && pref.contains("password") && pref.contains("sessionValue"))
        {
            email = pref.getString("email", null);
            password = pref.getString("sessionValidate", null);

            new LoginTask(activity).execute();
        }
        else
        {
            Button loginButton = findViewById(R.id.loginBtn);

            //Create Listener to detect a click
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Create and execute a new LoginTask thread
                    //Every network transaction cannot be performed in the main thread
                    new LoginTask(activity).execute();
                }
            });
        }

        TextView signUpTextView = findViewById(R.id.signupReTextView);

        // Listener to detect click on text view for redirect to signup page
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     *  This class define a thread for networks transactions
     */
    private class LoginTask extends AsyncTask<Void, Void, Void> {

        // Context: every transaction in a Android application must be attached to a context
        private Activity activity;

        //Server response
        private String serverResponse;

        private String url;

        /***
         * Special constructor: assigns the context to the thread
         *
         * @param activity: Context
         */
        protected LoginTask(Activity activity)
        {
            //Define the servlet URL
            url = "http://" + hostAddress + servletName;
            this.activity = activity;
        }

        /**
         *  on PreExecute method: runs after the constructor is called and before the thread runs
         */
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "Authenticating..." + url, Toast.LENGTH_LONG).show();
        }

        /***
         *  Main thread
         * @param arg0
         * @return
         */
        protected Void doInBackground(Void... arg0) {

            //Read GUI inputs
            String email, password;
            email = ((EditText) findViewById(R.id.editEmail)).getText().toString();
            password = ((EditText) findViewById(R.id.editPassword)).getText().toString();

            //Define a HttpHandler
            HttpHandler handler = new HttpHandler();

            //perform the authentication process and capture the result in serverResponse variable
            serverResponse = handler.makeServiceCallPost(url, email, password);

            return null;
        }



        protected void onPostExecute (Void result){
            String msgToast;

            //Verify the authentication result
            // not: the user could not be authenticated
            System.out.println("RESPONSE: " + serverResponse);
            if (serverResponse == null) {
                // Handle the case when the server did not return a response
                msgToast = "Server did not respond";
                Toast.makeText(getApplicationContext(),
                        msgToast,
                        Toast.LENGTH_LONG).show();
            } else if (serverResponse.trim().compareTo("not")==0) {
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();

                // Handle the case when the login credentials are incorrect
                msgToast= "Wrong email or password";
                Toast.makeText(getApplicationContext(),
                        msgToast,
                        Toast.LENGTH_LONG).show();
            } else {
                Intent i = new Intent(MainActivity.this, ProfileActivity.class);

                SharedPreferences.Editor editor = pref.edit();

                editor.putString("email", email);
                editor.putString("password", password);
                editor.putString("sessionValidate", serverResponse);
                editor.apply();
                if(serverResponse != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(serverResponse);
                        JSONArray items = jsonObj.getJSONArray("userInfo");
                        System.out.print("JSON ITEMS: " + items);

                        for(int j = 0; j < items.length(); j++) {
                            JSONObject c = items.getJSONObject(j);
                            if(c != null) {
                                switch(j)
                                {
                                    case 0:
                                        String fname = c.optString("fname", "First Name");
                                        editor.putString("fname", fname);
                                        break;
                                    case 1:
                                        String lname = c.optString("lname", "Last Name");
                                        editor.putString("lname", lname);
                                        break;
                                    case 2:
                                        String dob = c.optString("dob", "dob");
                                        editor.putString("dob", dob);
                                        break;
                                    case 3:
                                        String gender = c.optString("gender", "gender");
                                        editor.putString("gender", gender);
                                        break;
                                    case 4:
                                        String country = c.optString("country", "country");
                                        editor.putString("country", country);
                                        break;
                                    case 5:
                                        String street = c.optString("street", "street");
                                        editor.putString("street", street);
                                        break;
                                    case 6:
                                        String town = c.optString("town", "town");
                                        editor.putString("town", town);
                                        break;
                                    case 7:
                                        String state = c.optString("state", "state");
                                        editor.putString("state",state );
                                        break;
                                    case 8:
                                        String fos = c.optString("fieldOfStudy", "field of study");
                                        editor.putString("fos", fos);
                                        break;
                                    case 9:
                                        String degree = c.optString("degree", "degree");
                                        editor.putString("degree", degree);
                                        break;
                                    case 10:
                                        String school = c.optString("school", "school");
                                        editor.putString("school", school);
                                        break;
                                    case 11:
                                        String userId = c.optString("id", "");
                                        editor.putString("id", userId);
                                        break;
                                }
                            }
                        }
                        editor.apply();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                editor.putString("sessionValue", serverResponse);
                editor.commit();

                //Start the other activity
                startActivity(i);

                msgToast= "Logged in successfully!";
                Toast.makeText(getApplicationContext(),
                        msgToast,
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}