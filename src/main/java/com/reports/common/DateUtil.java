package com.reports.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static final String YYYY_MM_DD = "yyyy-MM-dd";

	public static final String YYYY_MM_DD_hhmmsss = "yyyy-MM-dd-HH-mm-sss";

	public static final String YYYY_MM_DD_hh_mm_ss = "yyyy-MM-dd HH:mm:ss";

	public static final String YYYYMMDD = "yyyyMMdd";

	public static final String YYYYMMDD_ZH = "yyyy年MM月dd日";

	public static final int FIRST_DAY_OF_WEEK = Calendar.MONDAY; // 中国周一是一周的第一天
	public static final String YYYYMMDDHHmmssSSS = "yyyyMMddHHmmssSSS";

	/**
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date parseDate(String strDate) {
		return parseDate(strDate, null);
	}

	/**
	 * 
	 * @param date
	 *            yyyymmdd
	 * @return
	 */
	public static String parseDate(Long date) {
		if (Long.toString(date).length() == 8) {
			String str = Long.toString(date);
			return str.substring(0, 4) + "年" + str.substring(4, 6) + "月" + str.substring(6) + "日";
		}
		return "";
	}

	/**
	 * parseDate
	 * 
	 * @param strDate
	 * @param pattern
	 * @return
	 */
	public static Date parseDate(String strDate, String pattern) {
		Date date = null;
		try {
			if (pattern == null) {
				pattern = YYYY_MM_DD;
			}
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			date = format.parse(strDate);
		} catch (Exception e) {
		}
		return date;
	}

	/**
	 * format date
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return formatDate(date, null);
	}

	/**
	 * format date
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		String strDate = null;
		try {
			if (pattern == null) {
				pattern = YYYY_MM_DD;
			}
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			strDate = format.format(date);
		} catch (Exception e) {
		}
		return strDate;
	}

	/**
	 * format date
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDateTime(Date date) {
		return formatDateTime(date, null);
	}

	public static String formatDateTime(Long date) {
		return formatDateTime(new Date(date), null);
	}

	/**
	 * format date
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDateTime(Date date, String pattern) {
		String strDate = null;
		try {
			if (pattern == null) {
				pattern = YYYY_MM_DD_hh_mm_ss;
			}
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			strDate = format.format(date);
		} catch (Exception e) {
		}
		return strDate;
	}

	/**
	 * 获取当前时间
	 * 
	 * @return yyyymmdd
	 */
	public static final Integer getCurrentDate() {
		String strDate = null;
		Integer date = 0;
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			strDate = format.format(new Date());
			date = Integer.parseInt(strDate);
		} catch (Exception e) {
		}
		return date;

	}

	/**
	 * 获取当前时间字符串
	 * 
	 * @param pattern
	 *            给定时间格式化表达式eg:yyyyMMddHHmmssSSS
	 * @return
	 */
	public static String getNowDateTime(String pattern) {
		return formatDateTime(new Date(), pattern);
	}

}
