package com.study.iread.homepage;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.study.iread.bean.DailyEssays;
import com.study.iread.bean.JsonModel;
import com.study.iread.interfaces.OnJsonListener;
import com.study.iread.util.Api;
import com.study.iread.util.DateFormatter;
import com.study.iread.util.NetWorkState;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dnw on 2017/4/25.
 */
public class DailyEssayPresenter implements DailyEssayContract.Presenter {
    private DailyEssayContract.View view;
    private Context context;
    private JsonModel model;
    Gson gson=new Gson();
    private DateFormatter formatter = new DateFormatter();
    private ArrayList<DailyEssays> list = new ArrayList<DailyEssays>();
    public DailyEssayPresenter(Context context,DailyEssayContract.View view)
    {
        this.context=context;
        this.view=view;
        this.view.setPresenter(this);
        model = new JsonModel(context);
    }

    @Override
    public void start() {
        loadPosts(0,true);
    }

    @Override
    public void feelLucky() {

    }

    @Override
    public void refresh() {
        loadPosts(0,true);
    }

    @Override
    public void loadPosts(long date, final boolean clearing) {
        if (clearing) {
           view.showLoading();
        }
        if (NetWorkState.networkconnected(context)) {
            model.load(date==0?Api.MEIRIYIWEN:Api.MEIRIYIWENDAY+formatter.ZhihuDailyDateFormat(date), new OnJsonListener() {
                @Override
                public void onSuccess(JSONObject result) {
                    try {
                        Log.d("test111111111",result.toString());
                        DailyEssays post=new DailyEssays();
                        JSONObject object_data=result.getJSONObject("data");
                        Log.d("test111111112",object_data.toString());
                        Log.d("test111111112",object_data.getString("author"));
                        post.setAuthor(object_data.getString("author"));
                        post.setContent(object_data.getString("content"));
                        post.setTitle(object_data.getString("title"));
                        JSONObject getDate=object_data.getJSONObject("date");
                        post.setCurr_date(getDate.getString("curr"));
                        post.setPrev_date(getDate.getString("prev"));
                        post.setNext_date(getDate.getString("next"));
                        view.showResults(post);
                    } catch (Exception e) {
                        view.showLoadingError();
                    }
                    view.stopLoading();
                }
                @Override
                public void onError(VolleyError error) {
                    view.stopLoading();
                    view.showLoadingError();
                }
            });
        }
    }
    public void loadPosts(String date, final boolean clearing) {
        if (clearing) {
            view.showLoading();
        }
        if (NetWorkState.networkconnected(context)) {
            model.load(date.equals("")?Api.MEIRIYIWEN:Api.MEIRIYIWENDAY+date, new OnJsonListener() {
                @Override
                public void onSuccess(JSONObject result) {
                    try {
                        Log.d("test111111111",result.toString());
                        DailyEssays post=new DailyEssays();
                        JSONObject object_data=result.getJSONObject("data");
                        Log.d("test111111112",object_data.toString());
                        Log.d("test111111112",object_data.getString("author"));
                        post.setAuthor(object_data.getString("author"));
                        post.setContent(object_data.getString("content"));
                        post.setTitle(object_data.getString("title"));
                        JSONObject getDate=object_data.getJSONObject("date");
                        post.setCurr_date(getDate.getString("curr"));
                        post.setPrev_date(getDate.getString("prev"));
                        post.setNext_date(getDate.getString("next"));
                        view.showResults(post);
                    } catch (Exception e) {
                        view.showLoadingError();
                    }
                    view.stopLoading();
                }
                @Override
                public void onError(VolleyError error) {
                    view.stopLoading();
                    view.showLoadingError();
                }
            });
        }
    }

    @Override
    public void startReading(int position) {

    }
}
