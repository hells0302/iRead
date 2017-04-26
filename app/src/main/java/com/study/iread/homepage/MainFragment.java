package com.study.iread.homepage;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.iread.R;
import com.study.iread.adapter.MainPagerAdapter;

/**
 * Created by dnw on 2017/4/25.
 */
public class MainFragment extends Fragment {
    private Context context;
    private ZhihuDailyFragment zhihuDailyFragment;
    private DailyEssayFragment dailyEssayFragment;
    private TabLayout tabLayout;
    private MainPagerAdapter adapter;
    ZhihuDailyPresenter zhihuDailyPresenter;
    DailyEssayPresenter dailyEssayPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context=getActivity();
        if(savedInstanceState!=null)
        {
            FragmentManager manager=getChildFragmentManager();
            zhihuDailyFragment=(ZhihuDailyFragment)manager.getFragment(savedInstanceState,"zhihu");
            dailyEssayFragment =(DailyEssayFragment)manager.getFragment(savedInstanceState,"daily");
        }else
        {
            zhihuDailyFragment=ZhihuDailyFragment.newInstance();
            dailyEssayFragment = DailyEssayFragment.newInstance();
        }
        zhihuDailyPresenter=new ZhihuDailyPresenter(context,zhihuDailyFragment);
        dailyEssayPresenter=new DailyEssayPresenter(context,dailyEssayFragment);

    }
    public MainFragment()
    {

    }
    public static MainFragment newInstance()
    {
        return new MainFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frament_main,container,false);
        initViews(view);
        setHasOptionsMenu(true);
        return view;
    }
    private void initViews(View view) {
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_paper);
        viewPager.setOffscreenPageLimit(2);
        adapter=new MainPagerAdapter(getChildFragmentManager(),context,zhihuDailyFragment, dailyEssayFragment);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager manager=getChildFragmentManager();
        manager.putFragment(outState,"zhihu",zhihuDailyFragment);
        manager.putFragment(outState,"daily", dailyEssayFragment);
    }
}
