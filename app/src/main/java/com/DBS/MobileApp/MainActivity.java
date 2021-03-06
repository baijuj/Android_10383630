/*
Student Number: 10383630
Student Name:   Baiju John
*/

package com.DBS.MobileApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
public class MainActivity extends AppCompatActivity implements NetworkCallback{

    ProgressBar progressBar;
    RecyclerView recyclerView;
    FloatingActionButton mapFAB;
    ContractStationsAdapter contractStationsAdapter = new ContractStationsAdapter();

    public static final String CONTRACTS_ENDPOINT = "/vls/v1/contracts?";
    public static final String STATION_ENDPOINT = "/vls/v1/stations?contract=";

    String stationName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //To show progress bar on service call.
        progressBar = findViewById(R.id.progress_bar);

        //to show city list as list view.
        recyclerView = findViewById(R.id.recycler_view);

        //set title on toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        stationName = getIntent().getStringExtra("stationName");
        final boolean isStationList = stationName!=null;
        toolbar.setTitle(isStationList?R.string.station_list:R.string.contract_list);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //home button navigation
                Intent mainIntent = new Intent(MainActivity.this, Home.class);
                startActivity(mainIntent);
            }
        });

        mapFAB = findViewById(R.id.fab_to_map);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(contractStationsAdapter);
        //Call Async API call.
        makeAsyncDataFetch(isStationList?STATION_ENDPOINT + stationName + "&" :CONTRACTS_ENDPOINT);
    }

    //API key is attached to the endpoint so add necessary & or ? at the end of endpoint
    public void makeAsyncDataFetch(final String endpoint){
        //make async service call.
        String url = getString(R.string.BASE_URL) + endpoint + "apiKey=" + getString(R.string.JCD_API_KEY);
        new AsyncAPICall(this, url, endpoint).execute();
    }

    @Override
    public void dataFetchStarted(String endpoint) {
        progressBar.setVisibility(View.VISIBLE);
    }
    //process json data from API call
    @Override
    public void processJSONString(final String jsonString, String endpoint) {
        if(jsonString!=null) {
            try {
                JSONArray jsonArray = new JSONArray(jsonString);
                contractStationsAdapter.updateDataSet(jsonArray, endpoint);
                if(stationName!=null){
                    mapFAB.setVisibility(View.VISIBLE);
                    //show map intent
                    mapFAB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent mapIntent = new Intent(MainActivity.this, MapsActivity.class);
                            mapIntent.putExtra("stationJsonString", jsonString);
                            startActivity(mapIntent);
                        }
                    });
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        else
            Toast.makeText(this, "Data Fetch Failed!", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
    }

}
