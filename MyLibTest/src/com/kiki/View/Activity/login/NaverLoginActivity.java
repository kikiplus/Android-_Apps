package com.kiki.View.Activity.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;

import com.kiki.View.Bean.Login;
import com.kiki.View.R;
import com.kiki.android.Listener.IHttpReceive;
import com.kiki.android.Logic.Managers.http.UserTaskManager;
import com.kiki.android.Utils.ContextUtils;
import com.kiki.android.Utils.DomParser;
import com.kiki.android.Utils.KLog;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginDefine;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.data.OAuthLoginState;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

/**
 * 네이버 로그인 액티비티
 */
public class NaverLoginActivity extends Activity implements Handler.Callback, IHttpReceive {
    private OAuthLoginButton mOAuthLoginButton;
    private static OAuthLogin mOAuthLoginInstance;

    private static String OAUTH_CLIENT_NAME = "네이버 아이디로 로그인";

    private static Context mContext;
    private static Handler mNaverHandler;
    private static Login mLoginData = null;
    private static String mTokenType = null;

    private static final int LOGIN_OK = 111;
    private static final int LOGIN_FAIL = 2222;
    private static final int REQUEST_PROFILE = 3333;
    private static final int LOGOUT = 444;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naver_login);

        //네이버 아디로 로그인 라이브러리가 로그 출력/ 민감한 정보가 표시때문에 배포시에는 false
        OAuthLoginDefine.DEVELOPER_VERSION = false;
        mContext = this;
        mNaverHandler = new Handler(this);
        mLoginData = new Login();

        initData();

        Intent intent = getIntent();
        String isValue = intent.getStringExtra("LOGOUT");
        if (isValue != null && isValue.equals("Y")) {
            KLog.d(this.getClass().getSimpleName(), "@@ 네이버 액티비티 로그아웃 시작 ");
            //로그아웃
            mNaverHandler.sendEmptyMessage(LOGOUT);
        } else {
            KLog.d(this.getClass().getSimpleName(), "@@ 네이버 액티비티 로그인 시작 ");
            //로그인 기능
            initView();
        }
    }

    /**
     * 로그인 인스턴스 초기화
     */
    private void initData() {
        mOAuthLoginInstance = OAuthLogin.getInstance();
        mOAuthLoginInstance.init(mContext, ContextUtils.KEY_NAVER_CLIENT_ID, ContextUtils.KEY_NAVER_OAUTH_SECRET, OAUTH_CLIENT_NAME);
    }

    /**
     * 로그인 버튼 설정
     */
    private void initView() {
        mOAuthLoginButton = (OAuthLoginButton) findViewById(R.id.login_naver_button);
        mOAuthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);
        mOAuthLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOAuthLoginInstance.startOauthLoginActivity(NaverLoginActivity.this, mOAuthLoginHandler);
            }
        });
        mOAuthLoginButton.performClick();
    }

    @Override
    protected void onResume() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onResume();
    }

    /**
     * startOAuthLoginActivity() 호출시 인자로 넘기거나, OAuthLoginButton 에 등록해주면 인증이 종료되는 걸 알 수 있다.
     */
    static private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {
                KLog.d(this.getClass().getSimpleName(), "@@ 네이버  로그인 성공");
                String accessToken = mOAuthLoginInstance.getAccessToken(mContext);
                String refreshToken = mOAuthLoginInstance.getRefreshToken(mContext);
                long expiresAt = mOAuthLoginInstance.getExpiresAt(mContext);
                String tokenType = mOAuthLoginInstance.getTokenType(mContext);

//                if(expiresAt > 0 ){
//                    RefreshTokenTask tokenTask = new RefreshTokenTask();
//                    tokenTask.execute();
//                }
                mTokenType = tokenType;
                mLoginData.setExpiredDate(expiresAt + "");
                mLoginData.setToken(accessToken);
                mLoginData.setRefreshToken(refreshToken);

                mNaverHandler.sendEmptyMessage(REQUEST_PROFILE);
            } else {
                String errorCode = mOAuthLoginInstance.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLoginInstance.getLastErrorDesc(mContext);
                KLog.d(this.getClass().getSimpleName(), "@@ 네이버  로그인 실패 : " + errorCode + ", " + mOAuthLoginInstance.getLastErrorCode(mContext).getDesc());
                mNaverHandler.sendEmptyMessage(LOGIN_FAIL);
            }
        }

        ;
    };


    @Override
    public boolean handleMessage(Message msg) {
//        SQLQuery sqlQuery = new SQLQuery();
        switch (msg.what) {
            case LOGIN_OK://로그인 성공
//                mLoginData.setSNS(ConstUtils.KEY_LOGIN_NAVER);
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
                //화면 이동
//                MainActivity.mWebView.loadUrl("javascript:loginAfter('" + mLoginData.getJsonLoginData() + "')");
                finish();

                break;

            case LOGIN_FAIL://로그인 실패

                finish();

                break;

            case REQUEST_PROFILE://사용자 정보 조회

                UserTaskManager taskManager = new UserTaskManager("https://apis.naver.com/nidlogin/nid/getUserProfile.xml", false, "UTF-8", this);
                String accessToken = mLoginData.getToken();
                taskManager.execute(mTokenType + " " + accessToken);
                break;

            case LOGOUT://로그아웃 성공
                mOAuthLoginInstance.logout(mContext);

                if (mOAuthLoginInstance.getState(getApplicationContext()) == (OAuthLoginState.NEED_LOGIN)) {
                    KLog.d(this.getClass().getSimpleName(), "@@ 네이버 액티비티 로그아웃 성공 ");
                }
//                MainActivity.mWebView.loadUrl("javascript:logoutAfter()");
                finish();
                break;
        }
        return false;
    }

    @Override
    public void onHttpReceive(int type, Object obj) {
        KLog.d(this.getClass().getSimpleName(), "receive : " + obj);
        switch (type) {
            case HTTP_OK://프로필 정보 정상적으로 수신
                String data = (String) obj;
                try {
                    DomParser domParser = new DomParser(data);
                    String email = domParser.getValueParser("email");
                    String nickname = domParser.getValueParser("nickname");
                    String profile_image = domParser.getValueParser("profile_image");
                    String name = domParser.getValueParser("name");


                    mLoginData.setImgUrl(profile_image);
                    mLoginData.setEmail(email);
                    mLoginData.setUserName(name);
                    mLoginData.setNickname(nickname);
                    mNaverHandler.sendEmptyMessage(LOGIN_OK);
                } catch (SAXParseException e) {
                    e.printStackTrace();
                    KLog.d(this.getClass().getSimpleName(), "@@ 네이버 xml 파싱 에러 : " + e.getMessage());
                    mNaverHandler.sendEmptyMessage(LOGIN_FAIL);
                } catch (SAXException e) {
                    e.printStackTrace();
                    mNaverHandler.sendEmptyMessage(LOGIN_FAIL);
                } catch (IOException e) {
                    e.printStackTrace();
                    mNaverHandler.sendEmptyMessage(LOGIN_FAIL);
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                    mNaverHandler.sendEmptyMessage(LOGIN_FAIL);
                }
                break;
            case HTTP_FAIL:
                mNaverHandler.sendEmptyMessage(LOGIN_FAIL);
                break;
        }
    }

    /**
     * 토큰 갱신 Task
     */
    private class RefreshTokenTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            KLog.d(this.getClass().getSimpleName(), "토큰 갱신");
            String token = OAuthLogin.getInstance().refreshAccessToken(mContext);

            KLog.d(this.getClass().getSimpleName(), "토큰 갱신 token : " + mOAuthLoginInstance.getAccessToken(mContext));
            KLog.d(this.getClass().getSimpleName(), "토큰 갱신 refresh token : " + mOAuthLoginInstance.getRefreshToken(mContext));

            return token;
        }

        protected void onPostExecute(String res) {
        }
    }
}



