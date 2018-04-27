/*
Student Number: 10383630
Student Name:   Baiju John
*/

package com.DBS.MobileApp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ContractsStationViewHolder extends RecyclerView.ViewHolder {

    private TextView nameTxt;
    private TextView details;

    ContractsStationViewHolder(View contractView) {
        super(contractView);
        nameTxt = contractView.findViewById(R.id.name);
        details = contractView.findViewById(R.id.details);
    }

    public void setData(final JSONObject data, String endpoint) {
        if(data!=null){
            try {
                final String name = data.getString("name");
                switch (endpoint){
                    case MainActivity.CONTRACTS_ENDPOINT:
                        String contract = "City Name: " + name;

                        String city = name;
                        //Cities information is not available in the API now (Earlier it was available). So commented below code.

                        //JSONArray cities = data.getJSONArray("name");
                        //StringBuilder citiesString = new StringBuilder("Cities: ");
                        //for (int i = 0; i<cities.length(); i++)
                        //citiesString.append(citiesString.toString().equals("Cities: ") ? "" : ", ").append(cities.getString(i));
                        //details.setText(citiesString.toString());

                        nameTxt.setText(contract);
                        details.setText(city);

                        //attach click event listener on list items.
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                v.getContext().startActivity(new Intent(v.getContext(), MainActivity.class).putExtra("stationName", name));
                            }
                        });
                        break;
                    default:
                        final String station = "Station Name: " + name;
                        nameTxt.setText(station);
                        String address = "Address: " + data.getString("address");
                        details.setText(address);
                        final String status = data.getString("status");
                        final String contractName = data.getString("contract_name");
                        final String totalBikeStands = data.getString("bike_stands");
                        final String availableBikeStands = data.getString("available_bike_stands");
                        final String availableBikes = data.getString("available_bikes");
                        final String number = data.getString("number");
                        final Long updateTime = data.getLong("last_update");
                        Date date = new Date(updateTime);
                        final String lastUpdatedDate = new SimpleDateFormat("yyyy MMM dd 'at' HH:mm a z", Locale.US).format(date);
                        //attach click event handler on station list items.
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String message = "Number: " + number + "\n" +
                                        station + "\n" +
                                        name + "\n" +
                                        "Status: " + status + "\n" +
                                        "City Name: " + contractName + "\n" +
                                        "Total Bike Stands: " + totalBikeStands + "\n" +
                                        "Available Bike Stands: " + availableBikeStands + "\n" +
                                        "Available Bikes: " + availableBikes + "\n" +
                                        "Last Updated On: " + lastUpdatedDate;
                                new AlertDialog.Builder(v.getContext())
                                        .setTitle(name)
                                        .setMessage(message)
                                        .show();
                            }
                        });
                        break;
                }
            }catch (JSONException ex){
                ex.printStackTrace();
            }

        }
    }
}
