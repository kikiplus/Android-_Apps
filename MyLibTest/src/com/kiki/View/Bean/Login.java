package com.kiki.View.Bean;

import com.congnavi.utils.CSLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/***
 * @author mh kim
 * @version 1.0
 * @Class Name : Login
 * @Description : 로그인정보
 * @since 2015-09-11.
 */
public class Login implements Serializable {

    /**
     * 소셜 로그인 정보
     */
    private String mSNS = null;

    /**
     * 이메일 정보
     */
    private String mEmail = null;

    /**
     * 프로필 이미지 정보
     */
    private String mImgUrl = null;

    /**
     * 사용자 전화번호(단말기)
     */
    private String mPhone = null;

    /**
     * 사용자 자동 로그인 여부
     */
    private String mAutoLogin = "Y";

    /**
     * 토큰 정보
     */
    private String mToken = null;

    /**
     * 토큰 정보(Refresh Token)- (추후 사용할 수도 있을 것 같아 둠-네이버)
     */
    private String mRefreshToken = null;

    /**
     * 토큰 유효기간 (추후 사용할 수도 있을 것 같아 둠)
     */
    private String mExpiredDate = null;
    /**
     * 닉네임 (추후 사용할 수도 있을 것 같아 둠)
     */
    private String mNickName = null;
    /**
     * 사용자 이름 (추후 사용할 수도 있을 것 같아 둠)
     */
    private String mName = null;
    /**
     * 국가코드
     */
    private String mlocallCounty = null;
    /**
     * OS 타입
     */
    private String mOsType = "A";
    /**
     * 앱 버전 코드
     */
    private int mAppVersionCode = 0;
    /**
     * 앱 버전 명
     */
    private String mAppVersionName = null;
    /**
     * 앱 마켓 타입
     */
    private String mMarketType = null;
    /**
     * GCM 토큰
     */
    private String mRegId = null;
    /**
     * PUSH 설정값
     */
    private String mIsPush = null;

    /**
     * 디바이스 ID
     */
    private String mDeviceId = null;

    /**
     * 생성자
     */
    public Login() {
    }

    /**
     * 소셜 로그인 정보 반환
     *
     * @return 소셜 로그인 구분값
     */
    public String getSNS() {
        return mSNS;
    }

    /**
     * 이메일 정보 반환
     *
     * @return 이메일
     */
    public String getEmail() {
        return mEmail;
    }

    /**
     * 프로필 이미지 정보 반환
     *
     * @return 프로필 이미지 정보
     */
    public String getImgUrl() {
        return mImgUrl;
    }

    /**
     * 사용자  전화번호 정보 반환
     *
     * @return 사용자 전화번호
     */
    public String getPhone() {
        return mPhone;
    }

    /**
     * 소사용자 전화번호(소셜에 등록된 번호)
     *
     * @return 소셜 로그인 구분값
     */
    public String getAutoLogin() {
        return mAutoLogin;
    }

    /**
     * 토큰 정보 정보 반환
     *
     * @return 토큰 정보
     */
    public String getToken() {
        return mToken;
    }

    /**
     * 갱신 토큰 정보 정보 반환
     *
     * @return 토큰 정보
     */
    public String getmefreshTokenToken() {
        return mRefreshToken;
    }

    /**
     * 토큰 유효기간 정보 반환
     *
     * @return 토큰 유효기간
     */
    public String getExpiredDate() {
        return mExpiredDate;
    }


    /**
     * 사용자이름 정보 반환
     *
     * @return 사용자이름
     */
    public String getNickname() {
        return mNickName;
    }

    /**
     * 사용자 닉네임 정보 반환
     *
     * @return 사용자 닉네임
     */
    public String getUserName() {
        return mName;
    }

    /**
     * 국가코드  정보 반환
     *
     * @return 국가코드
     */
    public String getLocaleCoutry() {
        return mlocallCounty;
    }

    /**
     * OS 타입 정보 반환
     *
     * @return OS 타입
     */
    public String getOsType() {
        return mOsType;
    }

    /**
     * 앱 버전 코드 정보 반환
     *
     * @return 앱 버전 코드
     */
    public int getVersionCode() {
        return mAppVersionCode;
    }

    /**
     * 앱 버전 명 반환
     *
     * @return 앱 버전명
     */
    public String getVersionName() {
        return mAppVersionName;
    }

    /**
     * 마켓 타입 정보 반환
     *
     * @return 마켓 타입
     */
    public String getMarketType() {
        return mMarketType;
    }

    /**
     * GCM 토큰 정보 반환
     *
     * @return GCM 토큰 정보
     */
    public String getGCMToken() {
        return mRegId;
    }

    /**
     * 푸쉬 설정 정보 반환
     *
     * @return 푸쉬 설정 정보
     */
    public String getPushSetting() {
        return mIsPush;
    }

    /**
     * 디바이스ID  반환
     *
     * @return 디바이스 ID
     */
    public String getDeviceId() {
        return mDeviceId;
    }

    /**
     * 소셜 로그인 정보 설정
     *
     * @param sns 소셜 로그인 구분값
     */
    public void setSNS(String sns) {
        mSNS = sns;
    }

