package com.example.walinnsinnovation.waltics.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.walinnsinnovation.waltics.BeanClass.LoginData;

import static android.support.customtabs.CustomTabsIntent.KEY_ID;

/**
 * Created by walinnsinnovation on 12/01/18.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contactsManager";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + "login_tbl" + " (" + "username"
                + " text not null, " + "email" + " text not null " + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
    void addContact(LoginData loginData) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("username", loginData.getUsername()); // Contact Name
        values.put("email", loginData.getEmail()); // Contact Phone

        // Inserting Row
        db.insert("login_tbl", null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }
//    LoginData getLogin(String email) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query("login_tbl", new String[] { user,
//                        KEY_NAME, KEY_PH_NO }, KEY_ID + "=?",
//                new String[] { String.valueOf(id) }, null, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
//                cursor.getString(1), cursor.getString(2));
//        // return contact
//        return contact;
//    }
}
