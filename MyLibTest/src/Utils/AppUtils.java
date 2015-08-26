package Utils;

import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : AppUtils
 * @Description : 앱 관련 유틸 클래스
 * @since 2015-08-03.
 */
public class AppUtils {

    /**
     * 사용자 정보 출력 메소드
     *
     * @param context 컨텍스트
     */
    public static void PrintUserPhoneInfo(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String displayCountry = locale.getDisplayCountry();
        String country = locale.getCountry();
        String launage = locale.getLanguage();

        System.out.println("displayCountry => " + displayCountry);
        System.out.println("County => " + country);
        System.out.println("launage => " + launage);
    }

    /**
     * 현재 사용자에 설정된 언어 가져오기
     *
     * @param context 컨텍스트
     * @return 언어 정보
     */
    public static String getUserPhoneLanuage(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String launage = locale.getLanguage();
        return launage;
    }

    /***
     * 타임존 시간 출력 메소드
     */
    public static void printUserPoneTimezone(){
        TimeZone tz;
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss (z Z)");


        tz = TimeZone.getTimeZone("Asia/Seoul"); df.setTimeZone(tz);
        System.out.println("@@ timezone : " + tz.getDisplayName() +"," +  df.format(date));


        tz = TimeZone.getTimeZone("Greenwich"); df.setTimeZone(tz);
        System.out.println("@@ timezone : " + tz.getDisplayName() +"," +  df.format(date));


        tz = TimeZone.getTimeZone("America/Los_Angeles"); df.setTimeZone(tz);
        System.out.println("@@ timezone : " + tz.getDisplayName()+"," +  df.format(date));


        tz = TimeZone.getTimeZone("America/New_York"); df.setTimeZone(tz);
        System.out.println("@@ timezone : " + tz.getDisplayName()+"," +  df.format(date));


        tz = TimeZone.getTimeZone("Pacific/Honolulu"); df.setTimeZone(tz);
        System.out.println("@@ timezone : " + tz.getDisplayName()+"," +  df.format(date));


        tz = TimeZone.getTimeZone("Asia/Shanghai"); df.setTimeZone(tz);
        System.out.println("@@ timezone : " + tz.getDisplayName()+"," + df.format(date));
    }

    /**
     * 현재 사용자에 설정된 시간정보 가져오기
     * @return 시간 정보
     */
    public static String getUserPhoneTimezone(){
        TimeZone timeZone = TimeZone.getDefault();

        timeZone =  timeZone.getTimeZone(timeZone.getID());
        Date date = new Date();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(timeZone);

        return  df.format(date);
    }
}
