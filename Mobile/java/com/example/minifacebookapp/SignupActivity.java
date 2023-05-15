package com.example.minifacebookapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This class is responsible for handling the user registration process. It sends user data to the server and handles the server response.
 */
public class SignupActivity extends AppCompatActivity {

    public Activity activity;

    //Host address
    protected String hostAddress="192.168.0.2:8090";

    //Authentication Servlet name
    protected String servletName="/register/signup";

    /**
     * Called when the activity is starting. This sets up the UI and click listeners for the signup button and text view to redirect to the signin page.
     *
     * @param savedInstanceState The saved instance state bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        //Define current context
        activity = this;

        Button signupButton = findViewById(R.id.signupBtn);

        //Create Listener to detect a click
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create and execute a new LoginTask thread
                //Every network transaction cannot be performed in the main thread
                new SignupActivity.SignupTask(activity).execute();
            }
        });

        TextView signinReTextView = findViewById(R.id.signinReTextView);

        // Listener to detect click on text view for redirect to signin page
        signinReTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * This class is responsible for performing the user registration task in a background thread using an AsyncTask.
     */
    private class SignupTask extends AsyncTask<Void, Void, Void> {
        private Activity activity;

        private String serverResponse;

        private String url;

        /**
         * Constructor for the SignupTask class.
         *
         * @param activity The current activity.
         */
        protected SignupTask(Activity activity) {
            url = "http://" + hostAddress + servletName;
            this.activity = activity;
        }

        /**
         * Called on the UI thread before doInBackground() is called. This displays a toast to indicate that the registration process has started.
         */
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(),
                    "Registering... " + url, Toast.LENGTH_LONG).show();
        }

        /**
         * Performs the user registration task in a background thread.
         *
         * @param arg0 The arguments passed to the task.
         * @return null
         */
        protected Void doInBackground(Void... arg0) {
            String email, password, fname, lname;
            email = ((EditText) findViewById(R.id.editEmailSU)).getText().toString();
            password = ((EditText) findViewById(R.id.editPasswordSU)).getText().toString();
            fname = ((EditText) findViewById(R.id.editFirstName)).getText().toString();
            lname = ((EditText) findViewById(R.id.editLastName)).getText().toString();

            HttpHandler handler = new HttpHandler();

            serverResponse = handler.makeServiceCallSignupPost(url, email, password, fname, lname);

            return null;
        }

        /**
         * Called on the UI thread after doInBackground() has finished. This handles the server response and displays a toast to indicate whether the registration was successful.
         *
         * @param result The result of the task.
         */
        protected void onPostExecute (Void result) {
            String msgToast;

            if(serverResponse == null) {
                msgToast = "Server did not respond";
                Toast.makeText(getApplicationContext(),
                        msgToast,
                        Toast.LENGTH_LONG).show();
            } else if (serverResponse.trim().compareTo("not")==0) {
                msgToast = "Could not add user.";
                Toast.makeText(getApplicationContext(),
                        msgToast,
                        Toast.LENGTH_LONG).show();
            }
            else {
                Intent i = new Intent(SignupActivity.this, MainActivity.class);

                startActivity(i);

                msgToast = "Signup successful!";
                Toast.makeText(getApplicationContext(),
                        msgToast,
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
