//package com.kiki.View.Activity.login;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//
//import com.congnavi.android.http.IHttpReceive;
//import com.congnavi.utils.AppUtils;
//import com.congnavi.utils.CSLog;
//import com.congnavi.utils.ConstUtils;
//import com.congnavi.utils.sqlite.SQLQuery;
//import com.itw.android.R;
//import com.itw.android.activity.MainActivity;
//import com.itw.android.data.Login;
//import com.itw.android.data.SettingData;
//import com.kakao.APIErrorResult;
//import com.kakao.AuthType;
//import com.kakao.LogoutResponseCallback;
//import com.kakao.MeResponseCallback;
//import com.kakao.Session;
//import com.kakao.SessionCallback;
//import com.kakao.UserManagement;
//import com.kakao.UserProfile;
//import com.kakao.exception.KakaoException;
//import com.kakao.widget.LoginButton;
//
///**
// * 카카오 로그인 액티비티
// */
//public class KakaoLoginActivity extends Activity implements Handler.Callback, IHttpReceive, SessionCallback {
//    private final String TAG = "@@" + this.getClass().getSimpleName();
//    /**
//     * 로그인 버튼 - 꼭 사용해야 함,(정책적인 이유)
//     */
//    private LoginButton loginButton;
//    /**
//     * 핸들러
//     */
//    private Handler mHandler = null;
//
//    /**
//     * 로그인 정보
//     */
//    private Login mLoginData = null;
//    /**
//     * 로그인 성공
//     */
//    private static final int LOGIN_OK = 1;
//    /**
//     * 로그인 실패
//     */
//    private static final int LOGIN_FAIL = 2;
//    /**
//     * 토큰 유효검사
//     */
//    private static final int CHECK_TOKEN = 3;
//    /**
//     * 로그아웃
//     */
//    private static final int LOGOUT = 4;
//
//    /**
//     * 로그아웃 여부
//     */
//    private boolean mLoginout = false;
//    /**
//     * 컨텍스트
//     */
//    private static Context mContext;
//
//    @Override
//    protected void onCreate(final Bundle savedInstanceState) {
//        Log.d(TAG, "@@ onCreate ");
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_kakao_login);
//
//        // 카카오 sdk 초기화
//        Session.initialize(this, AuthType.KAKAO_ACCOUNT);
//
//        loginButton = (LoginButton) findViewById(R.id.login_kakao_button);
//
//        mContext = this;
//        mHandler = new Handler(this);
//        mLoginData = new Login();
//
//        // 세션 콜백 추가
//        Session.getCurrentSession().addCallback(this);
//
//        Intent intent = getIntent();
//        String isValue = intent.getStringExtra("LOGOUT");
//
//        if (isValue != null && isValue.equals("Y")) {
//            CSLog.d(this.getClass().getSimpleName(), "@@ 카카오 액티비티 로그아웃 : " + isValue);
//            mLoginout = true;
//            requestLogout();
//        } else {
//            Log.d(TAG, "@@ 카카오 액티비티 로그인 ");
//            Session.getCurrentSession().open(AuthType.KAKAO_ACCOUNT, this);
////            loginButton.performClick();
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Session.getCurrentSession().removeCallback(this);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
//            return;
//        }
//
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    @Override
//    /**
//     * 세션연결 연결시
//     */
//    public void onSessionOpened() {
//        // 프로그레스바 종료
//        CSLog.d(this.getClass().getSimpleName(), "@@ KakaoLoginActivity MySessionStatusCallback onSessionOpened ");
//        // 세션 오픈후 보일 페이지로 이동
//        requestMe();
//    }
//
//    @Override
//    /**
//     * 세션연결 연결 실패시
//     */
//    public void onSessionClosed(final KakaoException exception) {
//        CSLog.d(this.getClass().getSimpleName(), "@@ KakaoLoginActivity MySessionStatusCallback onSessionClosed ");
//        mHandler.sendEmptyMessage(LOGIN_FAIL);
//    }
//
//    @Override
//    public void onSessionOpening() {
//        CSLog.d(this.getClass().getSimpleName(), "@@ KakaoLoginActivity MySessionStatusCallback onSessionOpening ");
//        //프로그레스바 시작
//    }
//
//    private void requestMe() {
//        Log.d(TAG, "@@ 카카오 액티비티 로그인 requestMe ");
//        UserManagement.requestMe(new MeResponseCallback() {
//            @Override
//            public void onSuccess(final UserProfile userProfile) {
//                Log.d(TAG, "@@ 카카오 액티비티 로그인 onSuccess ");
//                // 성공.
//                String imgUrl = userProfile.getThumbnailImagePath();
//                String nickName = userProfile.getNickname();
//                String email = userProfile.getId() + "";
//                String token = Session.getCurrentSession().getAccessToken();
//                mLoginData.setToken(token);
//                mLoginData.setEmail(email);
//                mLoginData.setImgUrl(imgUrl);
//                mLoginData.setNickname(nickName);
//                if (mLoginout) {
//                    requestLogout();
//                } else {
//                    mHandler.sendEmptyMessage(CHECK_TOKEN);
//                }
//
//            }
//
//            @Override
//            public void onNotSignedUp() {
//                Log.d(TAG, "@@ 카카오 액티비티 로그인 onNotSignedUp ");
//                // 가입 페이지로 이동
//                mHandler.sendEmptyMessage(LOGIN_FAIL);
//            }
//
//            @Override
//            public void onSessionClosedFailure(final APIErrorResult errorResult) {
//                Log.d(TAG, "@@ 카카오 액티비티 로그인 onSessionClosedFailure ");
//                // 다시 로그인 시도
//                mHandler.sendEmptyMessage(LOGIN_FAIL);
//            }
//
//            @Override
//            public void onFailure(final APIErrorResult errorResult) {
//                Log.d(TAG, "@@ 카카오 액티비티 로그인 onFailure ");
//                // 실패
//                mHandler.sendEmptyMessage(LOGIN_FAIL);
//            }
//        });
//
//    }
//
//    private void requestLogout() {
//        Log.d(TAG, "@@ requestLogout ");
//        CSLog.d(this.getClass().getSimpleName(), "@@ kakao requestLogout call");
//        UserManagement.requestLogout(new LogoutResponseCallback() {
//            @Override
//            protected void onSuccess(long userId) {
//                CSLog.d(this.getClass().getSimpleName(), "@@ kakao logout onSuccess");
//                mHandler.sendEmptyMessage(LOGOUT);
//            }
//
//            @Override
//            protected void onFailure(APIErrorResult apiErrorResult) {
//                CSLog.d(this.getClass().getSimpleName(), "@@ kakao logout onFailure");
//                CSLog.d(this.getClass().getSimpleName(), "@@ kakao logout Message: " + apiErrorResult.getErrorMessage());
//                CSLog.d(this.getClass().getSimpleName(), "@@ kakao logout ErrorResult: " + apiErrorResult.toString());
//                mHandler.sendEmptyMessage(LOGOUT);
//            }
//        });
//    }
//
//    @Override
//    public boolean handleMessage(Message msg) {
//        SQLQuery sqlQuery = new SQLQuery();
//        switch (msg.what) {
//            case LOGIN_OK:
//                Log.d(TAG, "@@ 카카오 액티비티 handleMessage OK ");
//                //프로필 정보 SQLite 업데이트
//                mLoginData.setSNS(ConstUtils.KEY_LOGIN_KAKAO);
//                SettingData settingData = sqlQuery.getUserSetting(this);
//                mLoginData.setPushSetting(settingData.getPushSetting());
//                mLoginData.setGCMToken(settingData.getGcmToken());
//                mLoginData.setVersionCode(AppUtils.getVersionCode(this));
//                mLoginData.setVersionName(AppUtils.getVersionName(this));
//                mLoginData.setMarketType(ConstUtils.KEY_LOGIN_MARKET_GOOGLE);
//                mLoginData.setPhone(AppUtils.getUserPhoneNumber(this));
//                mLoginData.setLocaleCounty(AppUtils.getUserPhoneLanuage(this));
//                mLoginData.setDeviceID(AppUtils.getUserDeviceID(this));
//
//                sqlQuery.deleteUserLogin(mContext);
//                sqlQuery.insertUserLogin(mContext, mLoginData.getAutoLogin(), mLoginData.getEmail(), mLoginData.getImgUrl(),
//                        mLoginData.getPhone(), mLoginData.getToken(), mLoginData.getmefreshTokenToken(), mLoginData.getExpiredDate(),
//                        mLoginData.getUserName(), mLoginData.getNickname(), mLoginData.getSNS());
//
//                //화면 이동
//                MainActivity.mWebView.loadUrl("javascript:loginAfter('" + mLoginData.getJsonLoginData() + "')");
//                MainActivity.mWebView.resumeTimers();
//
////                Intent intent = new Intent(this, MainActivity.class);
////                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
////                AppUtils.restart(mContext, intent);
//                finish();
//
//                break;
//            case LOGIN_FAIL:
//                Log.d(TAG, "@@ 카카오 액티비티 handleMessage FAIL ");
//                //MainActivity.mWebView.init(null);
//                //MainActivity.mWebView.loadUrl(ConstUtils.webViewURL);
//                //Toast.makeText(getApplicationContext(), "카카오 로그인 실패", Toast.LENGTH_LONG).show();
//                finish();
//                break;
//            case CHECK_TOKEN:
//                Log.d(TAG, "@@ 카카오 액티비티 handleMessage CHECK ");
//                //토큰 갱신이 가능한지 반환
////                boolean isImpliitOpen = mSession.implicitOpen();
////                if (isImpliitOpen) {
////                    //토큰 유효성을 검사하고 만료된 경우 갱신시켜준다.
////                    mSession.checkAccessTokenInfo();
////                }
//
////                UserTaskManager taskManager = new UserTaskManager("https://kapi.kakao.com/v1/user/access_token_info", false, this);
////                taskManager.execute("Bearer " + mLoginData.getToken());
//
//                mHandler.sendEmptyMessage(LOGIN_OK);
//                break;
//            case LOGOUT://로그아웃
//                Log.d(TAG, "@@ 카카오 액티비티 handleMessage LOGOUT ");
//                sqlQuery.deleteUserLogin(mContext);
//                MainActivity.mWebView.loadUrl("javascript:logoutAfter()");
//                finish();
//                break;
//        }
//        return false;
//    }
//
//    @Override
//    public void onHttpReceive(int type, Object obj) {
//        CSLog.d(this.getClass().getSimpleName(),"@@ receive : " + obj);
//        switch (type) {
//            case HTTP_OK:
//                Log.d(TAG, "@@ 카카오 액티비티 onHttpReceive HTTP OK ");
//                String data = (String) obj;
//                mHandler.sendEmptyMessage(LOGIN_OK);
//                break;
//            case HTTP_FAIL:
//                Log.d(TAG, "@@ 카카오 액티비티 onHttpReceive HTTP FAIL ");
//                mHandler.sendEmptyMessage(LOGIN_FAIL);
//                break;
//        }
//    }
//
//}
