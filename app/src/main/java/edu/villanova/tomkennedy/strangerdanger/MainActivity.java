package edu.villanova.tomkennedy.strangerdanger;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button contactButton = (Button) findViewById(R.id.contactButton);
        contactButton.setOnClickListener(this);
        Button emergencyButton = (Button) findViewById(R.id.emergencyButton);
        emergencyButton.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.emergencyButton:
                Intent intent = new Intent(this, EmergencyActivity.class);
                startActivity(intent);
                break;
            case R.id.contactButton:
                Intent intent2 = new Intent(this, ContactsActivity.class);
                startActivity(intent2);
                break;
        }
    }






}

