package com.study.iread.ariticleDetail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.webkit.WebView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.study.iread.R;
import com.study.iread.bean.StringModel;
import com.study.iread.bean.ZhihuDailyStory;
import com.study.iread.interfaces.OnStringListener;
import com.study.iread.util.Api;
import com.study.iread.util.NetWorkState;

/**
 * Created by dnw on 2017/4/26.
 */
public class ArticlePresenter implements ArticleContract.Presenter {
    private ArticleContract.View view;
    private Context context;
    private ArticleFragment articleFragment;
    private String type;
    private int id;
    private String title;
    private String coverUrl;
    private StringModel model;
    private Gson gson;
    private ZhihuDailyStory zhihuDailyStory;
    public ArticlePresenter(Context context,ArticleContract.View view) {
        super();
        this.context=context;
        this.view=view;
        this.view.setPresenter(this);
        model = new StringModel(context);
        gson=new Gson();
    }
    public void setType(String type)
    {
        this.type=type;
    }
    public void setId(int id)
    {
        this.id=id;
    }

    public void setTitle(String title)
    {
        this.title=title;
    }
    public void setCoverUrl(String url)
    {
        this.coverUrl=url;
    }
    @Override
    public void start() {

    }

    @Override
    public void requestData() {
        if (id == 0 || type == null) {
            view.showLoadingError();
            return;
        }

        view.showLoading();
        view.setTitle(title);
        view.showCover(coverUrl);

        // set the web view whether to show the image
        view.setImageMode(false);
        switch (type) {
            case "ZHIHU":
                if (NetWorkState.networkconnected(context)) {
                    model.load(Api.ZHIHU_NEWS + id, new OnStringListener() {
                        @Override
                        public void onSuccess(String result) {
                            {
                                Gson gson = new Gson();
                                try {
                                    zhihuDailyStory = gson.fromJson(result, ZhihuDailyStory.class);
                                    if (zhihuDailyStory.getBody() == null) {
                                        view.showResultWithoutBody(zhihuDailyStory.getShare_url());
                                    } else {
                                        view.showResult(convertZhihuContent(zhihuDailyStory.getBody()));
                                    }
                                } catch (JsonSyntaxException e) {
                                    view.showLoadingError();
                                }
                                view.stopLoading();
                            }
                        }

                        @Override
                        public void onError(VolleyError error) {
                            view.stopLoading();
                            view.showLoadingError();
                        }
                    });
                }
                break;

            default:
                view.stopLoading();
                view.showLoadingError();
                break;
        }

        view.stopLoading();
    }
    private String convertZhihuContent(String preResult) {

        preResult = preResult.replace("<div class=\"img-place-holder\">", "");
        preResult = preResult.replace("<div class=\"headline\">", "");

        // 在api中，css的地址是以一个数组的形式给出，这里需要设置
        // in fact,in api,css addresses are given as an array
        // api中还有js的部分，这里不再解析js
        // javascript is included,but here I don't use it
        // 不再选择加载网络css，而是加载本地assets文件夹中的css
        // use the css file from local assets folder,not from network
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/zhihu_daily.css\" type=\"text/css\">";


        // 根据主题的不同确定不同的加载内容
        // load content judging by different theme
        String theme = "<body className=\"\" onload=\"onLoaded()\">";
        if ((context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                == Configuration.UI_MODE_NIGHT_YES){
            theme = "<body className=\"\" onload=\"onLoaded()\" class=\"night\">";
        }

        return new StringBuilder()
                .append("<!DOCTYPE html>\n")
                .append("<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n")
                .append("<head>\n")
                .append("\t<meta charset=\"utf-8\" />")
                .append(css)
                .append("\n</head>\n")
                .append(theme)
                .append(preResult)
                .append("</body></html>").toString();
    }
    @Override
    public void openUrl(WebView webView, String url) {

    }
    @Override
    public void openInBrowser() {

    }
}
