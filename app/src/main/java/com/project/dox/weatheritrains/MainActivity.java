package com.project.dox.weatheritrains;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_FINE = 0;
    private static final int REQUEST_COARSE = 1;

    private static TextView placeTextView;
    private static TextView tempTextView;
    private static TextView precipTextView;

    // TODO change to getter and setter method
    public BackGroundTask task;

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
          // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
          // setSupportActionBar(toolbar);

        placeTextView = findViewById(R.id.nameTextView);
        tempTextView = findViewById(R.id.temperatureTextView);
        precipTextView = findViewById(R.id.precipitationTextView);


        // Check if location permissions are enabled and then requests if SDK version is 23 or greater
       findLocation();


        // Init instance of background task and then run the helper method
        task = new BackGroundTask(tempTextView, placeTextView, precipTextView);
        task.runBackgroundTask(lat, longitude);


        // Creating Notifications
        // Remove casts since we are using maven repo

        mNotificationHelper = new NotificationHelper(this);


    }




    public void useLocation() {
        LocationManager locationManger = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManger.getBestProvider(new Criteria(), false);


        Location location = null;
        try{
            location = locationManger.getLastKnownLocation(provider);
        }catch(SecurityException se){
            System.err.println("Security Exception no permission check");
        }

        try{
            lat = location.getLatitude();
            longitude = location.getLongitude();

        }catch(NullPointerException npe){


        }

        // TODO Make sure it doesn't crash if it returns null

    }


    // Users the location gathered from the phones gps
    @TargetApi(Build.VERSION_CODES.M)
    public void findLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            useLocation();
        }else{
            if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)){
                // Display message which explains why user should enable permission
            }
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE);
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_FINE) {

            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                useLocation();
                return;
            }

            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
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
