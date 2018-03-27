package com.project.dox.weatheritrains;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static TextView placeTextView;
    private static TextView tempTextView;
    private static TextView precipTextView;

    BackGroundTask task;

    private Double lat;
    private Double longitude;

    private EditText editTextTitle;
    private EditText editTextMessage;
    private Button buttonChannel1;
    private Button buttonChannel2;
    private NotificationHelper mNotificationHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        placeTextView = findViewById(R.id.nameTextView);
        tempTextView = findViewById(R.id.temperatureTextView);
        precipTextView = findViewById(R.id.precipitationTextView);

        LocationManager locationManger = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManger.getBestProvider(new Criteria(), false);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManger.getLastKnownLocation(provider);

        lat = location.getLatitude();
        longitude = location.getLongitude();


        // Create instance of background task and the execute this task
        task = new BackGroundTask(tempTextView, placeTextView, precipTextView);
        task.execute("https://api.darksky.net/forecast/"+ APIContract.API_KEY+ "/"+ lat + ","+ longitude + "?units=si&exclude=hourly");

        // Creating Notifications
        // Remove casts since we are using maven repo


        mNotificationHelper = new NotificationHelper(this);




        // TODO(dox): Create listeners which work based on the weather data
        /*
        buttonChannel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOnChannel1("WeatherItRains", editTextMessage.getText().toString());
            }
        });

        buttonChannel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOnChannel2("WeatherItRains", editTextMessage.getText().toString());
            }
        });
        */


    }

    public void schedChannel(String title, String message){
        NotificationCompat.Builder nb = mNotificationHelper.getChannel1Notification(title, message);
        mNotificationHelper.getManager().notify(1, nb.build());
    }


    public void sendOnChannel2(String title, String message){
        NotificationCompat.Builder nb = mNotificationHelper.getChannel2Notification(title, message);
        mNotificationHelper.getManager().notify(2, nb.build());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
