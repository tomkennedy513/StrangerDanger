package edu.villanova.tomkennedy.strangerdanger;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by TomKennedy on 11/25/2015.
 */
public class EmergencyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        createEmergency();
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
}
