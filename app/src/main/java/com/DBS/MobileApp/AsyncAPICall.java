/*
Student Number: 10383630
Student Name:   Baiju John
*/

package com.DBS.MobileApp;


import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//AsyncTask enables proper and easy use of the UI thread.
// It allows to perform background operations and publish results on the UI thread
class AsyncAPICall extends AsyncTask<Void, Void, String> {

    private String url;
    private String endpoint;

    private NetworkCallback networkCallback;

    AsyncAPICall(NetworkCallback networkCallback, String url, String endpoint) {
        this.url = url;
        this.endpoint = endpoint;
        this.networkCallback = networkCallback;
    }

    @Override
    protected void onPreExecute() {
        networkCallback.dataFetchStarted(endpoint);
    }

    @Override
    protected String doInBackground(Void... voids) {
        //this method runs on the background thread.
        return GETdata(url);
    }

    @Override
    protected void onPostExecute(String jsonString) {
        //after successful API call process json string.
        if (networkCallback!=null) {
            networkCallback.processJSONString(jsonString, endpoint);
        }
    }

    //API call
    private static String GETdata(String urlString){
        HttpURLConnection urlConnection = null;
        StringBuilder result;
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(10000);
            urlConnection.connect();

            InputStream is = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            result = null;
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return result!=null?result.toString():null;
    }
}