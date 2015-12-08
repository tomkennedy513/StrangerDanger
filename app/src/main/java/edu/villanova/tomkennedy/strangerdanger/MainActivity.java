package edu.villanova.tomkennedy.strangerdanger;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.villanova.tomkennedy.strangerdanger.R;

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.emergencyButton:
                Intent intent = new Intent(this, EmergencyActivity.class);
                startService(intent);
            case R.id.contactButton:
                Intent intent2 = new Intent(this, ContactsActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
