package com.example.universitysportsfacilitybookingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB = "sfb.db";

    //USERS
    public static final String REGISTERED_USERS = "users";
    public static final String COL_ID = "ID";
    public static final String COL_EMAIL = "EMAIL";
    public static final String COL_USERNAME = "USERNAME";
    public static final String COL_PASSWORD = "PASSWORD";
    public static final String COL_REQUESTS = "REQUESTS";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create tables here
        db.execSQL("CREATE TABLE " + REGISTERED_USERS +
                " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_EMAIL + " TEXT, " +
                COL_USERNAME + " TEXT, " +
                COL_PASSWORD + " TEXT " +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + REGISTERED_USERS);
        onCreate(db);
    }

    // user
    public long addUser (String email, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("email", email);
        cv.put("username", username);
        cv.put("password", password);
        long res = db.insert(REGISTERED_USERS, null, cv);
        db.close();
        return res;
    }

    public int checkUser(String username, String password) {
        int userId = -1;
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_USERNAME + "=?" + " and " + COL_PASSWORD + "=?";
        String[] selectionArgs = { username, password};
        //SELECT {columns} FROM {table} WHERE {selection - insert ? for placholders} = {selectionargs - replaces placeholders in selection}
        Cursor cursor = db.rawQuery("SELECT " + COL_ID + " FROM " + REGISTERED_USERS + " WHERE " + selection, selectionArgs);

        while (cursor.moveToNext()) {
            userId = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return userId;
    }

    public long setUserPassword(int userId, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String selection = COL_ID + "=?";
        String[] selectionArgs = { String.valueOf(userId) };

        cv.put("password", newPassword);

        long res = db.update(REGISTERED_USERS, cv, selection, selectionArgs);
        db.close();
        return res;
    }

    public Cursor getUsers () {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = { COL_ID, COL_USERNAME, COL_REQUESTS };

        Cursor cursor = db.query(REGISTERED_USERS, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

}
