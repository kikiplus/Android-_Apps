package Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.util.Log;

/***
 * @Class Name : DateUtils
 * @Description : 날짜 관련 클래스
 * @since 2015. 1. 9.
 * @version 1.0
 */
public class DateUtils {

	public static final String getStringDateFormat(String pattern, Date date) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	public static void main(String[] args) {
		System.out.println(DateUtils.getStringDateFormat("yyyy.MM.dd HH:mm:ss", new Date()));
		System.out.println(DateUtils.getStringDateFormat("yyyy.MM.dd hh:mm", new Date()));
		System.out.println(DateUtils.getStringDateFormat("yy.MM.dd", new Date()));
	}

	/**
	 * @Description : 운행시간 체크하는 함수
	 * @Return 운행 시간 여불
	 */
	public static boolean checkTime() {
		Calendar cal = Calendar.getInstance();
		Date startTime = null; // 열차 운행 시간 새벽5시 30분 이후
		Date endTime = null; // 열차 종료 시간 새벽 1시 30분 이전까지
		Date currentTime = new Date(System.currentTimeMillis());

		cal.setTime(currentTime);
		int year = cal.get(1); // year
		int month = cal.get(2) + 1; // month
		int dd = cal.get(5); // day
		String yyyymm = year + "." + month + ".";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");

		try {
			startTime = sdf.parse(yyyymm + dd + START_TIME);
			Log.v(TAG, "startTime : " + yyyymm + dd + START_TIME);
			endTime = sdf.parse(yyyymm + (dd + 1) + END_TIME);
			Log.v(TAG, "endTime : " + yyyymm + (dd + 1) + END_TIME);

			Log.v(TAG, "currentTime : " + sdf.format(currentTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (currentTime.after(startTime) && currentTime.before(endTime)) {
			Log.v(TAG, "운행시간임");
			return true;
		} else {
			Log.v(TAG, "운행시간 아님");
			return false;
		}
	}

	private static final String START_TIME = " 05:30";
	private static final String END_TIME = " 01:00";
	private static final String TAG = "MYLIB";
}
