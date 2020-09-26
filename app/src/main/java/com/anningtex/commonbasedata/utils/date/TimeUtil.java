package com.anningtex.commonbasedata.utils.date;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author alvis
 */
public class TimeUtil {
    private String pattern = "yyyy-MM-dd";

    private static class Holder {
        private static TimeUtil instance = new TimeUtil();
    }

    public static TimeUtil getInstance() {
        return Holder.instance;
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @param format
     * @return
     */
    public String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        // 设置北京时区
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }

    public long dateToStamp() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = null;
        try {
            date = dateFormat.parse(getCurrentTime("yyyy-MM-dd HH:mm:ss"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime() / 1000;
    }

    /**
     * 获取时区
     *
     * @return
     */
    public String getTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        return tz.getDisplayName(false, TimeZone.SHORT);
    }

    /**
     * 将 int 转换为  String
     *
     * @param time
     * @return
     */
    public String int2StringDate(int time) {
        SimpleDateFormat sdr = new SimpleDateFormat(pattern);
        String times = sdr.format(new Date(time * 1000L));
        return times;
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    public String getCurrentTime(String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    /**
     * 将 String 转换成 date
     *
     * @param strDate
     * @return
     */
    public Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 将date 转换成 long
     *
     * @param date
     * @return 毫秒
     */
    public long dateToLong(Date date) {
        return date.getTime();
    }

    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    public Date stringToDate(String strTime, String formatType) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public Date longToDate(long currentTime, String formatType) {
        // 根据long类型的毫秒数生命一个date类型的时间
        Date dateOld = new Date(currentTime);
        String sDateTime = dateToString(dateOld, formatType);
        // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType);
        // 把String类型转换为Date类型
        return date;
    }

    /**
     * currentTime要转换的long类型的时间
     * formatType要转换的string类型的时间格式
     *
     * @param currentTime
     * @param formatType
     * @return
     */
    public String longToString(long currentTime, String formatType) {
        // long类型转成Date类型
        Date date = longToDate(currentTime, formatType);
        // date类型转成String
        String strTime = dateToString(date, formatType);
        return strTime;
    }

    /**
     * 获取前n天日期、后n天日期
     *
     * @param distanceDay 前几天 如获取前7天日期则传-7即可；如果后7天则传7
     * @return
     */
    public String getPastDate(int distanceDay) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) + distanceDay);
        Date endDate = null;
        try {
            endDate = dft.parse(dft.format(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dft.format(endDate);
    }

    public String getPastMonth(int distanceMonth) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.MONTH, date.get(Calendar.MONTH) + distanceMonth);
        Date endDate = null;
        try {
            endDate = dft.parse(dft.format(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dft.format(endDate);
    }

    private long getTimeMillis(String strTime) {
        long returnMillis = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = sdf.parse(strTime);
            returnMillis = d.getTime();
        } catch (ParseException e) {
            Log.e("DD", e.toString());
        }
        return returnMillis;
    }

    public long getTimeHours(String startTime, String endTime) {
        //传入字串类型 2016/06/28 08:30
        //获取开始时间毫秒数
        long longStart = getTimeMillis(startTime);
        //获取结束时间毫秒数
        long longEnd = getTimeMillis(endTime);
        //获取时间差（除以1000   转秒）
        long longExpend = longEnd - longStart;

        long longHours = longExpend / (60 * 60 * 1000); //根据时间差来计算小时数
        long longMinutes = (longExpend - longHours * (60 * 60 * 1000)) / (60 * 1000);   //根据时间差来计算分钟数
        return longExpend / 1000;
    }


    public int selectPosition = -1;

    public int getSelectPosition() {
        return selectPosition;
    }


    /**
     * 获取当前日期一周的日期
     *
     * @param date
     * @return
     */
    public ArrayList<DateEntity> getWeek(String date) {
        ArrayList<DateEntity> result = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        try {
            SimpleDateFormat sdr = new SimpleDateFormat(pattern);
            cal.setTime(sdr.parse(date));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //获取本周一的日期
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        for (int i = 0; i < 7; i++) {
            DateEntity entity = new DateEntity();
            entity.date = getValue(cal.get(Calendar.YEAR)) + "-" + getValue(cal.get(Calendar.MONTH) + 1) + "-" + getValue(cal.get(Calendar.DATE));
            entity.million = cal.getTimeInMillis();
            entity.day = getValue(cal.get(Calendar.DATE));
            entity.weekNum = cal.get(Calendar.DAY_OF_WEEK);
            entity.weekName = getWeekName(entity.weekNum);
            entity.isToday = isToday(entity.date);
            cal.add(Calendar.DATE, 1);
            result.add(entity);
        }
        return result;
    }

    /**
     * 获取当前日期一月的日期
     *
     * @param date
     * @return
     */
    public ArrayList<DateEntity> getMonth(String date) {
        ArrayList<DateEntity> result = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(new SimpleDateFormat("yyyy-MM").parse(date));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int max = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= max; i++) {
            DateEntity entity = new DateEntity();
            entity.date = getValue(cal.get(Calendar.YEAR)) + "-" + getValue(cal.get(Calendar.MONTH) + 1) + "-" + getValue(cal.get(Calendar.DATE));
            entity.million = cal.getTimeInMillis();
            entity.weekNum = cal.get(Calendar.DAY_OF_WEEK);
            entity.day = getValue(cal.get(Calendar.DATE));
            entity.weekName = getWeekName(entity.weekNum);
            entity.isToday = isToday(entity.date);
            entity.luna = getLuna(entity.date);
            cal.add(Calendar.DATE, 1);
            result.add(entity);
        }
        //为了用空的值填补第一个之前的日期
        //先获取在本周内是周几
        int weekNum = result.get(0).weekNum - 1;
        for (int j = 0; j < weekNum; j++) {
            DateEntity entity = new DateEntity();
            result.add(0, entity);
        }
        for (int i = 0; i < result.size(); i++) {
            if (date.equals(result.get(i).date)) {
                selectPosition = i;
            }
        }
        return result;

    }

    /**
     * 根据美式周末到周一 返回
     *
     * @param weekNum
     * @return
     */
    private String getWeekName(int weekNum) {
        String name = "";
        switch (weekNum) {
            case 1:
                name = "星期日";
                break;
            case 2:
                name = "星期一";
                break;
            case 3:
                name = "星期二";
                break;
            case 4:
                name = "星期三";
                break;
            case 5:
                name = "星期四";
                break;
            case 6:
                name = "星期五";
                break;
            case 7:
                name = "星期六";
                break;
            default:
                break;
        }
        return name;
    }

    /**
     * 是否是今天
     *
     * @param sdate
     * @return
     */
    public boolean isToday(String sdate) {
        boolean b = false;
        Date time = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            time = dateFormat.parse(sdate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater.get().format(today);
            String timeDate = dateFormater.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 个位数补0操作
     *
     * @param num
     * @return
     */
    public String getValue(int num) {
        return String.valueOf(num > 9 ? num : ("0" + num));
    }

    private ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    /**
     * 获取系统当前日期
     */
    public String getCurrDate(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 格式化日期
     */
    public String formatDate(String date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date curDate = null;//获取当前时间
        try {
            curDate = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 切换周的时候用
     * 获取前/后 几天的一个日期
     *
     * @param currentData
     * @param dayNum
     * @return
     */
    public String getSomeDays(String currentData, int dayNum) {
        Calendar c = Calendar.getInstance();
        //过去七天
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            c.setTime(dateFormat.parse(currentData));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, dayNum);
        Date d = c.getTime();
        String day = dateFormat.format(d);
        return day;
    }

    /**
     * 获取前/后 几个月的一个日期  切换月的时候用
     *
     * @param currentData
     * @param monthNum
     * @return
     */
    public String getSomeMonthDay(String currentData, int monthNum) {
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(new SimpleDateFormat("yyyy-MM").parse(currentData));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) + monthNum);
        Date day = c.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(day);
    }

    /**
     * 获取阴历
     *
     * @param date
     * @return
     */
    public String getLuna(String date) {
        Calendar today = Calendar.getInstance();
        try {
            today.setTime(Lunar.chineseDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Lunar(today).toString();
    }


}
