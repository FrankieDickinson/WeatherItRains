package com.example.dox.weatheritrains;

/**
 * Created by dox on 21/02/18.
 */


// Class which calculates which messages to display.
public class WeatherState {
    private Double precipitaion;
    private Double temp;

    private final Double raining = 0.5;
    private final int tempHot = 22;
    
    public WeatherState(Double precipitation, Double temp){
        this.precipitaion = precipitation;
        this.temp = temp;

    }

    public String weatherStateMessage(){
        String message;

        if(temp > tempHot){
            message = "It is hot";
        } else {
            message = "It is cold";
        }

        if(precipitaion >= raining){
            message += "and it is going to rain!";
        }

        return message;
    }

}
