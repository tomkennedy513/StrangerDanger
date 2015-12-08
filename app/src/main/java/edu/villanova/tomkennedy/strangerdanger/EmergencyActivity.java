package edu.villanova.tomkennedy.strangerdanger;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TomKennedy on 11/25/2015.
 */
public class EmergencyActivity extends Activity {

    ImageButton recordbutton;
    boolean recording;
    double lat;
    double lon;
    MediaRecorder mRecorder;

    public List<Contact> contactValues = new ArrayList<>();
    SQLiteHelper db = new SQLiteHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        //Set record button
        recordbutton = (ImageButton) findViewById(R.id.recordButton);
        recordbutton.setImageResource(android.R.drawable.ic_btn_speak_now);




        //Gather GPS Data
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

    public void onDestroy(){
        super.onDestroy();
        if (mRecorder != null){
        mRecorder.release();
        mRecorder = null;}
    }
    
    public String generatePhoneList(){
        contactValues = db.getAllContacts();
        String numbers = "";
        String temp;
        int i = 0;
        for (Contact contact : contactValues) {
            if(i != 0){numbers += ", ";}
            temp = contact.getNumber();
            temp = temp.replace(" ", "");
            temp = temp.replace("(", "");
            temp = temp.replace(")", "");
            temp = temp.replace("-", "");

            numbers += temp;
            i++;
        }

        Log.d("TEST ", numbers);

        return numbers;
    }
    
    

    public void recordPress(View v) throws IOException {
        if (!recording){
            recordbutton.setImageResource(android.R.drawable.ic_media_pause);
            recordbutton.setBackgroundColor(0xFFFF0000);
            recording = true;

            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/StrangerAudio.mp3");
            if(file.exists()){file.delete();}

            //Initialize Audio recording
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mRecorder.setOutputFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/StrangerAudio.mp3");
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            try {
                mRecorder.prepare();
                mRecorder.start();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            recordbutton.setImageResource(android.R.drawable.ic_btn_speak_now);
            recordbutton.setBackgroundColor(0xFF5B5A5C);
            recording = false;

            mRecorder.stop();


            sendAudio();



        }

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


    public void sendGPS(View view) {
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse("smsto:" + generatePhoneList()));
        sendIntent.putExtra("sms_body", "HELP! My current GPS coordinates are Lat: " + lat + " Lon: " + lon);
        //sendIntent.setType("vnd.android-dir/mms-sms");
        startActivity(sendIntent);
    }

    public void sendAudio(){
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"mausland@villanova.edu"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "StrangerDanger ALERT!");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "HELP! My current GPS coordinates are Lat: " + lat + " Lon: " + lon + "\nPlease listen to my attached audio message.");

        final File file1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"/StrangerAudio.mp3");
        Uri uri = Uri.parse("file://" + file1);

        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        //emailIntent.setType("audio/mp3");

        startActivity(Intent.createChooser(emailIntent, "Send email..."));

    }


}
