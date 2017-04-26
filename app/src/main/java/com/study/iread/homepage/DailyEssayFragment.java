package com.study.iread.homepage;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.study.iread.R;

import com.study.iread.bean.DailyEssays;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dnw on 2017/4/25.
 */
public class DailyEssayFragment extends Fragment implements DailyEssayContract.View{
    private DailyEssayContract.Presenter presenter;
    private SwipeRefreshLayout refresh;
    private FloatingActionButton fab;
    private TabLayout tabLayout;
    private TextView title;
    private TextView author;
    private TextView content;
    private Toolbar toolbar;
    private NestedScrollView nestedScrollView;
    private DailyEssays dailyEssays;
    private RelativeLayout bottom_menu;


    private int mYear = Calendar.getInstance().get(Calendar.YEAR);
    private int mMonth = Calendar.getInstance().get(Calendar.MONTH);
    private int mDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    public DailyEssayFragment() {
    }

    public static DailyEssayFragment newInstance() {
        return new DailyEssayFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_daily,container,false);
        initViews(view);
        //显示导航栏菜单
        setHasOptionsMenu(true);

        presenter.start();
        //刷新事件
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY > 0) {
                    fab.hide();
                } else {
                    fab.show();
                }
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
        title=(TextView)view.findViewById(R.id.title);
        author=(TextView)view.findViewById(R.id.author);
        content=(TextView)view.findViewById(R.id.content);
        toolbar=(Toolbar)getActivity().findViewById(R.id.toolbar);

        refresh = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        //设置下拉刷新的按钮的颜色
        refresh.setColorSchemeResources(R.color.colorPrimary);

        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setRippleColor(getResources().getColor(R.color.colorPrimaryDark));

        tabLayout = (TabLayout) getActivity().findViewById(R.id.tab_layout);

        nestedScrollView=(NestedScrollView)view.findViewById(R.id.nestedScrollView);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.daily_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.before:
                presenter.loadPosts(dailyEssays.getPrev_date(),false);
                break;
            case R.id.next:
                SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
                String tmp=format.format(new Date());
                if(Integer.parseInt(dailyEssays.getNext_date())<=Integer.parseInt(tmp))
                    presenter.loadPosts(dailyEssays.getNext_date(),false);
                else
                    Toast.makeText(getContext(),"已是最新文章",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public void setPresenter(DailyEssayContract.Presenter presenter) {
    if(presenter!=null)
    {
        this.presenter=presenter;
    }
    }

    @Override
    public void showResults(DailyEssays dailyEssays) {
        this.dailyEssays=dailyEssays;
        title.setText(dailyEssays.getTitle());
        author.setText(dailyEssays.getAuthor());
        Pattern p=Pattern.compile("<p>");
        Matcher m=p.matcher(dailyEssays.getContent());
        String tmp=m.replaceAll("        ");
        p=Pattern.compile("</p>");
        m=p.matcher(tmp);
        content.setText(m.replaceAll("\n"));
    }

    @Override
    public void showLoadingError() {
        Snackbar.make(fab,"加载失败",Snackbar.LENGTH_INDEFINITE)
                .setAction("重试", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.refresh();
                    }
                })
                .setDuration(1000)
                .show();
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

    public void showPickDialog() {

        Calendar now = Calendar.getInstance();
        now.set(mYear, mMonth, mDay);
        DatePickerDialog dialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                Calendar temp = Calendar.getInstance();
                temp.clear();
                temp.set(year, monthOfYear, dayOfMonth);
                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;
                presenter.loadPosts(temp.getTimeInMillis(), true);
            }
        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));

        dialog.setMaxDate(Calendar.getInstance());
        Calendar minDate = Calendar.getInstance();
        minDate.set(2014, 1, 1);
        dialog.setMinDate(minDate);
        // set the dialog not vibrate when date change, default value is true
        dialog.vibrate(false);
        dialog.show(getActivity().getFragmentManager(), "DatePickerDialog");

    }

}
