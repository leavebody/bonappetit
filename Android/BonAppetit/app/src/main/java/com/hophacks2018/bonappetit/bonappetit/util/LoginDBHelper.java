//package com.hophacks2018.bonappetit.bonappetit.util;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import java.util.ArrayList;
//
//
///**
// * The helper class to handle the login SQLite database
// * @author Xiaochen Li
// */
//
//public class LoginDBHelper extends SQLiteOpenHelper {
//    // If you change the database schema, you must increment the database version.
//    public static final int DATABASE_VERSION = 1;
//    public static final String DATABASE_NAME = "Login.db";
//
//    private static final String SQL_CREATE_ENTRIES =
//            "CREATE TABLE " + DBContract.LoginDB.TABLE_NAME + " (" +
//                    DBContract.LoginDB.COLUMN_ID_NAME + " TEXT PRIMARY KEY," +
//                    DBContract.LoginDB.COLUMN_ISLOGGEDIN_NAME + " boolean";
//
//    private static final String SQL_DELETE_ENTRIES =
//            "DROP TABLE IF EXISTS " + DBContract.LoginDB.TABLE_NAME;
//
//    // implement the singleton pattern
//    private static LoginDBHelper instance;
//
//    private LoginDBHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    public static LoginDBHelper getInstance(Context context) {
//        if (instance == null) {
//            instance = new LoginDBHelper(context.getApplicationContext());
//        }
//        return instance;
//    }
//
//    /**
//     * Check if the user is logged in.
//     *
//     * @return
//     */
//    public boolean isLoggedIn(String id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        // Define a projection that specifies which columns from the database
//        // you will actually use after this query.
//        String[] projection = {
//                DBContract.LoginDB.COLUMN_EMAIL_NAME
//        };
//
//        Cursor cursor = db.query(
//                DBContract.LoginDB.TABLE_NAME,
//                projection,
//                null,
//                null,
//                null,
//                null,
//                null
//        );
//
//        boolean logedin = cursor.getCount() == 1;
//        cursor.close();
//        return logedin;
//    }
//
//    public ArrayList<String> getAll() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("select * from " + DBContract.LoginDB.TABLE_NAME, null);
//        ArrayList<String> list = new ArrayList<>();
//
//        if(cursor.moveToFirst())
//
//        {
//            while (!cursor.isAfterLast()) {
//                String name = cursor.getString(cursor.getColumnIndex(DBContract.LoginDB.COLUMN_EMAIL_NAME));
//
//                list.add(name);
//                cursor.moveToNext();
//            }
//        }
//        return list;
//    }
//
//    /**
//     * Insert a new user record.
//     * @param id The id of the login session.
//     */
//    public void insert(String id){
//        SQLiteDatabase db = this.getWritableDatabase();
//        // Create a new map of values, where column names are the keys
//        ContentValues values = new ContentValues();
//        values.put(DBContract.LoginDB.COLUMN_ID_NAME, id);
//        values.put(DBContract.LoginDB.COLUMN_ISLOGGEDIN_NAME, false);
//
//        // Insert the new row, returning the primary key value of the new row
//        db.insert(DBContract.LoginDB.TABLE_NAME, null, values);
//    }
//
//
//    /**
//     * Get the email of the current login session.
//     * @return the email of the current login session
//     */
//    public String getLoggedInID(){
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        // Define a projection that specifies which columns from the database
//        // you will actually use after this query.
//        String[] projection = {
//                DBContract.LoginDB.COLUMN_EMAIL_NAME
//        };
//
//        Cursor cursor = db.query(
//                DBContract.LoginDB.TABLE_NAME,
//                projection,
//                null,
//                null,
//                null,
//                null,
//                null
//        );
//        cursor.moveToLast();
//        String email = cursor.getString(0);
//        cursor.close();
//
//        return email;
//    }
//
//    /**
//     * Get the key of the current login session.
//     * @return the key of the current login session
//     */
//    public String getKey(){
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        // Define a projection that specifies which columns from the database
//        // you will actually use after this query.
//        String[] projection = {
//                DBContract.LoginDB.COLUMN_KEY_NAME
//        };
//
//        Cursor cursor = db.query(
//                DBContract.LoginDB.TABLE_NAME,
//                projection,
//                null,
//                null,
//                null,
//                null,
//                null
//        );
//        cursor.moveToLast();
//        String key = cursor.getString(0);
//        cursor.close();
//
//        return key;
//    }
//
//
//    // implement methods from superclass
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(SQL_CREATE_ENTRIES);
//    }
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        // This database is only a cache for online data, so its upgrade policy is
//        // to simply to discard the data and start over
//        db.execSQL(SQL_DELETE_ENTRIES);
//        onCreate(db);
//    }
//    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        onUpgrade(db, oldVersion, newVersion);
//    }
//}
