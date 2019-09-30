package top.chendawei.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Slf4j
public class DateUtils {
    public static final long SECOND = 1000L;
    public static final long MINUTE = 60000L;
    public static final long HOUR = 3600000L;
    public static final long DAY = 86400000L;
    public static final long WEEK = 604800000L;
    public static final long MONTH = 2592000000L;
    public static final long YEAR = 31536000000L;
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    private static SimpleDateFormat sdf = new SimpleDateFormat();

    public static String[] getAvailableMonth(int begin, int end) {
        int numberOfMonth = end - begin;

        int index = Math.abs(numberOfMonth) + 1;

        String[] strMonth = new String[index];

        int roll = end > begin ? begin - 1 : end - 1;
        try {
            for (int i = 1; i <= index; i++) {
                Calendar calendar = Calendar.getInstance();

                calendar.add(2, roll + i);

                java.util.Date now = calendar.getTime();

                strMonth[(i - 1)] = getDateString(now, "yyyyMM");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strMonth;
    }

    public static String getDateString(java.util.Date date, String formatPattern) {
        if (date == null) {
            return "";
        }
        if ((formatPattern == null) || (formatPattern.equals(""))) {
            formatPattern = "yyyy-MM-dd";
        }
        sdf.applyPattern(formatPattern);

        return sdf.format(date);
    }

    public static String getDateString(java.util.Date date) {
        return getDateString(date, null);
    }

    public static String getDateTimeString(java.util.Date date, String formatPattern) {
        if (date == null) {
            return "";
        }
        if ((formatPattern == null) || (formatPattern.equals(""))) {
            formatPattern = "yyyy-MM-dd HH:mm:ss";
        }
        sdf.applyPattern(formatPattern);

        return sdf.format(date);
    }

    public static String getDateTimeString(java.util.Date date) {
        return getDateTimeString(date, null);
    }

    public static java.util.Date getStringDate(String date, String formatPattern) {
        try {
            if ((formatPattern == null) || (formatPattern.equals(""))) {
                formatPattern = "yyyy-MM-dd";
            }
            sdf.applyPattern(formatPattern);

            return sdf.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static java.util.Date getStringDateTime(String date, String formatPattern) {
        try {
            if ((formatPattern == null) || (formatPattern.equals(""))) {
                formatPattern = "yyyy-MM-dd HH:mm:ss";
            }
            sdf.applyPattern(formatPattern);

            return sdf.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static java.util.Date getStringDateTime(String date) {
        return getStringDateTime(date, null);
    }

    public static java.util.Date getStringDate(String date) {
        return getStringDate(date, null);
    }

    public static long getTimeByUnit(String unit)
            throws Exception {
        switch (unit.charAt(0)) {
            case 'h':
                return 3600000L;
            case 'd':
                return 86400000L;
            case 'w':
                return 604800000L;
            case 'm':
                return 60000L;
            case 'n':
                return 2592000000L;
            case 'y':
                return 31536000000L;
            case 's':
                return 1000L;
        }
        throw new IllegalArgumentException("unknown time unit");
    }

    public static long getTime(String timestr)
            throws Exception {
        int begin = timestr.indexOf("{");
        int end = timestr.indexOf("}");
        String timeUnit = timestr.substring(begin + 1, end);
        int time = Integer.parseInt(timestr.substring(end + 1));

        return getTimeByUnit(timeUnit) * time;
    }

    public static java.util.Date changeDay(java.util.Date d, int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(6, calendar.get(6) + offset);
        return calendar.getTime();
    }

    public static java.util.Date getFirstQueryDate(java.util.Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        return calendar.getTime();
    }

    public static java.util.Date getLastQueryDate(java.util.Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.add(5, 1);
        return calendar.getTime();
    }

    public static java.util.Date getLastEndDate(java.util.Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(11, 23);
        calendar.set(12, 59);
        calendar.set(13, 59);

        return calendar.getTime();
    }

    public static String getCurrDate(String dateFormat) {
        java.util.Date date = new java.util.Date();
        if (dateFormat == null) {
            dateFormat = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat myDateFormat = new SimpleDateFormat(dateFormat);
        return myDateFormat.format(date);
    }

    public static String getMonth(java.util.Date date) {
        SimpleDateFormat myDateFormat = new SimpleDateFormat("MM");
        return myDateFormat.format(date);
    }

    public static java.util.Date getCurrentDate() {
        return new java.util.Date();
    }

    public static Timestamp getCurrDateTime() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static java.sql.Date getCurrDate() {
        return new java.sql.Date(System.currentTimeMillis());
    }

    public static String getCurrYear() {
        return getCurrDate("yyyy");
    }

    public static String getCurrMonth() {
        return getCurrDate("MM");
    }

    public static String dateToString(java.util.Date date) {
        SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return myDateFormat.format(date);
    }

    public static String dateToString(java.util.Date date, String format) {
        SimpleDateFormat myDateFormat = new SimpleDateFormat(format);
        return myDateFormat.format(date);
    }

    public static java.sql.Date stringToDate(String strDate) {
        java.sql.Date date = null;
        try {
            date = java.sql.Date.valueOf(strDate);
        } catch (Exception localException) {
        }
        return date;
    }

    public static java.util.Date stringToUtilDate(String strDate) {
        java.util.Date date = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = df.parse(strDate);
        } catch (Exception localException) {
        }
        return date;
    }

    public static java.util.Date stringToUtilDate(String strDate, String format) {
        java.util.Date date = null;
        SimpleDateFormat df = new SimpleDateFormat(format);
        try {
            date = df.parse(strDate);
        } catch (Exception localException) {
        }
        return date;
    }

    public static String addMonth(int offset, String dateFormat) {
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.add(2, offset);
        return df.format(calendar.getTime());
    }

    public static java.util.Date addMonth(int offset, java.util.Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(2, offset);
        return calendar.getTime();
    }

    public static java.util.Date addHOUR(int offset, java.util.Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(10, offset);
        return calendar.getTime();
    }

    public static java.util.Date addSECOND(int offset, java.util.Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(13, offset);
        return calendar.getTime();
    }

    public static java.util.Date addMINUTE(int offset, java.util.Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(12, offset);
        return calendar.getTime();
    }

    public static java.util.Date addYear(int offset, java.util.Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(1, offset);
        return calendar.getTime();
    }

    public static java.util.Date addDay(int offset, java.util.Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(6, offset);
        return calendar.getTime();
    }

    public static int minusWeek(java.util.Date startDate, java.util.Date endDate) {
        if (startDate.getTime() >= endDate.getTime()) {
            return 0;
        }
        long startDay = (startDate.getTime() + 345600000L) / 604800000L;
        long endDay = (endDate.getTime() + 345600000L) / 604800000L;

        long result = endDay - startDay + 1L;

        return new Long(result).intValue();
    }

    public static double minusYear(java.util.Date startDate, java.util.Date endDate) {
        if (startDate.getTime() >= endDate.getTime()) {
            return 0.0D;
        }
        double startDay = (startDate.getTime() + 345600000L) / 3.1536E10D;
        double endDay = (endDate.getTime() + 345600000L) / 3.1536E10D;

        double result = endDay - startDay;

        return new Double(result).doubleValue();
    }

    public static double minusMonth(java.util.Date startDate, java.util.Date endDate) {
        if (startDate.getTime() >= endDate.getTime()) {
            return 0.0D;
        }
        double startDay = (startDate.getTime() + 345600000L) / 2.592E9D;
        double endDay = (endDate.getTime() + 345600000L) / 2.592E9D;

        double result = endDay - startDay;

        return new Double(result).doubleValue();
    }

    public static int minusDay(java.util.Date startDate, java.util.Date endDate) {
        if (startDate.getTime() > endDate.getTime()) {
            return -1;
        }
        double startDay = (startDate.getTime() + 345600000L) / 8.64E7D;
        double endDay = (endDate.getTime() + 345600000L) / 8.64E7D;

        double result = endDay - startDay;

        return new Double(result).intValue();
    }

    public static double minusHour(java.util.Date startDate, java.util.Date endDate) {
        if (startDate.getTime() >= endDate.getTime()) {
            return 0.0D;
        }
        double startDay = (startDate.getTime() + 345600000L) / 3600000.0D;
        double endDay = (endDate.getTime() + 345600000L) / 3600000.0D;

        double result = endDay - startDay;

        return new Double(result).doubleValue();
    }

    public static double minusMinute(java.util.Date startDate, java.util.Date endDate) {
        if (startDate.getTime() >= endDate.getTime()) {
            return 0.0D;
        }
        double startDay = (startDate.getTime() + 345600000L) / 60000.0D;
        double endDay = (endDate.getTime() + 345600000L) / 60000.0D;

        double result = endDay - startDay;

        return new Double(result).doubleValue();
    }

    public static boolean checkTime(Long ts, int sec) {
        if (ts == null) {
            return false;
        }
        java.util.Date d = addMINUTE(-sec, getCurrentDate());
        if (d != null) {
            log.debug("d.getTime()  is " + d.getTime() + ",ts is " + ts + ",current time is " + getCurrentDate().getTime());
            return d.getTime() < ts.longValue();
        }
        return false;
    }

    public static String getDatePlusHour(String date, String format, String format2, int m) {
        java.util.Date d = getStringDateTime(date, format);
        java.util.Date d2 = addHOUR(m, d);
        return getDateString(d2, format2);
    }

    public static String getfirstDayOfThisMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal_1 = Calendar.getInstance();
        cal_1.add(2, 0);
        cal_1.set(5, 1);
        return format.format(cal_1.getTime());
    }

    public static boolean isOutMinus(String lockedTime, int i) {
        if (StringUtils.isBlank(lockedTime)) {
            return true;
        }
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        java.util.Date d = getStringDate(lockedTime, dateFormat);
        java.util.Date d2 = addMINUTE(-i, getCurrentDate());
        return d.compareTo(d2) <= 0;
    }

    public static String getDateStringPlusMinus(int i) {
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        java.util.Date d2 = addMINUTE(i, getCurrentDate());

        return getDateTimeString(d2, dateFormat);
    }
}
