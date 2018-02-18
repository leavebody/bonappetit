package com.hophacks2018.bonappetit.bonappetit.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hophacks2018.bonappetit.bonappetit.models.ReviewObj;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by molly on 2/17/18.
 */

public class HistoryDBHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "History.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract.HistoryDB.TABLE_NAME + " (" +
                    DBContract.HistoryDB.COLUMN_DISH_NAME + " TEXT PRIMARY KEY, " +
                    DBContract.HistoryDB.COLUMN_IMAGE_PATH + " TEXT, " +
                    DBContract.HistoryDB.COLUMN_TIME + " TEXT, " +
                    DBContract.HistoryDB.COLUMN_FEATURE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DBContract.HistoryDB.TABLE_NAME;

    // implement the singleton pattern
    private static HistoryDBHelper instance;

    private HistoryDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static HistoryDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new HistoryDBHelper(context.getApplicationContext());
        }
        return instance;
    }

    public boolean insert(String dish_name, String image_path, Date date, double[] featuresDouble) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
            String dateTime = dateFormat.format(date);
            StringBuilder featuresString = new StringBuilder();
            for (double d : featuresDouble) {
                featuresString.append(d);
                featuresString.append(",");
            }
            String featureString = featuresString.toString().substring(0, featuresString.length() - 1);

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(DBContract.HistoryDB.COLUMN_DISH_NAME, dish_name);
            values.put(DBContract.HistoryDB.COLUMN_IMAGE_PATH, image_path);
            values.put(DBContract.HistoryDB.COLUMN_TIME, dateTime);
            values.put(DBContract.HistoryDB.COLUMN_FEATURE, featureString);
            // Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(DBContract.HistoryDB.TABLE_NAME, null, values);
            return newRowId != -1;
        } catch (Exception e) {
            throw e;
        }
    }


    public ReviewObj[] getTen() throws ParseException {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] projection = {
                DBContract.HistoryDB.COLUMN_DISH_NAME,
                DBContract.HistoryDB.COLUMN_IMAGE_PATH,
                DBContract.HistoryDB.COLUMN_TIME,
                DBContract.HistoryDB.COLUMN_FEATURE
        };
        Cursor cursor = db.query(
                DBContract.HistoryDB.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        ReviewObj[] returnedlist= new ReviewObj[10];
        for (int i = 0; i < 10; i++) {
            cursor.moveToNext();
            String datetime = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.HistoryDB.COLUMN_TIME));
            String line = "";
            line = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.HistoryDB.COLUMN_FEATURE));
            String[] split = line.split(",", -1);
            double[] result = new double[split.length];
            for (int j = 0; j < split.length; j++) {
                result[j] = Double.parseDouble(split[j]);
            }

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
            Date date = format.parse(datetime);

            ReviewObj returned = new ReviewObj(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.HistoryDB.COLUMN_DISH_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DBContract.HistoryDB.COLUMN_IMAGE_PATH)),
                    date,
                    result);
            cursor.close();
            returnedlist[i] = returned;
        }
        return returnedlist;
    }


    public ReviewObj get() throws ParseException {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] projection = {
                DBContract.HistoryDB.COLUMN_DISH_NAME,
                DBContract.HistoryDB.COLUMN_IMAGE_PATH,
                DBContract.HistoryDB.COLUMN_TIME,
                DBContract.HistoryDB.COLUMN_FEATURE
        };
        Cursor cursor = db.query(
                DBContract.HistoryDB.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        cursor.moveToNext();
        String datetime = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.HistoryDB.COLUMN_TIME));
        String line = "";
        line = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.HistoryDB.COLUMN_FEATURE));
        String[] split = line.split(",", -1);
        double[] result = new double[split.length];
        for (int i = 0; i < split.length; i++) {
            result[i] = Double.parseDouble(split[i]);
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        Date date = format.parse(datetime);

        ReviewObj returned = new ReviewObj(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.HistoryDB.COLUMN_DISH_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(DBContract.HistoryDB.COLUMN_IMAGE_PATH)),
                date,
                result);
        cursor.close();
        return returned;
    }

    public boolean delete(String dish_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = DBContract.HistoryDB.COLUMN_DISH_NAME + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { dish_name };
        // Issue SQL statement.
        db.delete(DBContract.HistoryDB.TABLE_NAME, selection, selectionArgs);
        return true;
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
