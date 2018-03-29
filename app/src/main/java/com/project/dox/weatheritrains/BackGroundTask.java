package com.project.dox.weatheritrains;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by dox on 20/02/18.
 */

public class BackGroundTask extends AsyncTask<String, Void, String> {
    // Text view var which will store the locations of temp place and precipitation
    private TextView temp, place, precipitation;

    // Stores the temp place and precip for this instance
    public BackGroundTask(TextView temp, TextView place, TextView precipitation){
        this.temp = temp;
        this.place = place;
        this.precipitation = precipitation;

    }

    @Override
    /*
     Overriding this method and constructing an API request which takes 1 or more
     String URLs as a parameter.
      */
    protected String doInBackground(String[] urls) {
        final int endOfStream = -1;
        String result = "";
        URL url;
        HttpURLConnection urlCon = null;
        try {
            url = new URL(urls[0]);
            urlCon = (HttpURLConnection) url.openConnection();
            InputStream in = urlCon.getInputStream();
            InputStreamReader isr = new InputStreamReader(in);
            int data = isr.read();

            // Continues when endOfStream (-1) is reached
            while(data != endOfStream){
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

    // Method which checks the url before calling the method so it can fail safely
    public void runBackgroundTask(Double lat, Double longitude){

        if(lat == null|| longitude == null){
            // TODO fail gracefully
        }

        this.execute("https://api.darksky.net/forecast/"+ APIContract.API_KEY+ "/"+ lat + ","+ longitude + "?units=si&exclude=hourly");

    }



    /*
    Using the return value from doInBackground sets the
    city/town if it exists
    temperature
    the probability of rain
     */
    @Override
    protected void onPostExecute(String result){
        // result is the value returned by doInBackground
        super.onPostExecute(result);

        String name;

        try {

            JSONObject json = new JSONObject(result);

            JSONObject weatherData = new JSONObject(json.getString("currently"));

            Double celsius = Double.parseDouble(weatherData.getString("temperature"));

            Double rain = Double.parseDouble(weatherData.getString("precipProbability"));


            if(json.isNull("timezone")){
                name = "No Name";
            }else {
                name = json.getString("timezone");
            }
            this.temp.setText(String.valueOf(celsius));
            place.setText(name);
            precipitation.setText(String.valueOf(rain));


        }catch(Exception e) {
            e.printStackTrace();
        }

        }
    }

