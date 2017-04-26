package com.study.iread.homepage;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.study.iread.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private MainFragment mainFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        if(savedInstanceState!=null)
        {
            mainFragment=(MainFragment)getSupportFragmentManager().getFragment(savedInstanceState,"MainFrament");
        }else
        {
            mainFragment=MainFragment.newInstance();
        }
        if (!mainFragment.isAdded()) {
                getSupportFragmentManager().beginTransaction().add(R.id.layout_fragment, mainFragment, "MainFragment").commit();
        }
        showMainFragment();
    }
    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        //关联导航栏按钮和导航栏
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView=(NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    private void showMainFragment() {
        FragmentTransaction fragmentTransaction =getSupportFragmentManager().beginTransaction();
    fragmentTransaction.show(mainFragment);
    fragmentTransaction.commit();
    toolbar.setTitle("iRead");
}

    /**
     * 导航栏菜单点击事件
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);
        switch (item.getItemId())
        {
            case R.id.home:
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.dayOrNight:
                break;
            case R.id.setting:
                break;
            case R.id.about:
                break;
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mainFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "MainFragment", mainFragment);
        }
    }
}
