package com.study.iread.interfaces;

import com.android.volley.VolleyError;
import com.google.gson.JsonObject;

import org.json.JSONObject;

/**
 * Created by dnw on 2017/4/26.
 */
public interface OnJsonListener {
    void onSuccess(JSONObject result);
    void onError(VolleyError error);
}
