package com.emailim.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil
{
  public static String dateToStr(Date date)
  {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String str = sdf.format(date);return str;
  }
  
  public static Date parseDate(String dateStr, String format)
  {
    Date date = null;
    try
    {
      DateFormat df = new SimpleDateFormat(format);
      String dt = dateStr.replaceAll("-", "/");
      if ((!dt.equals("")) && (dt.length() < format.length())) {
        dt = dt + format.substring(dt.length()).replaceAll("[YyMmDdHhSs]", "0");
      }
      date = df.parse(dt);
    }
    catch (Exception localException) {}
    return date;
  }
  
  public static Date parseDate(String dateStr)
  {
    return parseDate(dateStr, "yyyy/MM/dd");
  }
  
  public static String format(Date date, String format)
  {
    String result = "";
    try
    {
      if (date != null)
      {
        DateFormat df = new SimpleDateFormat(format);
        result = df.format(date);
      }
    }
    catch (Exception localException) {}
    return result;
  }
  
  public static String format(Date date)
  {
    return format(date, "yyyy/MM/dd");
  }
  
  public static int getYear(Date date)
  {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    return c.get(1);
  }
  
  public static int getMonth(Date date)
  {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    return c.get(2) + 1;
  }
  
  public static int getDay(Date date)
  {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    return c.get(5);
  }
  
  public static int getHour(Date date)
  {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    return c.get(11);
  }
  
  public static int getMinute(Date date)
  {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    return c.get(12);
  }
  
  public static int getSecond(Date date)
  {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    return c.get(13);
  }
  
  public static long getMillis(Date date)
  {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    return c.getTimeInMillis();
  }
  
  public static String getDate(Date date)
  {
    return format(date, "yyyy/MM/dd");
  }
  
  public static String getTime(Date date)
  {
    return format(date, "HH:mm:ss");
  }
  
  public static String getDateTime(Date date)
  {
    return format(date, "yyyy/MM/dd HH:mm:ss");
  }
  
  public static Date addDate(Date date, int day)
  {
    Calendar c = Calendar.getInstance();
    c.setTimeInMillis(getMillis(date) + day * 24L * 3600L * 1000L);
    return c.getTime();
  }
  
  public static int diffDate(Date date, Date date1)
  {
    return (int)((getMillis(date) - getMillis(date1)) / 86400000L);
  }
  
  public static String getMonthBegin(String strdate)
  {
    Date date = parseDate(strdate);
    return format(date, "yyyy-MM") + "-01";
  }
  
  public static String getMonthEnd(String strdate)
  {
    Date date = parseDate(getMonthBegin(strdate));
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(2, 1);
    calendar.add(6, -1);
    return formatDate(calendar.getTime());
  }
  
  public static String formatDate(Date date)
  {
    return formatDateByFormat(date, "yyyy-MM-dd");
  }
  
  public static String formatDateByFormat(Date date, String format)
  {
    String result = "";
    if (date != null) {
      try
      {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        result = sdf.format(date);
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
    }
    return result;
  }
}