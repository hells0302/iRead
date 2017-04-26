package com.study.iread;

import android.view.View;

/**
 * Created by dnw on 2017/4/25.
 */
public interface BaseView<T> {
    void setPresenter(T presenter);
    void initViews(View view);
}
