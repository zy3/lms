package com.red.lms.common.utils;

import com.red.lms.common.constants.Constants;
import com.red.lms.common.enums.CodeEnum;
import com.red.lms.common.exceptions.CommonException;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 */
@Slf4j
public class DateTimeUtils {

    public static Date initDateByDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static long getTimeStamp() {
        Date d = new Date();
        long timeStamp = d.getTime() / 1000;     //getTime()得到的是微秒， 需要换算成秒
        return timeStamp;
    }

    /**
     * 通过生日获取年龄
     *
     * @param birth
     * @return
     */
    public static Integer getAgeSimple(Date birth) {
        Calendar now = Calendar.getInstance();
        Calendar born = Calendar.getInstance();

        now.setTime(new Date());
        born.setTime(birth);

        if (born.after(now)) {
            return null;
        }

        int age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
        if (now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR)) {
            age -= 1;
        }
        return age;
    }

    /**
     * 通过生日获取年龄
     *
     * @param birth
     * @return
     */
    public static Integer getAge(Date birth) {
        Calendar now = Calendar.getInstance();
        Calendar born = Calendar.getInstance();

        now.setTime(new Date());
        born.setTime(birth);

        if (born.after(now)) {
            throw new CommonException(CodeEnum.BIZ_ERROR, "生日不可能在未来");
        }

        int age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
        if (now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR)) {
            age -= 1;
        }
        return age;
    }

    /**
     * 通过年龄计算出生日年份时间
     *
     * @param age
     * @return
     */
    public static Date getBornDate(Integer age) {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        int nowYear = now.get(Calendar.YEAR);
        int bornYear = nowYear - age;
        if (nowYear < bornYear) {
            bornYear += 1;
        }
        Calendar born = Calendar.getInstance();
        born.set(Calendar.YEAR, bornYear);
        return born.getTime();
    }

    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static String dateToStr(Date dateDate) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateDate);
    }

    public static String dateToStrPattern(Date dateDate, String pattern) {
        return new SimpleDateFormat(pattern).format(dateDate);
    }

    public static Date strToDate(String dateString) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString);
        } catch (Exception e) {
            log.error("strToDate error", e);
        }
        return null;
    }

    public static Date strToDatePattern(String dateString, String pattern) {
        try {
            return new SimpleDateFormat(pattern).parse(dateString);
        } catch (Exception e) {
            log.error("strToDate error", e);
        }
        return null;
    }


    /**
     * 获取当天0点
     *
     * @param date
     * @return
     */
    public static Date getZeroTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startTime = calendar.getTime();
        return startTime;
    }

    public static int diffDays(Date startTime, Date endTime) {
        long diff = endTime.getTime() - startTime.getTime();
        return (int) (diff / Constants.ONE_DAY_TIME);
        //TODO TODO
    }

}
