package com.study.iread.homepage;

import com.study.iread.BasePresenter;
import com.study.iread.BaseView;
import com.study.iread.bean.DailyEssays;

import java.util.ArrayList;

/**
 * Created by dnw on 2017/4/25.
 */
public class DailyEssayContract {
    interface View extends BaseView<Presenter> {

        void showLoading();

        void stopLoading();

        void showLoadingError();

        void showResults(DailyEssays dailyEssays);

    }

    interface Presenter extends BasePresenter {

        void startReading(int position);

        void loadPosts(long date, boolean clearing);
        void loadPosts(String date, boolean clearing);

        void refresh();
        void feelLucky();

    }
}
