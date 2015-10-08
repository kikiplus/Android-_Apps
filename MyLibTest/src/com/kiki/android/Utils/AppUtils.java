package com.kiki.android.Utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
    public static void printUserPhoneInfo(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String displayCountry = locale.getDisplayCountry();
        String country = locale.getCountry();
        String launage = locale.getLanguage();
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
    public static void printUserPoneTimezone() {
        TimeZone tz;
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss (z Z)");


        tz = TimeZone.getTimeZone("Asia/Seoul");
        df.setTimeZone(tz);
        System.out.println("@@ timezone : " + tz.getDisplayName() + "," + df.format(date));


        tz = TimeZone.getTimeZone("Greenwich");
        df.setTimeZone(tz);
        System.out.println("@@ timezone : " + tz.getDisplayName() + "," + df.format(date));


        tz = TimeZone.getTimeZone("America/Los_Angeles");
        df.setTimeZone(tz);
        System.out.println("@@ timezone : " + tz.getDisplayName() + "," + df.format(date));


        tz = TimeZone.getTimeZone("America/New_York");
        df.setTimeZone(tz);
        System.out.println("@@ timezone : " + tz.getDisplayName() + "," + df.format(date));


        tz = TimeZone.getTimeZone("Pacific/Honolulu");
        df.setTimeZone(tz);
        System.out.println("@@ timezone : " + tz.getDisplayName() + "," + df.format(date));


        tz = TimeZone.getTimeZone("Asia/Shanghai");
        df.setTimeZone(tz);
        System.out.println("@@ timezone : " + tz.getDisplayName() + "," + df.format(date));
    }

    /**
     * 언어 설정 메소드
     *
     * @param context 컨텍스트
     * @param locale  국가
     */
    public static void setLocale(Context context, Locale locale) {
        Locale.setDefault(locale);

        Configuration configuration = new Configuration();
        configuration.locale = locale;
        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
    }

    /**
     * 현재 사용자에 설정된 시간정보 가져오기
     *
     * @return 시간 정보
     */
    public static String getUserPhoneTimezone() {
        TimeZone timeZone = TimeZone.getDefault();

        timeZone = timeZone.getTimeZone(timeZone.getID());
        Date date = new Date();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(timeZone);

        return df.format(date);
    }


    /**
     * 서비스가 실행중인지 반환하는 메소드
     *
     * @param context     컨텍스트
     * @param serviceName 서비스명
     * @return 실행중이면 true, 아니면 false
     */
    public static boolean getRunningService(Context context, String serviceName) {
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
     *
     * @param context 컨텍스트
     * @return 앱 버전(int형) 예외상황 -1값 반환
     */
    public static int getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        String pkgName = context.getPackageName();
        int versionCode;
        try {
            PackageInfo packageInfo = (PackageInfo) packageManager.getPackageInfo(pkgName, 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            versionCode = -1;
        }
        return versionCode;
    }

    /**
     * 앱 버전 네임 반환 메소드
     *
     * @param context 컨텍스트
     * @return 앱 버전 네임(string형) 예외상황 null 반환
     */
    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        String pkgName = context.getPackageName();
        String versionName;
        try {
            PackageInfo packageInfo = (PackageInfo) packageManager.getPackageInfo(pkgName, 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            versionName = null;
        }
        return versionName;
    }

    /**
     * 패키지명으로 실행중인 앱 여부 반환 메소드
     *
     * @param context  컨텍스트
     * @param packName 패키지명
     * @return 실행 여부(true - 실행중, false - 실행안됨)
     */
    public static boolean getServiceTaskName(Context context, String packName) {
        boolean checked = false;
        ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo rap : list) {
            if (rap.processName.equals(packName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 스트링 트림 처리.
     */
    public static String checkString(String str, String tmp) {
        if (!(str == null || str.trim().equals("") || str.trim().equals("null")))
            return (String) str.trim().toString();
        else return tmp;
    }

    /**
     * 앱 hash 값 반환 메소드
     *
     * @param context 컨텍스트
     * @return hash 키값
     */
     /*
    tjkim
    카카오로그인 키해시생성 코드 , 알아본바에 openssl 버전에 따라 생성되는 키해시값이 달라 , 카카오와 연동이 안되는 경우가 있었음
    이 메소드 현재 배포된 키싸인의 해쉬코드를 가져오는것이기에 , 불편할순있지만 , 카카오 계정 해시코드는 이걸로 사용을 하며 , 다른 sns 로그인에도 필요하면 사용하도록하자.
     */
    public static String getAppKeyHash(Context context) {
        String hashValue = null;
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                hashValue = new String(Base64.encode(md.digest(), 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashValue;
    }

    /**
     * 패키지로 앱 설치 유무 반환 메소드
     *
     * @param packageName 패키지명
     * @return 앱 설치 유무
     */
    public static boolean checkAppPakcage(Context context, String packageName) {
        try {

            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, PackageManager.GET_META_DATA);
            ApplicationInfo appInfo = pi.applicationInfo;

        } catch (PackageManager.NameNotFoundException e) {
            // 패키지가 없을 경우.
            return false;
        }
        return true;
    }

    /**
     * 마켓 이동 메소드
     *
     * @param context      컨텍스트
     * @param packangeName 마켓 이동할 패키지명
     */
    public static void locationMarket(Context context, String packangeName) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + packangeName));
        context.startActivity(intent);
    }

    /**
     * 앱 재 실행
     *
     * @param context 컨텍스트
     * @param intent  실행할 인텐트
     */
    public static void restart(Context context, Intent intent) {
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(context, mPendingIntentId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        System.exit(0);
    }

    /**
     * 사용자 전화번호 반환 메소드
     *
     * @return 사용자 단말기 전화번호
     */
    public static String getUserPhoneNumber(Context context) {
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String telPhoneNo = telephony.getLine1Number();
        return telPhoneNo;
    }

    /**
     * 사용자 Device ID 반환 메소드
     *
     * @return 사용자 Device ID
     */
    public static String getUserDeviceID(Context context) {
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = telephony.getDeviceId();
        return deviceId;
    }

    /**
     * 사용자 전화국 통신사 정보 반환 메소드
     *
     * @return 사용자 단말 통신사
     */
    public static String getUserPhoneNetworkOperationName(Context context) {
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String value = telephony.getNetworkOperatorName();
        return value;
    }
}
