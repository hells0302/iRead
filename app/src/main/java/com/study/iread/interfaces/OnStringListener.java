package com.study.iread.interfaces;

import com.android.volley.VolleyError;

/**
 * Created by dnw on 2017/4/25.
 */
public interface OnStringListener {
    void onSuccess(String result);
    void onError(VolleyError error);
}
