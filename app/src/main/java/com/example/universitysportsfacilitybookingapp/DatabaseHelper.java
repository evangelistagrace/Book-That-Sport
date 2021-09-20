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
    public static final String TABLE_USERS = "users";
    public static final String COL_ID = "ID";
    public static final String COL_EMAIL = "EMAIL";
    public static final String COL_USERNAME = "USERNAME";
    public static final String COL_PASSWORD = "PASSWORD";

    //TABLE_FACILITIES
    private static final String TABLE_FACILITIES = "facilities";
    public static final String COL_NAME = "name";
    public static final String COL_ADDRESS = "address";
    public static final String COL_OPENING_HOURS = "opening_hours";
    public static final String COL_MAX_PAX = "max_pax";
    public static final String COL_CONTACT = "contact";

    //TABLE_BOOKINGS
    private static final String TABLE_BOOKINGS = "bookings";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_FACILITY_ID = "facility_id";
    public static final String COL_DATE = "date";
    public static final String COL_TIME = "time";
    public static final String COL_PAX = "pax";
    public static final String COL_STATUS = "status";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create tables
        //users
        db.execSQL("CREATE TABLE " + TABLE_USERS +
                " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_EMAIL + " TEXT, " +
                COL_USERNAME + " TEXT, " +
                COL_PASSWORD + " TEXT " +
                ")");

        //facilities
        db.execSQL("CREATE TABLE " + TABLE_FACILITIES +
                " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_ADDRESS + " TEXT, " +
                COL_OPENING_HOURS + " TEXT, " +
                COL_CONTACT + " TEXT " +
                ")");

        //bookings
        db.execSQL("CREATE TABLE " + TABLE_BOOKINGS +
                " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USER_ID + " INTEGER, " +
                COL_FACILITY_ID + " INTEGER, " +
                COL_DATE + " TEXT, " +
                COL_TIME + " TEXT, " +
                COL_PAX + " TEXT, " +
                COL_STATUS + " TEXT " +
                ")");

        // insert into tables
        //user
        db.execSQL("INSERT INTO " + TABLE_USERS + "( " +
                COL_EMAIL + ", " + COL_USERNAME + ", " + COL_PASSWORD +
                ") VALUES ('1171302056@student.mmu.edu.my', '1171302056', '123')");

        db.execSQL("INSERT INTO " + TABLE_USERS + "( " +
                COL_EMAIL + ", " + COL_USERNAME + ", " + COL_PASSWORD +
                ") VALUES ('1171302099@student.mmu.edu.my', '1171302099', '123')");

        //facilities
        db.execSQL("INSERT INTO " + TABLE_FACILITIES + "( " +
                COL_NAME + ", " + COL_ADDRESS + ", " + COL_OPENING_HOURS + ", " +
                COL_CONTACT +
                ") VALUES ('Badminton', 'B-G-2', '10.00AM-10.00PM', '+60189686773')");

        //bookings
        db.execSQL("INSERT INTO " + TABLE_BOOKINGS + "( " +
                COL_USER_ID + ", " + COL_FACILITY_ID + ", " + COL_DATE + ", " +
                COL_TIME + ", " + COL_PAX + ", " + COL_STATUS +
                ") VALUES ('1', '1', '23/09/2021', '10AM - 11AM', '4', 'Booked')");

        db.execSQL("INSERT INTO " + TABLE_BOOKINGS + "( " +
                COL_USER_ID + ", " + COL_FACILITY_ID + ", " + COL_DATE + ", " +
                COL_TIME + ", " + COL_PAX + ", " + COL_STATUS +
                ") VALUES ('1', '1', '23/09/2021', '11AM - 12PM', '4', 'Booked')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // user
    public long addUser (String email, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("email", email);
        cv.put("username", username);
        cv.put("password", password);
        long res = db.insert(TABLE_USERS, null, cv);
        db.close();
        return res;
    }

    public int checkUser(String username, String password) {
        int userId = -1;
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_USERNAME + "=?" + " and " + COL_PASSWORD + "=?";
        String[] selectionArgs = { username, password};
        //SELECT {columns} FROM {table} WHERE {selection - insert ? for placholders} = {selectionargs - replaces placeholders in selection}
        Cursor cursor = db.rawQuery("SELECT " + COL_ID + " FROM " + TABLE_USERS + " WHERE " + selection, selectionArgs);

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

        long res = db.update(TABLE_USERS, cv, selection, selectionArgs);
        db.close();
        return res;
    }

    //  users
    public Cursor getUsers () {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = { COL_ID, COL_USERNAME};

        Cursor cursor = db.query(TABLE_USERS, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getUser (int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = { COL_ID, COL_USERNAME, COL_PASSWORD};
        String selection = COL_ID + "=" + id;

        Cursor cursor = db.query(TABLE_USERS, columns, selection, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    // facility
    public Cursor getFacility(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = { COL_ID, COL_NAME, COL_ADDRESS, COL_OPENING_HOURS, COL_CONTACT };
        String selection = COL_ID + "=" + id;

        Cursor cursor = db.query(TABLE_FACILITIES, columns, selection, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getFacilityName(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = { COL_ID, COL_NAME, COL_ADDRESS, COL_OPENING_HOURS, COL_CONTACT };
        String selection = COL_ID + "=" + id;

        Cursor cursor = db.query(TABLE_FACILITIES, columns, selection, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    // booking
    public Cursor getBookings() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = { COL_ID, COL_USER_ID, COL_FACILITY_ID, COL_DATE, COL_TIME, COL_PAX, COL_STATUS };

        Cursor cursor = db.query(TABLE_BOOKINGS, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getBookings(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = { COL_ID, COL_USER_ID, COL_FACILITY_ID, COL_DATE, COL_TIME, COL_PAX, COL_STATUS };
        String selection = COL_USER_ID + "=" + id;

        Cursor cursor = db.query(TABLE_BOOKINGS, columns, selection, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public long addBooking (int userID, int facilityID, String date, String time, String pax, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("user_id", userID);
        cv.put("facility_id", facilityID);
        cv.put("date", date);
        cv.put("time", time);
        cv.put("pax", pax);
        cv.put("status", status);
        long res = db.insert(TABLE_BOOKINGS, null, cv);
        db.close();
        return res;
    }

}
