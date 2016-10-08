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

}
