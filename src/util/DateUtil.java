package util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.time.DateFormatUtils;

import com.eltima.components.ui.DatePicker;

/**
 * 工具类 DateUtil 时间
 */

public class DateUtil {
    //定义一天的时间
    private static final long millisecondsOfOneDay = 1000 * 60 * 60 * 24;
    //获取日历
    private static Calendar c = Calendar.getInstance();

    /**
     * @param d java.util.Date
     * @return java.sql.Date 去掉了时分秒
     */
    public static java.sql.Date util2sql(java.util.Date d) {
        return new java.sql.Date(d.getTime());
    }

    /**
     * @return 今天0点的时间
     */
    public static Date today() {
        c.setTime(new Date());
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * @return 月初0点的时间
     */
    public static Date monthBegin() {
        c.setTime(new Date());
        c.set(Calendar.DATE, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date monthEnd() {
        return monthEnd(monthBegin());
    }

    /**
     * @param start 月初时间
     * @return 月末0点的时间
     */
    public static Date monthEnd(Date start) {
        c.setTime(start);
        c.set(Calendar.DATE, 1);
        c.add(Calendar.MONTH, 1);
        c.add(Calendar.DATE, -1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * @return 本月天数
     */
    public static int thisMonthTotalDay() {
        c.setTime(new Date());
        monthEnd();
        return c.get(Calendar.DATE);
    }

    /**
     * @return 本月剩余天数
     */
    public static int thisMonthLeftDay() {
        int TotalDay = thisMonthTotalDay();
        today();
        int today = c.get(Calendar.DATE);
        return TotalDay - today + 1;
    }

    /**
     * @return 本年年份数
     */
    public static int thisYear() {
        today();
        return c.get(Calendar.YEAR);
    }

    /**
     * @return 本年月份数
     */
    public static int thisMonth() {
        today();
        return c.get(Calendar.MONTH);
    }

    /**
     * 
     * @return 当前日期
     */
    public static String getDate() {
		return DateFormatUtils.format(new Date(), "yyyy-MM-dd");
	}
    
    /**
     * 
     * @return 当前时间
     */
    public static String getTime() {
		return DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
	}
    
    /**
     * 判断起始时间是否早于结束时间
     * @param startTime
     * @param endTime
     * @return
     * @throws ParseException 
     */
    public static boolean	checkTime(String startTime,String endTime) throws ParseException{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.parse(startTime).before(formatter.parse(endTime));
	}

    public static DatePicker getDatePicker() {
        final DatePicker datepick;
        // 格式
        String DefaultFormat = "yyyy-MM-dd HH:mm:ss";
        // 当前时间
        Date date = new Date();
        // 字体
        Font font = new Font("Times New Roman", Font.BOLD, 14);

        Dimension dimension = new Dimension(177, 24);

        int[] hilightDays = { 1, 3, 5, 7 };

        int[] disabledDays = { 4, 6, 5, 9 };
    //构造方法（初始时间，时间显示格式，字体，控件大小）
        datepick = new DatePicker(date, DefaultFormat, font, dimension);

        datepick.setLocation(137, 83);//设置起始位置
        /*
        //也可用setBounds()直接设置大小与位置
        datepick.setBounds(137, 83, 177, 24);
        */
        // 设置一个月份中需要高亮显示的日子
        datepick.setHightlightdays(hilightDays, Color.red);
        // 设置一个月份中不需要的日子，呈灰色显示
        datepick.setDisableddays(disabledDays);
        // 设置国家
        datepick.setLocale(Locale.CANADA);
        // 设置时钟面板可见
        datepick.setTimePanleVisible(true);
        return datepick;
    }
}
