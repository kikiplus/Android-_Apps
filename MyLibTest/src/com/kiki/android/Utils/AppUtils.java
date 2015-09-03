package com.kiki.android.Utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.util.Log;

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
     * @param context 컨텍스트
     */
    public static void printUserPhoneInfo(Context context){
        Locale locale = context.getResources().getConfiguration().locale;
        String displayCountry = locale.getDisplayCountry();
        String country = locale.getCountry();
        String launage = locale.getLanguage();

        Log.d(ContextUtils.LOG, "displayCountry => " + displayCountry);
        Log.d(ContextUtils.LOG, "County => " + country);
        Log.d(ContextUtils.LOG, "launage => " + launage);
    }

    /**
     * 현재 사용자에 설정된 언어 가져오기
     * @param context 컨텍스트
     * @return 언어 정보
     */
    public static String getUserPhoneLanuage(Context context){
        Locale locale = context.getResources().getConfiguration().locale;
        String launage = locale.getLanguage();
        return  launage;
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
     * 언어 설정 메소드
     * @param context 컨텍스트
     * @param locale 국가
     */
    public static void setLocale(Context context, Locale locale){
        Locale.setDefault(locale);

        Configuration configuration = new Configuration();
        configuration.locale = locale;
        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
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


    /**
     * 서비스가 실행중인지 반환하는 메소드
     * @param context 컨텍스트
     * @param serviceName 서비스명
     * @return 실행중이면 true, 아니면 false
     */
    public static boolean getRunningService(Context context, String serviceName){
        ActivityManager manager = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceName.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 앱 버전 코드 반환 메소드
     * @param context 컨텍스트
     * @return 앱 버전(int형) 예외상황 -1값 반환
     *
     */
    public static int getVersionCode(Context context){
        PackageManager packageManager = context.getPackageManager();
        String pkgName = context.getPackageName();
        int versionCode;
        try{
            PackageInfo packageInfo = (PackageInfo)packageManager.getPackageInfo(pkgName, 0);
            versionCode =  packageInfo.versionCode;
        }catch(PackageManager.NameNotFoundException e){
            versionCode = -1;
        }
        return  versionCode;
    }

    /**
     * 앱 버전 네임 반환 메소드
     * @param context 컨텍스트
     * @return 앱 버전 네임(string형) 예외상황 null 반환
     */
    public static String getVersionName(Context context){
        PackageManager packageManager = context.getPackageManager();
        String pkgName = context.getPackageName();
        String versionName;
        try{
            PackageInfo packageInfo = (PackageInfo)packageManager.getPackageInfo(pkgName, 0);
            versionName =  packageInfo.versionName;
        }catch(PackageManager.NameNotFoundException e){
            versionName = null;
        }
        return  versionName;
    }
}
