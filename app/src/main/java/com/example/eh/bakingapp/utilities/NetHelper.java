package com.example.eh.bakingapp.utilities;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eh on 6/23/2017.
 */

public class NetHelper {

    private static NetHelper mInstance;
    private static RequestQueue mRequestQueue;

    private NetHelper(Context context) {
        mRequestQueue = Volley.getInstance(context).getRequestQueue();
    }


    /**
     * Get an Instance from NetHelper in all the project (Signleton pattern).
     *
     * @return NetHelper
     */
    public static NetHelper getInstance(Context context) {
        if (mInstance == null)
            mInstance = new NetHelper(context);

        return mInstance;
    }
    public void jsonRequest(final String url, Response.Listener<String> successLinstener, Response.ErrorListener errorListener) {
        StringRequest request = new StringRequest(Request.Method.GET,url, successLinstener, errorListener) {
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(request);
    }





}
