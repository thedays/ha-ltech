package com.ltech.smarthome.utils;

import com.blankj.utilcode.util.StringUtils;
import com.ltech.smarthome.R;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/* loaded from: classes4.dex */
public class DateUtils {
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_DATE_TIME_SECOND = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_MONTH_DAY_TIME = "MM-dd HH:mm";
    public static final String FORMAT_TIME = "HH:mm";

    public static String getFormatToday(String dateFormat) {
        return new SimpleDateFormat(dateFormat).format(new Date());
    }

    public static Date stringToDate(String dateStr, String dateFormat) {
        try {
            return new SimpleDateFormat(dateFormat).parse(dateStr);
        } catch (ParseException unused) {
            return null;
        }
    }

    public static String dateToString(Date date, String dateFormat) {
        return new SimpleDateFormat(dateFormat).format(date);
    }

    public static String getChatTime(boolean hasYear, long timesamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
        int parseInt = Integer.parseInt(simpleDateFormat.format(new Date(System.currentTimeMillis()))) - Integer.parseInt(simpleDateFormat.format(new Date(timesamp)));
        if (parseInt == 0) {
            return "今天 " + getHourAndMin(timesamp);
        }
        if (parseInt == 1) {
            return "昨天 " + getHourAndMin(timesamp);
        }
        if (parseInt == 2) {
            return "前天 " + getHourAndMin(timesamp);
        }
        return getTime(hasYear, timesamp);
    }

    private static String getTime(boolean hasYear, long time) {
        String str;
        if (hasYear) {
            str = FORMAT_DATE_TIME;
        } else {
            str = FORMAT_MONTH_DAY_TIME;
        }
        return new SimpleDateFormat(str).format(new Date(time));
    }

    private static String getHourAndMin(long time) {
        return new SimpleDateFormat(FORMAT_TIME).format(new Date(time));
    }

    public static String getWeekOfDate(Date date) {
        String[] strArr = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int i = calendar.get(7) - 1;
        if (i < 0) {
            i = 0;
        }
        return strArr[i];
    }

    public static int getOffsetDay(long date1, long date2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(date2);
        int i = calendar.get(1);
        int i2 = calendar2.get(1);
        int i3 = calendar.get(6);
        int i4 = calendar2.get(6);
        int i5 = i - i2;
        if (i5 > 0) {
            return (i3 - i4) + calendar2.getActualMaximum(6);
        }
        if (i5 >= 0) {
            return i3 - i4;
        }
        return (i3 - i4) - calendar.getActualMaximum(6);
    }

    public static int getOffsetHour(long date1, long date2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(date2);
        return (calendar.get(11) - calendar2.get(11)) + (getOffsetDay(date1, date2) * 24);
    }

    public static int getOffsetMinutes(long date1, long date2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(date2);
        return (calendar.get(12) - calendar2.get(12)) + (getOffsetHour(date1, date2) * 60);
    }

    private static String getDayOfWeek(String format, int calendarField) {
        try {
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            int i = gregorianCalendar.get(7);
            if (i == calendarField) {
                return simpleDateFormat.format(gregorianCalendar.getTime());
            }
            int i2 = calendarField - i;
            if (calendarField == 1) {
                i2 = 7 - Math.abs(i2);
            }
            gregorianCalendar.add(5, i2);
            return simpleDateFormat.format(gregorianCalendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getFirstDayOfMonth(String format) {
        try {
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            gregorianCalendar.set(5, 1);
            return simpleDateFormat.format(gregorianCalendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getLastDayOfMonth(String format) {
        try {
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            gregorianCalendar.set(5, 1);
            gregorianCalendar.roll(5, -1);
            return simpleDateFormat.format(gregorianCalendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getWeekOfDateNum(Date date) {
        String[] strArr = {"7", "1", "2", "3", "4", "5", "6"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int i = calendar.get(7) - 1;
        if (i < 0) {
            i = 0;
        }
        return strArr[i];
    }

    public static String getStrTime2(Integer time) {
        DecimalFormat decimalFormat = new DecimalFormat("00");
        if (time == null || time.intValue() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int intValue = time.intValue() / 3600;
        if (intValue != 0) {
            sb.append(decimalFormat.format(intValue));
            sb.append(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR);
        }
        sb.append(decimalFormat.format((time.intValue() - (intValue * 3600)) / 60));
        sb.append(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR);
        sb.append(decimalFormat.format((time.intValue() - r2) - (r4 * 60)));
        return sb.toString();
    }

    public static String getStrTime3(Integer time) {
        DecimalFormat decimalFormat = new DecimalFormat("00");
        if (time == null || time.intValue() == 0) {
            return "";
        }
        int intValue = time.intValue() / 3600;
        int i = intValue * 3600;
        int intValue2 = (time.intValue() - i) / 60;
        return decimalFormat.format(intValue) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + decimalFormat.format(intValue2) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + decimalFormat.format((time.intValue() - i) - (intValue2 * 60));
    }

    public static String millisToStr(long time) {
        if (time <= 0) {
            return "0 秒";
        }
        if (time < 1000) {
            return (time / 1000.0f) + "秒";
        }
        return getStrTime(Integer.valueOf(((int) time) / 1000));
    }

    public static String getStrTime(Integer time) {
        if (time == null || time.intValue() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int intValue = time.intValue() / 3600;
        if (intValue != 0) {
            sb.append(intValue);
            sb.append(StringUtils.getString(R.string.hour));
        }
        int i = intValue * 3600;
        int intValue2 = (time.intValue() - i) / 60;
        if (intValue2 != 0) {
            sb.append(intValue2);
            sb.append(StringUtils.getString(R.string.min_new));
        }
        int intValue3 = (time.intValue() - i) - (intValue2 * 60);
        if (intValue3 != 0) {
            sb.append(intValue3);
            sb.append(StringUtils.getString(R.string.sec));
        }
        return sb.toString();
    }
}