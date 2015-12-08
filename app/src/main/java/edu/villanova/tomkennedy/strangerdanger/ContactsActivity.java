package edu.villanova.tomkennedy.strangerdanger;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TomKennedy on 11/25/2015.
 */
public class ContactsActivity extends ListActivity implements View.OnClickListener {
    public final int PICK_CONTACT = 1001;
    public String contactPhone;
    public String contactName;
    public List<Contact> contactValues = new ArrayList<>();
    public int idValue;
    SQLiteHelper db = new SQLiteHelper(this);
    ArrayAdapter<Contact> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Button addContactButton = (Button) findViewById(R.id.addContact);
        addContactButton.setOnClickListener(this);
        Button deleteContactButton = (Button) findViewById(R.id.deleteContact);
        deleteContactButton.setOnClickListener(this);
        Button refresh = (Button) findViewById(R.id.refreshButton);
        refresh.setOnClickListener(this);

        contactValues = db.getAllContacts();
        Log.d("test", contactValues.toString());
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactValues);
        setListAdapter(adapter);
        ListView myList = getListView();
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int position, long id) {
                Contact contact = (Contact) av.getItemAtPosition(position);
                idValue = contact.getID();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.deleteContact:
                Contact contact = db.getContact(idValue);
                if(contact.getID() >= 1){
                    db.deleteContact(contact);
                }
                contactValues = db.getAllContacts();
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactValues);
                setListAdapter(adapter);
                adapter.notifyDataSetChanged();

                break;
            case R.id.addContact:
                Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(i, PICK_CONTACT);
                break;
            case R.id.refreshButton:
                contactValues = db.getAllContacts();
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactValues);
                setListAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;
        }

    }

    public void onStart(){
        super.onStart();
        contactValues = db.getAllContacts();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactValues);
        setListAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
    public void onResume(){
        super.onResume();
        contactValues = db.getAllContacts();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactValues);
        setListAdapter(adapter);
        adapter.notifyDataSetChanged();
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
                db.addContact(new Contact(contactName, contactPhone));
                adapter.notifyDataSetChanged();

            }
        }
    }




}
