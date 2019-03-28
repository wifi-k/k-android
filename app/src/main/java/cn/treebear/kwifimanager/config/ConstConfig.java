package cn.treebear.kwifimanager.config;


import android.support.annotation.IntDef;

import java.util.ArrayList;
import java.util.Calendar;

import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.bean.Daybean;

/**
 * @author Tinlone
 * @date 2018/3/27.
 */

public interface ConstConfig {
    @IntDef({
            Calendar.FRIDAY, Calendar.MONDAY, Calendar.THURSDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.SATURDAY, Calendar.SUNDAY
    })
    @interface DayCode {
    }

    ArrayList<Integer> HOME_GUIDE_IMAGE_RESID = new ArrayList<Integer>() {{
        add(0, R.drawable.guide_home_1);
        add(1, R.drawable.guide_home_2);
        add(2, R.drawable.guide_home_3);
    }};

    ArrayList<Daybean> DAY_OF_WEEK = new ArrayList<Daybean>() {
        {
            add(new Daybean("一", Calendar.MONDAY));
            add(new Daybean("二", Calendar.TUESDAY));
            add(new Daybean("三", Calendar.WEDNESDAY));
            add(new Daybean("四", Calendar.THURSDAY));
            add(new Daybean("五", Calendar.FRIDAY));
            add(new Daybean("六", Calendar.SATURDAY));
            add(new Daybean("日", Calendar.SUNDAY));
        }
    };

    ArrayList<Integer> ONLINE_TIME_TYPE = new ArrayList<Integer>() {{
        add(R.string.auto_get_ip_address);
        add(R.string.static_ip_address);
        add(R.string.pppoe);
    }};

    ArrayList<Integer> SPECTRUM_BANDWIDTH = new ArrayList<Integer>() {{
        add(R.string.auto);
        add(R.string.sb_20mhz);
    }};

    ArrayList<Integer> WORK_MODEL = new ArrayList<Integer>() {{
        add(R.string.wm_11b);
        add(R.string.wm_11g);
        add(R.string.wm_11bn);
        add(R.string.wm_11n);
        add(R.string.wm_11gbn);
    }};
    ArrayList<Integer> NET_CHANNEL = new ArrayList<Integer>() {{
        add(R.string.nc_auto);
        add(R.string.nc_1);
        add(R.string.nc_2);
        add(R.string.nc_3);
        add(R.string.nc_4);
        add(R.string.nc_5);
        add(R.string.nc_6);
        add(R.string.nc_7);
        add(R.string.nc_8);
        add(R.string.nc_9);
    }};


}
