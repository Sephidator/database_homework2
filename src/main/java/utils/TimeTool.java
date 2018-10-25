package utils;

import java.util.Calendar;
import java.util.Date;

public class TimeTool {
    //这个类不能实例化
    private TimeTool(){
    }

    public static Date now() {
        return new Date();
    }

    public static int getYear() {
        Calendar today = Calendar.getInstance();
        return today.get(Calendar.YEAR);
    }

    public static int getMonth() {
        Calendar today = Calendar.getInstance();
        return today.get(Calendar.MONTH) + 1;
    }

    public static Date lastDayOfMonth() {
        Calendar day = Calendar.getInstance();
        day.setTime(new Date());
        day.set(Calendar.DAY_OF_MONTH, day.getActualMaximum(Calendar.DAY_OF_MONTH));
        day.set(Calendar.HOUR_OF_DAY, 23);
        day.set(Calendar.MINUTE, 59);
        day.set(Calendar.SECOND, 59);
        return day.getTime();
    }
}
