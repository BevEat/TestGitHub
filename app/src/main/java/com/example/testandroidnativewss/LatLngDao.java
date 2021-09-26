package com.example.testandroidnativewss;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

public class LatLngDao extends LatLngDbDao {

    private static final String WHERE_ID_EQUALS = DataBaseHelper.ID_COLUMN
            + " =?";

    public LatLngDao(Context context) {
        super(context);
    }

    public long save(LatLng latlng) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.LAT, latlng.getLat());
        values.put(DataBaseHelper.LNG, latlng.getLng());

        return database.insert(DataBaseHelper.LATLNG_TABLE, null, values);
    }

    public int delete(LatLng latlng) {
        return database.delete(DataBaseHelper.LATLNG_TABLE, WHERE_ID_EQUALS,
                new String[] { latlng.getId() + "" });
    }

    public ArrayList<LatLng> getLatLngArray() {
        ArrayList<LatLng> latlngArrayList = new ArrayList<LatLng>();

        Cursor cursor = database.query(DataBaseHelper.LATLNG_TABLE,
                new String[] { DataBaseHelper.ID_COLUMN,
                        DataBaseHelper.LAT,
                        DataBaseHelper.LNG }, null, null, null,
                null, null);

        while (cursor.moveToNext()) {
            LatLng latlng = new LatLng();
            latlng.setId(cursor.getInt(0));
            latlng.setLat(cursor.getDouble(1));
            latlng.setLng(cursor.getDouble(2));

            latlngArrayList.add(latlng);
        }
        return latlngArrayList;
    }

    public LatLng getLatLng(long id) {
        LatLng latlng = null;

        String sql = "SELECT * FROM " + DataBaseHelper.LATLNG_TABLE
                + " WHERE " + DataBaseHelper.ID_COLUMN + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[] { id + "" });

        if (cursor.moveToNext()) {
            latlng = new LatLng();
            latlng.setId(cursor.getInt(0));
            latlng.setLat(cursor.getDouble(1));
            latlng.setLng(cursor.getDouble(2));
        }
        return latlng;
    }
}