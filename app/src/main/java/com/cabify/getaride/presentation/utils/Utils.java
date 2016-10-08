package com.cabify.getaride.presentation.utils;

import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by davidtorralbo on 08/10/16.
 */

public class Utils {

    public static String getDateStringFromDate(Calendar cal) {
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.SIMPLE_DATE_FORMAT_FOR_START_AT);

        return formatter.format(cal.getTime());
    }

    public static String getDateStringFromDateForPicker(Calendar cal) {
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.SIMPLE_DATE_FORMAT_FOR_PICKER);

        long now = System.currentTimeMillis();
        int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_MONTH;

        String prefix = (String) DateUtils.getRelativeTimeSpanString(cal.getTime().getTime(), now, DateUtils.DAY_IN_MILLIS, flags);
        String suffix = formatter.format(cal.getTime());
        String resultDate = prefix +", "+ suffix;
        return resultDate;
    }

    public static boolean isDatePassed(Calendar cal) {
        return cal.getTime().getTime() < System.currentTimeMillis();
    }

}
