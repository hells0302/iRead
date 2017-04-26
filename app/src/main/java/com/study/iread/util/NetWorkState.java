package com.study.iread.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by dnw on 2017/4/25.
 */
public class NetWorkState {
    public static boolean networkconnected(Context context)
    {
        if(context!=null)
        {
            ConnectivityManager manager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info=manager.getActiveNetworkInfo();
            if(info!=null)
            {
                return info.isAvailable();
            }
        }
        return false;
    }
    public static boolean wifiConnected(Context context)
    {
        if(context!=null)
        {
            ConnectivityManager manager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info=manager.getActiveNetworkInfo();
            if(info!=null)
            {
                if(info.getType()==ConnectivityManager.TYPE_WIFI)
                {
                    return info.isAvailable();
                }
            }
        }
        return false;
    }
    public static boolean mobileConnected(Context context)
    {
        if(context!=null)
        {
            ConnectivityManager manager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info=manager.getActiveNetworkInfo();
            if(info!=null)
            {
                if(info.getType()==ConnectivityManager.TYPE_MOBILE)
                {
                    return info.isAvailable();
                }
            }
        }
        return false;
    }
}
