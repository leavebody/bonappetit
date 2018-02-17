package com.hophacks2018.bonappetit.bonappetit.util;


import android.provider.BaseColumns;

/**
 * @author Xiaochen Li
 */

public final class DBContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DBContract() {}

    /* Inner class that defines the table contents */
    public static class LoginDB implements BaseColumns {
        public static final String TABLE_NAME = "login";
        public static final String COLUMN_ID_NAME = "name";
        public static final String COLUMN_ISLOGGEDIN_NAME = "is_logged_in";
    }

}
