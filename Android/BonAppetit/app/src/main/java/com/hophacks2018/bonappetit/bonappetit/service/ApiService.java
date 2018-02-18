package com.hophacks2018.bonappetit.bonappetit.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hophacks2018.bonappetit.bonappetit.models.KnowledgeGraphRaw;
import com.hophacks2018.bonappetit.bonappetit.request.ApiRequest;
import com.hophacks2018.bonappetit.bonappetit.util.MyParser;
import com.hophacks2018.bonappetit.bonappetit.util.NetworkResponseRequest;
import com.hophacks2018.bonappetit.bonappetit.util.VolleyCallback;
import com.hophacks2018.bonappetit.bonappetit.vector.VecManip;

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
        String url = "https://www.bing.com/search?q="+rawName.replace(" ", "+") + "+wiki";
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
            dishName = MyParser.BingHTMLParser(html);
            if (dishName != null) {
                result.setStatus(true);
                result.setModel(dishName);
            }
        } else {
            Log.d("ApiService", "dish name status code: " + response.statusCode);
        }
        return result;
    }

    /**
     * Get the feature from an official dish name.
     */
    public static void getFeature(final Context c, String rawName, final ServiceCallBack<double[]> callback) {

        getCookbookName(c, rawName, new ServiceCallBack<String>() {
            @Override
            public void getModelOnSuccess(ModelResult<String> modelResult) {
                if (modelResult.isStatus()) {
                    String url = "https://en.wikibooks.org/wiki/Cookbook:" + modelResult.getModel();
                    ApiRequest request = new ApiRequest(c);
                    request.htmlRequest(new VolleyCallback() {
                        @Override
                        public void onSuccess(NetworkResponse response) {
                            callback.getModelOnSuccess(getFeatureRespProcess(response));
                        }
                    }, url);
                } else {
                    callback.getModelOnSuccess(new ModelResult<double[]>());
                }
            }
        });
    }

    private static ModelResult<double[]> getFeatureRespProcess(NetworkResponse response) {
        ModelResult<double[]> result = new ModelResult();
        ArrayList<double[]> ingredients;

        String html = NetworkResponseRequest.parseToString(response);

        if (response.statusCode == 200) {

            ArrayList<double[]> rawVectors = MyParser.CookbookHTMLParser(html);
            double[] feature=null;
            VecManip kk = VecManip.getInstance();
            //todo get feature from rawVectors
            feature = kk.compute(rawVectors);

            //todo check feature is valid
            result.setStatus(true);
            result.setModel(feature);
        } else {
            result.setStatus(false);
            Log.d("ApiService", "feature status code: " + response.statusCode);
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
        String cookbookName;

        String html = NetworkResponseRequest.parseToString(response);

        if (response.statusCode == 200) {

            cookbookName = MyParser.CookbookSearchHTMLParser(html);
            if (cookbookName!=null) {
                result.setStatus(true);
                result.setModel(cookbookName);
            }
        } else {
            Log.d("ApiService", "cookbook name status code: " + response.statusCode);
        }
        return result;
    }


    public static void getKnowledge(final Context c, String dishName, final ServiceCallBack<KnowledgeGraphRaw> callback) {
        ApiRequest request = new ApiRequest(c);
        request.knowledgeGraphRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(getKnowledgeRespProcess(response));
            }
        }, dishName);
    }

    private static ModelResult<KnowledgeGraphRaw> getKnowledgeRespProcess(NetworkResponse response) {
        ModelResult<KnowledgeGraphRaw> result = new ModelResult();

        JsonObject jsResp = NetworkResponseRequest.parseToJsonObject(response);
        Gson gson = new Gson();
        KnowledgeGraphRaw knowledgeGraphRaw = gson.fromJson(jsResp, KnowledgeGraphRaw.class);
        if (response.statusCode == 200 && !knowledgeGraphRaw.itemListElement.isEmpty()) {
            // todo filter valid description
            result.setStatus(true);
            result.setModel(knowledgeGraphRaw);
        } else {
            result.setStatus(false);
            Log.d("ApiService", "Knowledge status code: " + response.statusCode);
        }
        return result;
    }
}
