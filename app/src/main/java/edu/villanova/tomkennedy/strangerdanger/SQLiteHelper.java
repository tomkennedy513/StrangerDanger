package edu.villanova.tomkennedy.strangerdanger;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by tom on 12/7/2015.
 */
public class SQLiteHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ContactDB";
    private static final String TABLE_CONTACTS= "Contact";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_NUMBER = "number";

    private static final String[] COLUMNS = {KEY_ID,KEY_NAME,KEY_NUMBER};

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACT_TABLE = "CREATE TABLE Contact ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, "+
                "number TEXT )";

        db.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Contact");
        this.onCreate(db);
    }

    public void addContact(Contact contact){
        Log.d("addContact", contact.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_NUMBER, contact.getNumber());
        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    public Contact getContact(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CONTACTS, COLUMNS, " id = ?", new String[]{String.valueOf(id)}, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        Contact contact = new Contact();
        contact.setId(Integer.parseInt(cursor.getString(0)));
        contact.setName(cursor.getString(1));
        contact.setNumber(cursor.getString(2));
        Log.d("getContact(" + id + ")", contact.toString());
        return contact;
    }

    public List<Contact> getAllContacts(){
        List<Contact> contacts = new LinkedList<>();
        String query = "SELECT * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Contact contact;
        if (cursor.moveToFirst()){
            do{
                contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setNumber(cursor.getString(2));

                contacts.add(contact);
            }while (cursor.moveToNext());
        }
        Log.d("getAllContacts()", contacts.toString());
        return contacts;
    }

    public int updateContact(Contact contact ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("number", contact.getName());
        values.put("number", contact.getNumber());

        int i = db.update(TABLE_CONTACTS, values, KEY_ID+" = ?",new String[]{String.valueOf(contact.getID())});
        db.close();
        return i;
    }

    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?", new String[]{String.valueOf(contact.getID())});
        db.close();
        Log.d("deleteContact", contact.toString());

    }

}
