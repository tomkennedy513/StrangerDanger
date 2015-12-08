package edu.villanova.tomkennedy.strangerdanger;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.List;

/**
 * Created by TomKennedy on 11/25/2015.
 */
public class ContactsActivity extends ListActivity implements View.OnClickListener {
    public final int PICK_CONTACT = 1001;
    public String contactPhone;
    public String contactName;
    public List<Contact> contactValues = null;
    SQLiteHelper db = new SQLiteHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Button addContactButton = (Button) findViewById(R.id.addContact);
        addContactButton.setOnClickListener(this);
        ArrayAdapter<Contact> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactValues);
        setListAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(i, PICK_CONTACT);
    }

    public void onStart(){
        super.onStart();

    }
    public void onResume(){
        super.onResume();

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_CONTACT && resultCode == RESULT_OK) {
            Uri contactUri = data.getData();
            Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                contactPhone = cursor.getString(column);
                int column2 = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Identity.DISPLAY_NAME);
                contactName = cursor.getString(column2);
                cursor.close();
                db.addContact(new Contact(contactName,contactPhone));
            }


        }
    }


}
