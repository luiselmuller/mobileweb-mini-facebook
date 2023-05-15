package com.example.minifacebookapp;

import android.app.Activity;
import android.app.appsearch.SearchResult;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class SearchActivity  extends AppCompatActivity {

    public Activity activity;

    //Host address
    protected String hostAddress = "192.168.0.2:8090";

    //Authentication Servlet name
    protected String servletName = "/search/users";

    private RecyclerView mRecyclerView;
    private SearchAdapter mAdapter;
    private List<SearchResult> mSearchResults;

    private String serverResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usersearch);

        activity = this;

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SearchAdapter();
        mRecyclerView.setAdapter(mAdapter);

        SearchView searchView = findViewById(R.id.searchView);

        TextView profileView2 = findViewById(R.id.textView2);

        // Listener to detect click on text view for redirect to signup page
        profileView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Do something when the search text changes, if needed
                return false;
            }
        });

    }

    private void performSearch(String query) {
        String url = "http://" + hostAddress + servletName;
        HttpHandler handler = new HttpHandler();
        serverResponse = handler.makeServiceCallSearch(url, query);

        System.out.println("QUERY: " + query);
        System.out.println("RESPONSE: " + serverResponse);

    }
}