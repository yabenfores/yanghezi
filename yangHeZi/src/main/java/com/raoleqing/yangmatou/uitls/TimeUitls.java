package com.raoleqing.yangmatou.uitls;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUitls {
	
	// 把当前时间转换成“yyyy-MM-dd HH:mm:ss”
		public static String getNowDate() {

			String dateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			return dateString;
		}

		// 把当前时间转换成“yyyy-MM-dd”
		public static String getOnDate() {

			String dateString = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
			return dateString;
		}

		/** yyyy年MM月dd日 HH时 */
		public static String geeYearMonthRi() {
			String dateString = new SimpleDateFormat("yyyy年MM月dd日 HH时").format(System.currentTimeMillis());
			return dateString;
		}

		/**
		 * 将字符串数据转化为毫秒数
		 */
		public static long getMillisecond(String dateTime) {
			
			if(dateTime == null){
				return 0;
			}

			try {

				Calendar c = Calendar.getInstance();
				c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime));

				return c.getTimeInMillis();

			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return 0;
		}
		
		
		/**
		 * 返加格式yyyy-MM-dd hh:mm:ss HH:mm:ss"
		 * */
		public static String getDate(Long triem) {
			Date d = new Date(triem);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(d);
		}

}
