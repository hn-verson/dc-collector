package com.hhly.datacenter.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Verson on 2016/11/19.
 */
public final class DateUtil {

    public static Date convert2Date(String date,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String convert2String(Date date,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static Date startOfDay(Date date){
        if (date == null)
            throw new IllegalArgumentException("The date must not be null");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY,0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        c.set(Calendar.MILLISECOND,0);
        return c.getTime();
    }

    public static Date endOfDay(Date date){
        if (date == null)
            throw new IllegalArgumentException("The date must not be null");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY,23);
        c.set(Calendar.MINUTE,59);
        c.set(Calendar.SECOND,59);
        c.set(Calendar.MILLISECOND,999);
        return c.getTime();
    }

    public static Date add(Date date,int calendarField,int amout){
        if (date == null)
            throw new IllegalArgumentException("The date must not be null");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField,amout);
        return c.getTime();
    }

    public static Date addYears(Date date,int amount){
        return add(date,Calendar.YEAR,amount);
    }

    public static Date addMonths(Date date,int amout){
        return add(date,Calendar.MONTH,amout);
    }

    public static Date addDays(Date date,int amount){
        return add(date,Calendar.DAY_OF_MONTH,amount);
    }

    public static Date addHours(Date date,int amount){
        return add(date,Calendar.HOUR_OF_DAY,amount);
    }

    public static Date addMinutes(Date date,int amout){
        return add(date,Calendar.MINUTE,amout);
    }

    public static Date addSeconds(Date date,int amount){
        return add(date,Calendar.SECOND,amount);
    }

}
