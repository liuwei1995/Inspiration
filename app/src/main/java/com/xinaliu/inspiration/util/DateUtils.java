package com.xinaliu.inspiration.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	/**
	 * @param createTime
	 *            2015-09-29 11:15:22.0
	 * @return 2015-09-29
	 */
	public static String TimeConversion(String createTime) {
		String Time = createTime.substring(0, 10);
		return Time;
	}

	/**
	 * @param createTime
	 *            2015-09-29 11:15:22.0
	 * @return 2015-09-29
	 */
	public static String TimeConversion(Long createTime) {
		String createTi = createTime + "";
		String Time = createTi.substring(0, 10);
		return Time;
	}

	/**
	 * @param cr
	 *            2015-09-29 11:15:22.0
	 * @return 09-29
	 */
	public static String Birthday(Long cr) {
		final String createTime = cr + "";
		String time = createTime.substring(5, 10);
		return time;
	}

	/**
	 * @param createTime
	 *            2015-09-29 11:15:22.0
	 * @return 09-29
	 */
	public static String Birthday(String createTime) {
		String Time = createTime.substring(0, 10);
		return Time;
	}

	/***
	 * 2015-09-29 2015-9-29
	 * 
	 * @param createTime1
	 * 
	 * @return Age
	 */
	public static int getAge(String createTime1) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String createTime = "2015-01-01";
		try {
		if (createTime1==null) {
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			createTime = date.format(new Date());
		}else {
			createTime = dateFormat.format(dateFormat.parse(createTime1));
		}
			String Time = TimeConversion(createTime);
			Date date = dateFormat.parse(Time);
			int age2 = DateUtils.getAge(date);
			return age2;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/***
	 * 20150929 变成 2015-9-29
	 * 
	 * @param date
	 * 
	 * @return String
	 */
	public static Date GetDateFormat(String date) {
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		String format2;
		try {
			String format = dateFormat1.format(dateFormat1.parse(date));
			format2 = dateFormat2.format(dateFormat2.parse(format));
			Date date2 = new Date(format2);
			return date2;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 计算年龄
	 * 
	 * @param dateOfBirth
	 * @return
	 */
	public static int getAge(Date dateOfBirth) {
		int age = 0;
		Calendar born = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		if (dateOfBirth != null) {
			now.setTime(new Date());
			born.setTime(dateOfBirth);
			if (born.after(now)) {
				throw new IllegalArgumentException(
						"Can't be born in the future");
			}
			age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
			if (now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR)) {
				age -= 1;
			}
		}
		return age;
	}
	
	

	
	
	/** 日期格式 */
	private final static ThreadLocal<SimpleDateFormat> dateFormat = new ThreadLocal<SimpleDateFormat>(){
	    protected SimpleDateFormat initialValue() {
		return new SimpleDateFormat("yyyy-MM-dd");
	    }
	};
	
	/** 时间格式 */
	private final static ThreadLocal<SimpleDateFormat> timeFormat = new ThreadLocal<SimpleDateFormat>(){
	    protected SimpleDateFormat initialValue() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    }
	};
	
	/**  
	 * 获取当前时间:Date
	 */
	public static Date getDate(){
		return new Date();
	}
	
	/**  
	 * 获取当前时间:Calendar
	 */
	public static Calendar getCal(){
		return Calendar.getInstance();
	}

	/**  
	 * 日期转换为字符串:yyyy-MM-dd
	 */
	public static String dateToStr(Date date){
		if(date != null)
			return dateFormat.get().format(date);
		return null;
	}
	
	/**  
	 * 时间转换为字符串:yyyy-MM-dd HH:mm:ss
	 */
	public static String timeToStr(Date date){
		if(date != null)
			return timeFormat.get().format(date);
		return null;
	}
	
	/**  
	 * 字符串转换为日期:yyyy-MM-dd
	 */
	public static Date strToDate(String str){
		Date date = null;
		try {
			date = dateFormat.get().parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**  
	 * 字符串转换为时间:yyyy-MM-dd HH:mm:ss
	 */
	public static Date strToTime(String str){
		Date date = null;
		try {
			date = timeFormat.get().parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	public static Date longToTime(String str){
		Date date = null;
		try {
			long str1 = Long.valueOf(str);
//			date = timeFormat.get().parse(str);
			date = new Date(str1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**  
	 * 友好的方式显示时间
	 */
	public static String friendlyFormat(String str){
		Date date = longToTime(str);
		if(date == null)
			return ":)";
		Calendar now = getCal();
		String time = new SimpleDateFormat("HH:mm").format(date);
		
		// 第一种情况，日期在同一天
		String curDate = dateFormat.get().format(now.getTime());
		String paramDate = dateFormat.get().format(date);
		if(curDate.equals(paramDate)){
			int hour = (int) ((now.getTimeInMillis() - date.getTime()) / 3600000);
			if(hour > 0)
				return time;
			int minute = (int) ((now.getTimeInMillis() - date.getTime()) / 60000);
			if (minute < 2)
				return "刚刚";
			if (minute > 30)
				return "半个小时以前";
			return minute + "分钟前";
		}
		
		// 第二种情况，不在同一天
		long time2 = getBegin(getDate()).getTime();
		long time3 = getBegin(date).getTime();
		int days = (int) ((time2-time3) / 86400000 );
		if(days == 1 )
			return "昨天 "+time;
		if(days == 2)
			return "前天 "+time;
		if(days <= 7)
			return days + "天前";
		return dateToStr(date);
	}
	
	/**  
	 * 返回日期的0点:2012-07-07 20:20:20 --> 2012-07-07 00:00:00
	 */
	public static Date getBegin(Date date){
		return strToTime(dateToStr(date)+" 00:00:00");
	}
	
	public static String getFormatDate(Object data) {
		if(data == null)return ":)";
		if(data instanceof Date){
			return friendlyFormat(((Date) data).getTime()+"");
		}
		String data1 = data.toString();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date parse = sdf.parse(data1);
			long time = parse.getTime();
			return friendlyFormat(time+"");
		} catch (ParseException e) {
			e.printStackTrace();
			try {
				Date parse = DateFormat.getInstance().parse(data1);
				return friendlyFormat(parse.getTime()+"");
			} catch (ParseException e1) {
				e1.printStackTrace();
				return friendlyFormat(data1+"");
			}
		}
	}
	
}
