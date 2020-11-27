package koreatech.cse.util;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class DateHelper {

    private DateHelper() {
    }

    public static Date now() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Egypt"));
        return calendar.getTime();
    }

    public static DateTime toDateTime(Date date) {
        return new DateTime(date);
    }


    public static String format(String dateFormat, Date date) {
        if(date == null || dateFormat == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("Egypt"));
        return sdf.format(date);
    }

    public static String format(Date date) {
        if(date == null) return "";
        return format("yyyy-MM-dd", date);
    }

    public static Date parse(String dateFormat, String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
            sdf.setTimeZone(TimeZone.getTimeZone("Egypt"));
            return sdf.parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date parse(String dateString) {
        try {
            return parse("yyyy-MM-dd", dateString);
        } catch (Exception e) {
            return null;
        }
    }

    public static SimpleDateFormat getDateFormat() {
        return getDateFormat("yyyy-MM-dd");
    }

    public static SimpleDateFormat getDateFormat(String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("Egypt"));
        return sdf;
    }

    public static int getDday(Date date) {
        Calendar todayCalendar = Calendar.getInstance();
        Calendar dDayCalendar = Calendar.getInstance();
        dDayCalendar.setTime(date);
        int toDayOfYear = todayCalendar.get(Calendar.DAY_OF_YEAR);
        int dDayOfYear = dDayCalendar.get(Calendar.DAY_OF_YEAR);
        if (dDayCalendar.get(Calendar.YEAR) > todayCalendar.get(Calendar.YEAR))
            dDayOfYear += (dDayCalendar.get(Calendar.YEAR) - todayCalendar.get(Calendar.YEAR)) * 365;
        else if (todayCalendar.get(Calendar.YEAR) > dDayCalendar.get(Calendar.YEAR))
            toDayOfYear += (todayCalendar.get(Calendar.YEAR) - dDayCalendar.get(Calendar.YEAR)) * 365;
        return -(dDayOfYear - toDayOfYear);
    }

    public static boolean isNowWithin(Date date) {
        return toDateTime(date).isAfterNow();
    }

    public static int getCurrentYear() {
        Calendar todayCalendar = Calendar.getInstance();
        return todayCalendar.get(Calendar.YEAR);
    }

}
