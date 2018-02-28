package com.example.dox.weatheritrains;

import android.content.Context;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by dox on 20/02/18.
 */

public class BackGroundTask extends AsyncTask<String, Void, String> {

    TextView temp, place;

    public BackGroundTask(TextView temp, TextView place){
        this.temp = temp;
        this.place = place;

    }



    @Override
    // Opens a connection and the result in a string
    protected String doInBackground(String... urls) {
        String result = "";
        URL url;
        HttpURLConnection urlCon = null;
        try {
            url = new URL(urls[0]);

            urlCon = (HttpURLConnection) url.openConnection();

            InputStream in = urlCon.getInputStream();

            InputStreamReader isr = new InputStreamReader(in);

            int data = isr.read();

            while(data != -1){
                char current = (char) data;
                result += current;
                data = isr.read();

        }


            return result;
        }catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Finds the temp in the data and then display's it on screen
    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);


        String name;

        try {
            JSONObject json = new JSONObject(result);

            JSONObject weatherData = new JSONObject(json.getString("currently"));

            Double Kelvin = Double.parseDouble(weatherData.getString("temperature"));


            if(json.isNull("timezone")){
                name = "No Name";
            }else {
                name = json.getString("timezone");
            }
            temp.setText(String.valueOf(Kelvin));
            place.setText(name);


        }catch(Exception e) {
            e.printStackTrace();
        }

        }
    }

