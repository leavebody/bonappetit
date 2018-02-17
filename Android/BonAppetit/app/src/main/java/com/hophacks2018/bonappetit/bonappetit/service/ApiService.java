package com.hophacks2018.bonappetit.bonappetit.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.hophacks2018.bonappetit.bonappetit.request.ApiRequest;
import com.hophacks2018.bonappetit.bonappetit.util.MyParser;
import com.hophacks2018.bonappetit.bonappetit.util.NetworkResponseRequest;
import com.hophacks2018.bonappetit.bonappetit.util.VolleyCallback;

import java.util.ArrayList;

/**
 * @author Xiaochen Li
 */

public class ApiService {

    /**
     * Get the official dish name from a raw name.
     *
     * @param c        Caller context.
     * @param callback
     */
    public static void getDishName(final Context c, String rawName, final ServiceCallBack<String> callback) {
        String url = "https://www.bing.com/search?q="+rawName.replace(" ", "+");
        ApiRequest request = new ApiRequest(c);
        request.htmlRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(getDishNameRespProcess(response));
            }
        }, url);
    }

    private static ModelResult<String> getDishNameRespProcess(NetworkResponse response) {
        ModelResult<String> result = new ModelResult();
        String dishName = null;

        String html = NetworkResponseRequest.parseToString(response);

        if (response.statusCode == 200) {
            result.setStatus(true);
            dishName = MyParser.BingHTMLParser(html);
            result.setModel(dishName);
        } else {
            result.setStatus(false);
            Log.d("ApiService", "dish name status code: " + response.statusCode);
        }
        return result;
    }

    /**
     * Get the ingredients from an official dish name.
     */
    public static void getIngredients(final Context c, String rawName, final ServiceCallBack<ArrayList<String>> callback) {
        String url = "https://www.bing.com/search?q="+rawName.replace(" ", "+");
        ApiRequest request = new ApiRequest(c);
        request.htmlRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(getIngredientsRespProcess(response));
            }
        }, url);
    }

    private static ModelResult<ArrayList<String>> getIngredientsRespProcess(NetworkResponse response) {
        ModelResult<ArrayList<String>> result = new ModelResult();
        ArrayList<String> ingredients;

        String html = NetworkResponseRequest.parseToString(response);

        if (response.statusCode == 200) {
            result.setStatus(true);
            ingredients = MyParser.CookbookHTMLParser(html);
            result.setModel(ingredients);
        } else {
            result.setStatus(false);
            Log.d("ApiService", "ingredients status code: " + response.statusCode);
        }
        return result;
    }

    /**
     * Get the ingredients from an official dish name.
     */
    public static void getCookbookName(final Context c, String dishName, final ServiceCallBack<String> callback) {
        String url = "https://en.wikibooks.org/w/index.php?search="+dishName.replace(" ", "+");
        ApiRequest request = new ApiRequest(c);
        request.htmlRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(getCookbookNameRespProcess(response));
            }
        }, url);
    }

    private static ModelResult<String> getCookbookNameRespProcess(NetworkResponse response) {
        ModelResult<String> result = new ModelResult();
        ArrayList<String> ingredients;

        String html = NetworkResponseRequest.parseToString(response);

        if (response.statusCode == 200) {
            result.setStatus(true);
            ingredients = MyParser.CookbookHTMLParser(html);
            result.setModel(ingredients);
        } else {
            result.setStatus(false);
            Log.d("ApiService", "ingredients status code: " + response.statusCode);
        }
        return result;
    }
}
