package com.hophacks2018.bonappetit.bonappetit.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author Xiaochen Li
 */

public class FeatureDBHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Feature.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract.FeatureDB.TABLE_NAME + " (" +
                    DBContract.FeatureDB.COLUMN_FEATURE + " TEXT PRIMARY KEY)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DBContract.FeatureDB.TABLE_NAME;

    // implement the singleton pattern
    private static FeatureDBHelper instance;

    private FeatureDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static FeatureDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new FeatureDBHelper(context.getApplicationContext());
        }
        return instance;
    }


    public boolean insert(double[] featuresDouble) {
        SQLiteDatabase db = this.getWritableDatabase();

        StringBuilder featuresString = new StringBuilder();
        for (double d : featuresDouble) {
            featuresString.append(d);
            featuresString.append(",");
        }

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DBContract.FeatureDB.COLUMN_FEATURE, featuresString.toString().substring(0, featuresString.length()-1));
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DBContract.FeatureDB.TABLE_NAME, null, values);
        return newRowId != -1;
    }

    public double[] get() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] projection = {
                DBContract.FeatureDB.COLUMN_FEATURE
        };
        Cursor cursor = db.query(
                DBContract.FeatureDB.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        String line = "";
        while(cursor.moveToNext()) {
            line = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.FeatureDB.COLUMN_FEATURE));
        }
        cursor.close();
        String[] split = line.split(",", -1);
        double[] result = new double[split.length];
        for (int i = 0; i < split.length; i++) {
            result[i] = Double.parseDouble(split[i]);
        }
        return result;
    }

    public boolean update(double[] featuresDouble) {
        SQLiteDatabase db = this.getWritableDatabase();
        StringBuilder featuresString = new StringBuilder();
        for (double d : featuresDouble) {
            featuresString.append(d);
            featuresString.append(",");
        }
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(DBContract.FeatureDB.COLUMN_FEATURE, featuresString.toString().substring(0, featuresString.length()-1));

        int count = db.update(
                DBContract.FeatureDB.TABLE_NAME,
                values,
                null,
                null);
        return count > 0;

    }

    // implement methods from superclass
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}