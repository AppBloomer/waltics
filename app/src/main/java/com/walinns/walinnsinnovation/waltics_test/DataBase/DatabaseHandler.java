package com.walinns.walinnsinnovation.waltics_test.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.walinns.walinnsinnovation.waltics_test.BeanClass.LoginData;
import com.walinns.walinnsinnovation.waltics_test.BeanClass.NoteItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by walinnsinnovation on 12/01/18.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "contactsManager";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + "login_tbl" + " (" + "username"
                + " text not null, " + "email" + " text not null " + ");");
        db.execSQL("CREATE TABLE " + "noteItem_tbl" + " (" + "id"
                + " INTEGER PRIMARY KEY, "+"note_txt"
                + " text not null, " + "note_cat" + " text not null, "+ "note_date" + " text not null " + ");");
        db.execSQL("CREATE TABLE " + "complete_noteItem_tbl" + " (" + "id"
                + " INTEGER PRIMARY KEY, "+"note_txt"
                + " text not null, " + "note_cat" + " text not null, "+ "note_date" + " text not null " + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
    public void addContact(LoginData loginData) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("username", loginData.getUsername()); // Contact Name
        values.put("email", loginData.getEmail()); // Contact Phone

        // Inserting Row
        db.insert("login_tbl", null, values);
        //2nd argument is String containing nullColumnHack
     }
    public void addNote(NoteItem noteItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("note_txt", noteItem.getNote_text());
        values.put("note_cat", noteItem.getNote_cat());
        values.put("note_date", noteItem.getNote_date());

        // Inserting Row
        db.insert("noteItem_tbl", null, values);
        //2nd argument is String containing nullColumnHack

    }
    public List<NoteItem> getAllContacts() {
        List<NoteItem> contactList = new ArrayList<NoteItem>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + "noteItem_tbl";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                System.out.println("database data:"+cursor.getInt(cursor.getColumnIndex("id")));
                NoteItem contact = new NoteItem(cursor.getString(cursor.getColumnIndex("note_txt")),cursor.getString(cursor.getColumnIndex("note_cat")),cursor.getString(cursor.getColumnIndex("note_date")), cursor.getInt(cursor.getColumnIndex("id")));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }
    public void deleteTitle(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
         String val = escape(name);
        db.execSQL("DELETE FROM " + "noteItem_tbl"+ " WHERE "+"note_txt"+"='"+val+"'");


     }
    private static String escape(String s) {
        return s != null ? s.replaceAll("\'", "\'\'") : null;
    }
    public void addCompleteItem(NoteItem noteItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("note_txt", noteItem.getNote_text());
        values.put("note_cat", noteItem.getNote_cat());
        values.put("note_date", noteItem.getNote_date());

        // Inserting Row
        db.insert("complete_noteItem_tbl", null, values);
        //2nd argument is String containing nullColumnHack

    }
    public List<NoteItem> getAllCompletedList() {
        List<NoteItem> contactList = new ArrayList<NoteItem>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + "complete_noteItem_tbl";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                System.out.println("database data:"+cursor.getString(cursor.getColumnIndex("note_txt")));
                NoteItem contact = new NoteItem(cursor.getString(cursor.getColumnIndex("note_txt")),cursor.getString(cursor.getColumnIndex("note_cat")),cursor.getString(cursor.getColumnIndex("note_date")));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    public void updateNote(NoteItem noteItem,int id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("note_txt", noteItem.getNote_text());
        values.put("note_cat", noteItem.getNote_cat());
        values.put("note_date", noteItem.getNote_date());

        // update Row
        db.update("noteItem_tbl",values,"id = ?",new String[] { String.valueOf(id) });

    }
}
