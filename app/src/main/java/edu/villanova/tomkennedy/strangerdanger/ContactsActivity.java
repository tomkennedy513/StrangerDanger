package edu.villanova.tomkennedy.strangerdanger;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;

import edu.villanova.tomkennedy.strangerdanger.R;

import static android.text.TextUtils.join;

/**
 * Created by TomKennedy on 11/25/2015.
 */
public class ContactsActivity extends ListActivity implements View.OnClickListener {
    public final int PICK_CONTACT = 1001;
    public String contactPhone;
    public ArrayList<String> contactValues = new ArrayList<>();
    public String csv;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Button addContactButton = (Button) findViewById(R.id.addContact);
        addContactButton.setOnClickListener(this);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactValues);
        setListAdapter(adapter);
        /*SharedPreferences prefs = getSharedPreferences("key", 0);
        String restoredCSV = prefs.getString("csv", "");
        if (restoredCSV != null){
            String[] restored = restoredCSV.split(",");
            for(int i =0; i < restored.length; i++){
                contactPhone = restored[i];
                addContact();
            }
        }*/
       //removeContact();

    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(i, PICK_CONTACT);
    }

    public void onStart(){
        super.onStart();
        SharedPreferences prefs = getSharedPreferences("key", 0);
        String restoredCSV = prefs.getString("csv", "");
        /*if (restoredCSV != null) {
            String[] restored = restoredCSV.split(",");
            for (int i = 0; i < restored.length; i++) {
                contactPhone = restored[i];
                addContact();
            }
        }*/
    }
    public void onResume(){
        super.onResume();
        SharedPreferences prefs = getSharedPreferences("key",0);
        String restoredCSV = prefs.getString("csv", "");
        /*if (restoredCSV != null){
            String[] restored = restoredCSV.split(",");
            for(int i =0; i < restored.length; i++){
                contactPhone = restored[i];
                addContact();
            }
        }*/
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_CONTACT && resultCode == RESULT_OK) {
            Uri contactUri = data.getData();
            Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                contactPhone = cursor.getString(column);
                cursor.close();
            }
            addContact();

        }
    }

    public void addContact(){
        contactValues.add(contactPhone);
        adapter.notifyDataSetChanged();
        SharedPreferences prefs = getSharedPreferences("key",0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(contactPhone, contactPhone);
        editor.apply();
        //csv = join(",", contactValues);
        Log.d("test", csv);
    }

    @SuppressWarnings("unused")
    public ArrayList<String> getNumbers(){
        return contactValues;
    }


    public void removeContact(){
        contactValues.clear();
        SharedPreferences p = getSharedPreferences("key",0);
        SharedPreferences.Editor editor = p.edit();
        editor.clear();
        editor.apply();
        adapter.notifyDataSetChanged();

    }
}
