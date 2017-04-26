package com.study.iread.ariticleDetail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.study.iread.R;

/**
 * Created by dnw on 2017/4/26.
 */
public class ArticleFragment extends Fragment implements ArticleContract.View{
    private ImageView imageView;
    private WebView webView;
    private NestedScrollView scrollView;
    private CollapsingToolbarLayout toolbarLayout;
    private SwipeRefreshLayout refreshLayout;
    private ArticleContract.Presenter presenter;
    private Context content;

    public ArticleFragment() {
        super();
    }
    public static ArticleFragment newInstance()
    {
        return new ArticleFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.content=getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.article_fragment_layout,container,false);
        initViews(view);
        setHasOptionsMenu(true);
        presenter.requestData();
        view.findViewById(R.id.toolbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.smoothScrollTo(0, 0);
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.requestData();
            }
        });
        return view;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void initViews(View view) {
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        //设置下拉刷新的按钮的颜色
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);

        webView = (WebView) view.findViewById(R.id.web_view);
        webView.setScrollbarFadingEnabled(true);

        ArticleActivity activity = (ArticleActivity) getActivity();
        activity.setSupportActionBar((Toolbar) view.findViewById(R.id.toolbar));
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = (ImageView) view.findViewById(R.id.image_view);
        scrollView = (NestedScrollView) view.findViewById(R.id.scrollView);
        toolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.toolbar_layout);
        //缩放,设置为不能缩放可以防止页面上出现放大和缩小的图标
        webView.getSettings().setBuiltInZoomControls(false);
        //缓存
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        //开启DOM storage API功能
        webView.getSettings().setDomStorageEnabled(true);
        //开启application Cache功能
        webView.getSettings().setAppCacheEnabled(false);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {presenter.openUrl(view, url);
                return true;
            }

        });
    }

    @Override
    public void setPresenter(ArticleContract.Presenter presenter) {
        if(presenter!=null)
        {
            this.presenter=presenter;
        }
    }
    @Override
    public void setImageMode(boolean showImage) {
        webView.getSettings().setBlockNetworkImage(showImage);
    }

    @Override
    public void setTitle(String title) {
        setCollapsingToolbarLayoutTitle(title);
    }

    @Override
    public void showCover(String url) {
        Glide.with(content)
                .load(url)
                .asBitmap()
                .placeholder(R.drawable.placeholder)
                .centerCrop()
                .error(R.drawable.placeholder)
                .into(imageView);
    }

    @Override
    public void showResultWithoutBody(String url) {

    }

    @Override
    public void showResult(String result) {
        webView.loadDataWithBaseURL("x-data://base",result,"text/html","utf-8",null);
    }

    @Override
    public void showLoadingError() {
        Snackbar.make(imageView,"加载失败",Snackbar.LENGTH_INDEFINITE)
                .setAction("重试", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        presenter.requestData();
                    }
                })
                .show();
    }

    @Override
    public void stopLoading() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void showLoading() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });
    }
    private void setCollapsingToolbarLayoutTitle(String title) {
        toolbarLayout.setTitle(title);
        toolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        toolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        toolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBarPlus1);
        toolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarPlus1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                getActivity().onBackPressed();
            break;
        }
        return true;
    }
}
