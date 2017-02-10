package com.kikiplus.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class TimeUtils {
	public static String getCurrentTime() {
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);

		String strHH = String.format("%02d", hour);
		String strMM = String.format("%02d", minute);
		String strSS = String.format("%02d", second);

		String date = getDateFormat("yyyymmdd", 0);
		String time = date + "_" + strHH + "" + strMM + "" + strSS + "";
		return time;
	}

	/**
	 * 패턴 형식으로 날짜 계산 후 형식에 맞는 날짜 반환하기
	 *
	 * @param pattern
	 *            yyyyMMdd
	 * @param day
	 * @return
	 */
	public static String getDateFormat(String pattern, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, day);
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");
		sdf.setTimeZone(tz);
		return sdf.format(calendar.getTime());
	}
}
