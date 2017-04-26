package com.study.iread.homepage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.study.iread.R;
import com.study.iread.adapter.MainPagerAdapter;
import com.study.iread.adapter.ZhihuDailyNewsAdapter;
import com.study.iread.bean.ZhihuDaily;
import com.study.iread.interfaces.OnRecyclerViewOnClickListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by dnw on 2017/4/25.
 */
public class ZhihuDailyFragment extends Fragment implements ZhihuDailyContract.View{
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refresh;
    private FloatingActionButton fab;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private FrameLayout frameLayout;
    private ZhihuDailyContract.Presenter presenter;
    private int mYear = Calendar.getInstance().get(Calendar.YEAR);
    private int mMonth = Calendar.getInstance().get(Calendar.MONTH);
    private int mDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    private ZhihuDailyNewsAdapter adapter;

    @Override
    public void setPresenter(ZhihuDailyContract.Presenter presenter) {
        if(presenter!=null)
            this.presenter=presenter;

    }

    public ZhihuDailyFragment() {
    }
    public static ZhihuDailyFragment newInstance()
    {
        return new ZhihuDailyFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frament_item,container,false);
        initViews(view);
        presenter.start();
        //刷新事件
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 获取最后一个完全显示的item position
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();

                    // 判断是否滚动到底部并且是向下滑动
                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                        Calendar c = Calendar.getInstance();
                        c.set(mYear, mMonth, --mDay);
                        presenter.loadMore(c.getTimeInMillis());
                    }
                }

                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isSlidingToLast = dy > 0;
                // 隐藏或者显示fab
                if(dy > 0) {
                    fab.hide();
                } else {
                    fab.show();
                }
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tabLayout.getSelectedTabPosition() == 0) {
                    Log.d("2222222222222", "onClick");
                    Calendar now = Calendar.getInstance();
                    now.set(mYear, mMonth, mDay);
                    DatePickerDialog dialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                            mYear = year;
                            mMonth = monthOfYear;
                            mDay = dayOfMonth;
                            Calendar temp = Calendar.getInstance();
                            temp.clear();
                            temp.set(year, monthOfYear, dayOfMonth);
                            presenter.loadPosts(temp.getTimeInMillis(), true);
                        }
                    }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));

                    dialog.setMaxDate(Calendar.getInstance());
                    Calendar minDate = Calendar.getInstance();
                    // 2013.5.20是知乎日报api首次上线
                    minDate.set(2013, 5, 20);
                    dialog.setMinDate(minDate);
                    dialog.vibrate(false);

                    dialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
                } else if (tabLayout.getSelectedTabPosition() == 1) {
                    ViewPager p = (ViewPager) getActivity().findViewById(R.id.view_paper);
                    MainPagerAdapter ad = (MainPagerAdapter) p.getAdapter();
                    ad.getDailyEssayFragment().showPickDialog();
                }
            }
        });

        return view;
    }

    @Override
    public void showResults(ArrayList<ZhihuDaily.Question> list) {
        if (adapter == null) {
            adapter = new ZhihuDailyNewsAdapter(getContext(), list);
            adapter.setItemClickListener(new OnRecyclerViewOnClickListener() {
                @Override
                public void OnItemClick(View v, int position) {
                    presenter.showArticle(position);
                }
            });
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void stopLoading() {
        refresh.post(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void showLoading() {
        refresh.post(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(true);
            }
        });
    }

    @Override
    public void showError() {
        Snackbar.make(fab,"加载失败",Snackbar.LENGTH_INDEFINITE)
                .setAction("重试", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.refresh();
                    }
                })
                .show();
    }
    @Override
    public void initViews(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        refresh = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        //设置下拉刷新的按钮的颜色
        refresh.setColorSchemeResources(R.color.colorPrimary);

        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setRippleColor(getResources().getColor(R.color.colorPrimaryDark));

        tabLayout = (TabLayout) getActivity().findViewById(R.id.tab_layout);


    }
}
