package com.example.minifacebookapp;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
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

import java.util.HashMap;

/**
 * ProfileActivity is responsible for displaying the user's profile information and updating it when necessary.
 */
public class ProfileActivity extends AppCompatActivity{
    SharedPreferences prf;

    public Activity activity;

    //Host address
    protected String hostAddress="192.168.0.2:8090";

    //Authentication Servlet name
    protected String servletName="/user/info/update";

    EditText firstName;
    EditText lastName;
    EditText dob;
    EditText gender;
    EditText country;
    EditText street;
    EditText town;
    EditText state;
    EditText fos;
    EditText degree;
    EditText school;

    /**
     * Called when the activity is starting. Inflates the layout and initializes the UI elements and the SharedPreferences object.
     *
     * @param savedInstanceState the saved state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        prf = getSharedPreferences("user_details", MODE_PRIVATE);

        TextView searchView = findViewById(R.id.searchView);

        // Listener to detect click on text view for redirect to signup page
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        Button profileSaveBtn = findViewById(R.id.profileSaveBtn);

        profileSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UpdateUserInfo().execute();
            }
        });

        Button logout = findViewById(R.id.logoutBtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear user data from SharedPreferences
                SharedPreferences prefs = getSharedPreferences("user_details", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.apply();
                // Navigate to login screen
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        firstName = ((EditText) findViewById(R.id.profileFirstName));
        lastName = ((EditText) findViewById(R.id.profileLastName));
        dob = ((EditText) findViewById(R.id.profileDob));
        gender = ((EditText) findViewById(R.id.profileGender));
        country = ((EditText) findViewById(R.id.profileCountry));
        street = ((EditText) findViewById(R.id.profileStreet));
        town = ((EditText) findViewById(R.id.profileTown));
        state = ((EditText) findViewById(R.id.profileState));
        fos = ((EditText) findViewById(R.id.profileFos));
        degree = ((EditText) findViewById(R.id.profileDegree));
        school = ((EditText) findViewById(R.id.profileSchool));

        firstName.setText(prf.getString("fname", "First Name"));
        lastName.setText(prf.getString("lname", "Last Name"));
        dob.setText(prf.getString("dob", "Date of Birth"));
        gender.setText(prf.getString("gender", "Gender"));
        country.setText(prf.getString("country", "Country"));
        street.setText(prf.getString("street", "Street"));
        town.setText(prf.getString("town", "Town"));
        state.setText(prf.getString("state", "State"));
        fos.setText(prf.getString("fos", "Field of Study"));
        degree.setText(prf.getString("degree", "Degree"));
        school.setText(prf.getString("school", "School"));

    }

    /**
     * An AsyncTask that is used to update the user's profile information in the background.
     */
    private class UpdateUserInfo extends AsyncTask<Void, Void, Void> {
        private Drawable pfp;
        private Activity activity;
        private String serverResponse;
        private String url;

        HashMap<String, String> userInfo = new HashMap<>();

        protected UpdateUserInfo() {
            url = "http://" + hostAddress + servletName;
            this.activity = activity;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(ProfileActivity.this, "Updating user info...", Toast.LENGTH_LONG).show();
        }

        protected Void doInBackground(Void... arg0) {
            // Getting the fields

            //Define a HttpHandler
            HttpHandler handler = new HttpHandler();
            SharedPreferences.Editor editor = prf.edit();

            userInfo.put("fname", firstName.getText().toString());
            userInfo.put("lname", lastName.getText().toString());
            userInfo.put("dob", dob.getText().toString());
            userInfo.put("gender", gender.getText().toString());
            userInfo.put("country", country.getText().toString());
            userInfo.put("street", street.getText().toString());
            userInfo.put("town", town.getText().toString());
            userInfo.put("state", state.getText().toString());
            userInfo.put("fos", fos.getText().toString());
            userInfo.put("degree", degree.getText().toString());
            userInfo.put("school", school.getText().toString());
            userInfo.put("id", prf.getString("id", null));

            serverResponse = handler.makeServiceCallSendUserDetailsPost(url, userInfo);

            editor.putString("fname", firstName.getText().toString());
            editor.putString("lname", lastName.getText().toString());
            editor.putString("dob", dob.getText().toString());
            editor.putString("gender", gender.getText().toString());
            editor.putString("country", country.getText().toString());
            editor.putString("street", street.getText().toString());
            editor.putString("town", town.getText().toString());
            editor.putString("state", state.getText().toString());
            editor.putString("fos", fos.getText().toString());
            editor.putString("degree", degree.getText().toString());
            editor.putString("school", school.getText().toString());

            editor.apply();
            editor.putString("sessionValue", serverResponse);
            editor.commit();
            return null;
        }

        protected void onPostExecute (Void result) {


            firstName.setText(prf.getString("fname", "First Name"));
            lastName.setText(prf.getString("lname", "Last Name"));
            dob.setText(prf.getString("dob", "dob"));
            gender.setText(prf.getString("gender", "gender"));
            country.setText(prf.getString("country", "country"));
            street.setText(prf.getString("street", "street"));
            town.setText(prf.getString("town", "town"));
            state.setText(prf.getString("state", "state"));
            fos.setText(prf.getString("fos", "field of study"));
            degree.setText(prf.getString("degree", "degree"));
            school.setText(prf.getString("school", "school"));
        }
    }

}
