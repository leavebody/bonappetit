package com.hophacks2018.bonappetit.bonappetit.request;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.hophacks2018.bonappetit.bonappetit.util.NetworkResponseRequest;
import com.hophacks2018.bonappetit.bonappetit.util.VolleyCallback;

/**
 * @author Xiaochen Li
 */

public class ApiRequest extends Request {
    /**
     * Constructor
     * @param c
     */
    public ApiRequest(Context c) {super(c);}

    /**
     * get the raw html content by its url
     * @param callback
     */
    public void htmlRequest(final VolleyCallback callback, String url) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contextf);
        NetworkResponseRequest nrRequest = new NetworkResponseRequest(com.android.volley.Request.Method.GET, url, null,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Context context = contextf.getApplicationContext();
                Toast toast = Toast.makeText(context, "volley error: "+error.getMessage(), Toast.LENGTH_LONG);
                toast.show();
            }
        }
        );
        queue.add(nrRequest);
    }

    public void knowledgeGraphRequest(final VolleyCallback callback, String name) {
        // Instantiate the RequestQueue.
        String url = "https://kgsearch.googleapis.com/v1/entities:search?query="+
                name.replace(" ", "+") +
                "&key=AIzaSyCiLSewsCDaml2lNXdqIBm34s5oDKrpGVs&limit=1&indent=True";
        RequestQueue queue = Volley.newRequestQueue(contextf);
        NetworkResponseRequest nrRequest = new NetworkResponseRequest(com.android.volley.Request.Method.GET, url, null,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Context context = contextf.getApplicationContext();
                Toast toast = Toast.makeText(context, "volley error: "+error.getMessage(), Toast.LENGTH_LONG);
                toast.show();
            }
        }
        );
        queue.add(nrRequest);
    }

    public void imageUrlRequest(final VolleyCallback callback, String url) {
        RequestQueue queue = Volley.newRequestQueue(contextf);
        NetworkResponseRequest nrRequest = new NetworkResponseRequest(com.android.volley.Request.Method.GET, url, null,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Context context = contextf.getApplicationContext();
                Toast toast = Toast.makeText(context, "volley error: "+error.getMessage(), Toast.LENGTH_LONG);
                toast.show();
            }
        }
        );
        queue.add(nrRequest);
    }

}
