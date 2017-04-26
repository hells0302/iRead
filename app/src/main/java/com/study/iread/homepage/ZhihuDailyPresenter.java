package com.study.iread.homepage;

import android.content.Context;
import android.content.Intent;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.study.iread.ariticleDetail.ArticleActivity;
import com.study.iread.bean.StringModel;
import com.study.iread.bean.ZhihuDaily;
import com.study.iread.interfaces.OnStringListener;
import com.study.iread.util.Api;
import com.study.iread.util.DateFormatter;
import com.study.iread.util.NetWorkState;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by dnw on 2017/4/25.
 */
public class ZhihuDailyPresenter implements ZhihuDailyContract.Presenter{
    private Context context;
    private ZhihuDailyContract.View view;
    private StringModel model;
    private ArrayList<ZhihuDaily.Question> list = new ArrayList<ZhihuDaily.Question>();
    private DateFormatter formatter = new DateFormatter();
    private Gson gson = new Gson();

    public ZhihuDailyPresenter(Context context, ZhihuDailyContract.View view) {
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
        model = new StringModel(context);

    }

    /**
     * 开始加载数据等等
     */
    @Override
    public void start() {
        loadPosts(Calendar.getInstance().getTimeInMillis(),true);
    }
    @Override
    public void showArticle(int position) {
        Intent intent=new Intent(context, ArticleActivity.class);
        intent.putExtra("type", "ZHIHU");
        intent.putExtra("id", list.get(position).getId());
        intent.putExtra("title", list.get(position).getTitle());
        intent.putExtra("coverUrl", list.get(position).getImages().get(0));
        context.startActivity(intent);
    }

    /**
     * 加载更多
     * @param date
     */
    @Override
    public void loadMore(long date) {
        loadPosts(date, false);
    }

    /**
     * 下拉刷新
     */
    @Override
    public void refresh() {
        loadPosts(Calendar.getInstance().getTimeInMillis(), true);
    }

    /**
     * 加载数据
     * @param date
     * @param clearing
     */
    @Override
    public void loadPosts(long date, final boolean clearing) {
        if (clearing) {
            view.showLoading();
        }

        if (NetWorkState.networkconnected(context)) {
            model.load(Api.ZHIHU_HISTORY + formatter.ZhihuDailyDateFormat(date), new OnStringListener() {
                @Override
                public void onSuccess(String result) {

                    try {
                        ZhihuDaily post = gson.fromJson(result, ZhihuDaily.class);
                        if (clearing) {
                            list.clear();
                        }
                        for (ZhihuDaily.Question item : post.getStories()) {
                            list.add(item);
                        }
                        view.showResults(list);
                    } catch (JsonSyntaxException e) {
                        view.showError();
                    }
                    view.stopLoading();
                }
                @Override
                public void onError(VolleyError error) {
                    view.stopLoading();
                    view.showError();
                }
            });
        }
    }
}
