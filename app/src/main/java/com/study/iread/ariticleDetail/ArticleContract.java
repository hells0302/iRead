package com.study.iread.ariticleDetail;


import android.webkit.WebView;

import com.study.iread.BasePresenter;
import com.study.iread.BaseView;

/**
 * Created by dnw on 2017/4/26.
 */
public class ArticleContract {
   interface View extends BaseView<Presenter>{
       void showLoading();

       void stopLoading();

       void showLoadingError();

       void showResult(String result);

       void showResultWithoutBody(String url);

       void showCover(String url);

       void setTitle(String title);

       void setImageMode(boolean showImage);

   }
    interface Presenter extends BasePresenter{
        void openInBrowser();

        void openUrl(WebView webView, String url);

        void requestData();
    }
}
