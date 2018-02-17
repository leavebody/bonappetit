//package com.hophacks2018.bonappetit.bonappetit.service;
//
//import android.content.Context;
//import android.util.Log;
//
//import com.android.volley.NetworkResponse;
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//import com.hophacks2018.bonappetit.bonappetit.R;
//import com.hophacks2018.bonappetit.bonappetit.util.NetworkResponseRequest;
//import com.hophacks2018.bonappetit.bonappetit.util.VolleyCallback;
//
//import java.util.ArrayList;
//
///**
// * @author Xiaochen Li
// */
//
//public class HTMLService {
//
//    /**
//     * Get the addresses and zipcode by latLng.
//     *
//     * @param c        Caller context.
//     * @param callback
//     */
//    public static void getGeocoding(final Context c, LatLng latLng, final ActivityCallBack<String> callback) {
//        String key = c.getResources().getString(R.string.google_maps_web_key);
//        ResourceRequest request = new ResourceRequest(c);
//        request.geoCodingRequest(new VolleyCallback() {
//            @Override
//            public void onSuccess(NetworkResponse response) {
//                callback.getModelOnSuccess(getGeocodingRespProcess(response));
//            }
//        }, latLng, key);
//
//    }
//
//    /**
//     * The helper method to process the result of get all sports request.
//     *
//     * @param response The network response to process
//     * @return A ModelResult with model type ArrayList<Sport>,
//     * which is the requested sports data.
//     */
//    private static ModelResult<MapApiResult> getGeocodingRespProcess(NetworkResponse response) {
//        ModelResult<MapApiResult> result = new ModelResult();
//        MapApiResult apiResult = new MapApiResult();
//
//        JsonObject jsResp = NetworkResponseRequest.parseToJsonObject(response);
//        Gson gson = new Gson();
//        GeocodeRaw geocodeRaw = gson.fromJson(jsResp, GeocodeRaw.class);
//
//        if (geocodeRaw.status.equals("OK")) {
//            result.setStatus(true);
//            String zipcode = null;
//            Boolean hasZipcode = false;
//            ArrayList<String> addresses = new ArrayList<>();
//
//            for (GeocodeRaw.Result result1 : geocodeRaw.results) {
//                addresses.add(result1.formatted_address);
//                if (!hasZipcode) {
//                    for (GeocodeRaw.Result.Address_component component : result1.address_components) {
//                        if (component.types.size() == 1 && component.types.get(0).equals("postal_code")) {
//                            zipcode = component.long_name;
//                            hasZipcode = true;
//                            break;
//                        }
//                    }
//                }
//            }
//            apiResult.setZipcode(zipcode);
//            apiResult.setAddresses(addresses);
//            result.setModel(apiResult);
//
//        } else {
//            result.setStatus(false);
//            Log.d("ResourceService", "geocoding: " + geocodeRaw.status);
//            result.setMessage(geocodeRaw.status);
//        }
//        return result;
//    }
//}
