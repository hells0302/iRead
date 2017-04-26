package com.study.iread.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by dnw on 2017/4/25.
 */
public class DateFormatter {
    public String ZhihuDailyDateFormat(long date)
    {
        String tmp;
        Date d=new Date(date+24*60*60*1000);
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        tmp=format.format(d);
        return tmp;
    }
}
