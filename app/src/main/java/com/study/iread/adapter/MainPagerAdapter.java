package com.study.iread.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.study.iread.homepage.DailyEssayFragment;
import com.study.iread.homepage.ZhihuDailyFragment;

/**
 * Created by dnw on 2017/4/25.
 */
public class MainPagerAdapter extends FragmentPagerAdapter{
    private Context context;
    private String[] titles;
    private ZhihuDailyFragment zhihuDailyFragment;
    private DailyEssayFragment dailyEssayFragment;
    public DailyEssayFragment getDailyEssayFragment()
    {
        return dailyEssayFragment;
    }
    public ZhihuDailyFragment getZhihuDailyFragment()
    {
        return zhihuDailyFragment;
    }
    public MainPagerAdapter(FragmentManager fm, Context context, ZhihuDailyFragment zhihuDailyFragment, DailyEssayFragment dailyEssayFragment) {
        super(fm);
        this.context = context;
        titles = new String[] {"知乎日报", "每日一文"};
        this.zhihuDailyFragment = zhihuDailyFragment;
        this.dailyEssayFragment = dailyEssayFragment;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Fragment getItem(int position) {
        if(position==1)
        {
            return dailyEssayFragment;
        }

        return zhihuDailyFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
