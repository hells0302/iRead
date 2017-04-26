package com.study.iread.bean;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.study.iread.interfaces.OnStringListener;
import com.study.iread.util.VolleySingleton;

import org.json.JSONObject;

/**
 * Created by dnw on 2017/4/25.
 */
public class StringModel {
    private Context context;
    public StringModel(Context context) {
        this.context=context;
    }
    public void load(String url,final OnStringListener listener){
        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getVolleySingleton(context).addToRequestQueue(request);
    }

}
