package edu.villanova.tomkennedy.strangerdanger;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by TomKennedy on 11/25/2015.
 */
public class EmergencyActivity extends Activity {

    ImageButton recordbutton;
    boolean recording;
    double lat;
    double lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        recordbutton = (ImageButton) findViewById(R.id.recordButton);
        recordbutton.setImageResource(android.R.drawable.ic_btn_speak_now);

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

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
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);


    }

    public void recordPress(View v){
        if (!recording){
            recordbutton.setImageResource(android.R.drawable.ic_media_pause);
            recordbutton.setBackgroundColor(0xFFFF0000);
            recording = true;
        }
        else {
            recordbutton.setImageResource(android.R.drawable.ic_btn_speak_now);
            recordbutton.setBackgroundColor(0xFF5B5A5C);
            recording = false;
        }

    }


   /* private void sendSMS(String phoneNumber, String message) {
        ArrayList<PendingIntent> sentPendingIntents = new ArrayList<>();
        ArrayList<PendingIntent> deliveredPendingIntents = new ArrayList<>();
        try {
            SmsManager sms = SmsManager.getDefault();
            ArrayList<String> mSMSMessage = sms.divideMessage(message);
            sms.sendMultipartTextMessage(phoneNumber, null, mSMSMessage,
                    sentPendingIntents, deliveredPendingIntents);

        } catch (Exception e) {

            e.printStackTrace();
            Toast.makeText(getBaseContext(), "SMS sending failed...", Toast.LENGTH_SHORT).show();
        }

    }*/

    @SuppressWarnings("unused")
    private void createEmergency(/*String emergencyMessage*/){


    }


    LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            lat = (double) location.getLatitude();
            lon = (double) location.getLongitude();
            TextView gpsText = (TextView)findViewById(R.id.gpstext);
            TextView latText = (TextView)findViewById(R.id.latitude);
            TextView lonText = (TextView)findViewById(R.id.longitude);

            latText.setText("Lat= " + lat);
            lonText.setText("Lon= " +lon);

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };


}
