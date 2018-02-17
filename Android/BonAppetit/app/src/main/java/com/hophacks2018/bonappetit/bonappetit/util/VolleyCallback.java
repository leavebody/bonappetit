package com.hophacks2018.bonappetit.bonappetit.util;


import com.android.volley.NetworkResponse;

/**
 * Use this interface to handle callback function in the caller class.
 * @author Xiaochen Li
 */

public interface VolleyCallback {
    public void onSuccess(NetworkResponse response);
}