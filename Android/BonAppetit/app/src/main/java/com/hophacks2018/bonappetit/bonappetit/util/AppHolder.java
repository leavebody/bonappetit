package com.hophacks2018.bonappetit.bonappetit.util;

/**
 * Created by molly on 2/18/18.
 */

public class AppHolder {
    private static final android.app.Application APP;

    public static android.content.Context getContext(){
        return APP.getApplicationContext();
    }

    static {
        try {
            Class<?> c = Class.forName("android.app.ActivityThread");
            APP = (android.app.Application) c.getDeclaredMethod("currentApplication").invoke(null);
        } catch (Throwable ex) {
            throw new AssertionError(ex);
        }
    }
}
