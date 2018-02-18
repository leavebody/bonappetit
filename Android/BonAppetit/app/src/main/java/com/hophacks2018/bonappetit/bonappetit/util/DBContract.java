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

    /* Inner class that defines the table contents */
    public static class FeatureDB implements BaseColumns {
        public static final String TABLE_NAME = "feature";
        public static final String COLUMN_FEATURE = "feature";
    }

    /* Inner class that defines the table contents */
    public static class HistoryDB implements BaseColumns {
        public static final String TABLE_NAME = "history";
        public static final String COLUMN_DISH_NAME = "dish";
        public static final String COLUMN_IMAGE_PATH = "path";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_FEATURE = "feature";

    }
}
