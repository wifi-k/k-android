package net.treebear.kwifimanager.config;


import android.support.annotation.IntDef;

import java.util.Calendar;

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
}
