package com.study.iread.homepage;

import com.study.iread.BasePresenter;
import com.study.iread.BaseView;
import com.study.iread.bean.ZhihuDaily;

import java.util.ArrayList;

/**
 * Created by dnw on 2017/4/25.
 */
public interface ZhihuDailyContract {
    interface View extends BaseView<Presenter>{
        //显示加载或者其他类型的错误
        void showError();
        //显示正在加载
        void showLoading();
        //停止显示正在加载
        void stopLoading();
        //成功获取数据后，在界面中显示
        void showResults(ArrayList<ZhihuDaily.Question> list);
    }
    interface Presenter extends BasePresenter
    {
        //请求数据
        void loadPosts(long date,boolean clearing);
        //刷新数据
        void refresh();
        //加载更多文章
        void loadMore(long date);
        //显示详情
        void showArticle(int position);
    }
}
