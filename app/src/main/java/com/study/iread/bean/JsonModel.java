package com.study.iread.bean;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.study.iread.interfaces.OnJsonListener;
import com.study.iread.util.VolleySingleton;

import org.json.JSONObject;


/**
 * Created by dnw on 2017/4/26.
 */
public class JsonModel {
    private Context context;
    public JsonModel(Context context) {
        this.context=context;
    }
    public void load(String url, final OnJsonListener listener){
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET,url, null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("test111111111111",response.toString());
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("test11111111111",error.toString());
                listener.onError(error);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getVolleySingleton(context).addToRequestQueue(request);
    }
}
