/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 *
 * @author rafa
 */
public class DateUtils {

    public static Date getNowOnlyDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static String getDateStringFromDate(Date date, String format) {
        if (Objects.nonNull(date)) {
            SimpleDateFormat sdfDate = new SimpleDateFormat(format);
            return sdfDate.format(date);
        } else {
            return null;
        }
    }

    public static String getDayFromString(String dateString) {
        DateFormat format;
        Date date;
        SimpleDateFormat sdf;
        format = new SimpleDateFormat("dd-M-yyyy HH:mm:ss", Locale.ENGLISH);
        try {
            date = format.parse(dateString);
        } catch (ParseException ex) {
            return dateString;
        }
        sdf = new SimpleDateFormat("dd-M-yyyy");
        return sdf.format(date);
    }

    public static String getStringFromDate(Date value) {
        DateFormat format;
        SimpleDateFormat sdf;
        format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        return format.format(value);
    }

    public static String getDayFromDate(Date date) {
        if (!Objects.isNull(date)) {
            SimpleDateFormat sdf;
            sdf = new SimpleDateFormat("dd-M-yyyy");
            return sdf.format(date);
        } else {
            return null;
        }
    }

    public static String getHourFromString(Date date) {
        if (!Objects.isNull(date)) {
            SimpleDateFormat sdf;
            sdf = new SimpleDateFormat("HH:mm:ss");
            return sdf.format(date);
        } else {
            return null;
        }
    }

    public static Boolean isValid(String dateStr) {
        if (Objects.nonNull(dateStr)) {
            DateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
            sdf.setLenient(false);
            try {
                sdf.parse(dateStr);
            } catch (ParseException e) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

}