    /**
     * 이메일 정보 설정
     *
     * @param email 이메일
     */
    public void setEmail(String email) {
        if (email != null) {
            mEmail = email;
        }
    }

    /**
     * 프로필 이미지 정보 설정
     *
     * @param imgUrl 프로필 이미지 정보
     */
    public void setImgUrl(String imgUrl) {
        if (imgUrl != null) {
            mImgUrl = imgUrl;
        }
    }

    /**
     * 사용자 전화번호 정보 설정
     *
     * @param phone 사용자 전화번호
     */
    public void setPhone(String phone) {
        mPhone = phone;
    }

    /**
     * 소사용자 전화번호(소셜에 등록된 번호) 설정
     *
     * @param autoLogin 소셜 로그인 구분값
     */
    public void setAutoLogin(String autoLogin) {
        mAutoLogin = autoLogin;
    }

    /**
     * 토큰 정보 정보 설정
     *
     * @param token 토큰 정보
     */
    public void setToken(String token) {
        mToken = token;
    }

    /**
     * 갱신 토큰 정보 정보 설정
     *
     * @param token 갱신 토큰 정보
     */
    public void setRefreshToken(String token) {
        mRefreshToken = token;
    }

    /**
     * 토큰 유효기간 정보 설정
     *
     * @param expiredDate 토큰 유효기간
     */
    public void setExpiredDate(String expiredDate) {
        mExpiredDate = expiredDate;
    }

    /**
     * 사용자이름 정보 설정
     *
     * @param nickname 사용자이름
     */
    public void setNickname(String nickname) {
        mNickName = nickname;
    }

    /**
     * 사용자 닉네임 정보 설정
     *
     * @param name 사용자 닉네임
     */
    public void setUserName(String name) {
        mName = name;
    }


    /**
     * 언어 국가 정보 설정
     *
     * @param localeCountry 국가코드
     */
    public void setLocaleCounty(String localeCountry) {
        mlocallCounty = localeCountry;
    }

    /**
     * OS 타입 정보 설정
     *
     * @param osType OS 타입
     */
    public void setOsType(String osType) {
        mOsType = osType;
    }

    /**
     * 앱 버전 코드 정보 설정
     *
     * @param versionCode 앱 버전 코드
     */
    public void setVersionCode(int versionCode) {
        mAppVersionCode = versionCode;
    }

    /**
     * 앱 버전 명 설정
     *
     * @param versionName 앱 버전명
     */
    public void setVersionName(String versionName) {
        mAppVersionName = versionName;
    }

    /**
     * 마켓 타입 정보 설정
     *
     * @param marketType 마켓 타입
     */
    public void setMarketType(String marketType) {
        mMarketType = marketType;
    }

    /**
     * GCM 토큰 정보 설정
     *
     * @param token GCM 토큰 정보
     */
    public void setGCMToken(String token) {
        mRegId = token;
    }

    /**
     * 푸쉬 설정 정보 설정
     *
     * @param push 푸쉬 설정 정보
     */
    public void setPushSetting(String push) {
        mIsPush = push;
    }

    /**
     * 디바이스ID 설정
     *
     * @param deviceID 디바이스 ID
     */
    public void setDeviceID(String deviceID) {
        mDeviceId = deviceID;
    }
    /**
     * 로그인 정보 json 스트링으로 반환
     *
     * @return 로그인 정보 json 반환
     */
    public String getJsonLoginData()
    {
        JSONObject jsonObject = new JSONObject();
        String sns = mSNS != null ? mSNS : "";
        String emali = (mEmail != null && (!mEmail.equals(""))) ? mEmail : "";
        String imgurl = (mImgUrl != null && (!mImgUrl.equals(""))) ? mImgUrl : "";
        String phone = mPhone != null ? mPhone : "";
        String localeCountry = mlocallCounty != null ? mlocallCounty : "";
        String autoLogin = mAutoLogin != null ? mAutoLogin : "";
        String osType = mOsType != null ? mOsType : "";
        String versionCode = mAppVersionCode + "";
        String versionName = mAppVersionName != null ? mAppVersionName : "";
        String marketType = mMarketType != null ? mMarketType : "";
        String gcmToken = mRegId != null ? mRegId : "";
        String pushSetting = mIsPush != null ? mIsPush : "";
        String name = (mName != null && (!mName.equals(""))) ? mName : "";
        String nickname = (mNickName != null && (!mNickName.equals(""))) ? mNickName : "";
        String deviceID = mDeviceId;

        try {
            jsonObject.put("snsType", sns);
            jsonObject.put("snsId", emali);
            jsonObject.put("userImg", imgurl);
            jsonObject.put("phoneNum", phone);
            jsonObject.put("countryCode", localeCountry);
            jsonObject.put("isAutoLogin", autoLogin);
            jsonObject.put("osType", osType);
            jsonObject.put("versionCode", versionCode);
            jsonObject.put("appVersion", versionName);

            jsonObject.put("marketType", marketType);
            jsonObject.put("regId", gcmToken);
            jsonObject.put("isPush", pushSetting);
            jsonObject.put("userName", name);
            jsonObject.put("nickName", nickname);
            jsonObject.put("deviceId", deviceID);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return jsonObject.toString();
    }

}
