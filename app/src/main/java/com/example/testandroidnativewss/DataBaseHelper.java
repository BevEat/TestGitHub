package com.example.testandroidnativewss;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "testandroidnativedb";
    private static final int DATABASE_VERSION = 1;

    public static final String LATLNG_TABLE = "latlng";

    public static final String ID_COLUMN = "id";
    public static final String LAT = "lat";
    public static final String LNG = "lng";

    public static final String CREATE_LATLNG_TABLE = "CREATE TABLE "
            + LATLNG_TABLE + "(" + ID_COLUMN + " INTEGER PRIMARY KEY, "
            + LAT + " DOUBLE, " + LNG + " DOUBLE" + ")";

    private static DataBaseHelper instance;

    public static synchronized DataBaseHelper getHelper(Context context) {
        if (instance == null)
            instance = new DataBaseHelper(context);
        return instance;
    }

    private DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LATLNG_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}