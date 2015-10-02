package com.kiki.android.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/***
 * @Class Name : DateUtils
 * @Description : 날짜 관련 클래스
 * @since 2015. 1. 9.
 * @version 1.0
 */
public class DateUtils {

	/**
	 * 날짜 출력 포맷에 맞게 반환 메소드
	 * @param pattern 출력 패턴
	 * @param date 날짜
	 * @return 날자 출력 포맷에 맞는 문자열 반환
	 */
	public static final String getStringDateFormat(String pattern, Date date) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}


}
