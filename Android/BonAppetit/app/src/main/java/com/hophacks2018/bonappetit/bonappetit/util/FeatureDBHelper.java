package com.hophacks2018.bonappetit.bonappetit.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        SQLiteDatabase db = this.getReadableDatabase();
        String featuresString;

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                DBContract.LoginDB.COLUMN_ISLOGGEDIN_NAME,
                DBContract.LoginDB.COLUMN_ID_NAME
        };
        // Filter results WHERE "title" = 'My Title'
        String selection = DBContract.LoginDB.COLUMN_ISLOGGEDIN_NAME + " = ?";
        String[] selectionArgs = { "1" };
        Cursor cursor = db.query(
                DBContract.LoginDB.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.getCount()>0){
            return true;
        } else {
            return false;
        }
    }

    public String getCurrentUserName() {
        SQLiteDatabase db = this.getReadableDatabase();
        String username;

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                DBContract.LoginDB.COLUMN_ISLOGGEDIN_NAME,
                DBContract.LoginDB.COLUMN_ID_NAME
        };
        // Filter results WHERE "title" = 'My Title'
        String selection = DBContract.LoginDB.COLUMN_ISLOGGEDIN_NAME + " = ?";
        String[] selectionArgs = { "1" };
        Cursor cursor = db.query(
                DBContract.LoginDB.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        cursor.moveToFirst();
        username = cursor.getString(
                cursor.getColumnIndexOrThrow(DBContract.LoginDB.COLUMN_ID_NAME)
        );

        cursor.close();
        return username;
    }


    /**
     * Insert a new user record.
     * @param id The id of the login session.
     */
    public void insert(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DBContract.LoginDB.COLUMN_ID_NAME, id);
        values.put(DBContract.LoginDB.COLUMN_ISLOGGEDIN_NAME, "1");

        // Insert the new row, returning the primary key value of the new row
        db.insert(DBContract.LoginDB.TABLE_NAME, null, values);
    }


    public void login(String id){
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBContract.LoginDB.COLUMN_ISLOGGEDIN_NAME, "1");

        String selection = DBContract.LoginDB.COLUMN_ISLOGGEDIN_NAME + " LIKE ?";
        String[] selectionArgs = { "0" };

        db.update(
                DBContract.LoginDB.TABLE_NAME,
                values,
                selection,
                selectionArgs);


// New value for one column
        values = new ContentValues();
        values.put(DBContract.LoginDB.COLUMN_ISLOGGEDIN_NAME, "1");

// Which row to update, based on the title
        selection = DBContract.LoginDB.COLUMN_ID_NAME + " LIKE ?";
        String[] selectionArgs1 = { id };

        int count = db.update(
                DBContract.LoginDB.TABLE_NAME,
                values,
                selection,
                selectionArgs1);
        if (count == 0) {
            this.insert(id);
        }
    }

    public void logout() {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBContract.LoginDB.COLUMN_ISLOGGEDIN_NAME, "1");

        String selection = DBContract.LoginDB.COLUMN_ISLOGGEDIN_NAME + " LIKE ?";
        String[] selectionArgs = {"0"};

        db.update(
                DBContract.LoginDB.TABLE_NAME,
                values,
                selection,
                selectionArgs);
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