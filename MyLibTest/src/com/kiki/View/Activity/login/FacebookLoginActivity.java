//package com.kiki.View.Activity.login;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//
//import com.congnavi.utils.AppUtils;
//import com.congnavi.utils.CSLog;
//import com.congnavi.utils.ConstUtils;
//import com.congnavi.utils.sqlite.SQLQuery;
//import com.facebook.AccessToken;
//import com.facebook.CallbackManager;
//import com.facebook.FacebookCallback;
//import com.facebook.FacebookException;
//import com.facebook.FacebookSdk;
//import com.facebook.GraphRequest;
//import com.facebook.GraphResponse;
//import com.facebook.login.LoginManager;
//import com.facebook.login.LoginResult;
//import com.itw.android.activity.MainActivity;
//import com.itw.android.data.Login;
//import com.itw.android.data.SettingData;
//
//import org.json.JSONObject;
//
//import java.util.Arrays;
//
//public class FacebookLoginActivity extends Activity implements Handler.Callback {
//    private FacebookLoginActivity mRefObj = this;
//
//    private CallbackManager mCallbackManager;
//    private FacebookCallback<LoginResult> mFacebookCallback;
//    private Handler mHandler = null;
//
//    private static final int LOGOUT = 1;
//    private static final int LOGIN_OK = 2;
//    private static final int LOGIN_FAIL = 3;
//
//    private static Login mLoginData = null;
//    private static Context mContext;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        //setContentView(R.layout.activity_face_book_login);
//
//        // 페이스북 sdk 초기화
//        FacebookSdk.sdkInitialize(getApplicationContext());
//
//        mCallbackManager = CallbackManager.Factory.create();
//        mHandler = new Handler(this);
//
//        mContext = this;
//        mLoginData = new Login();
//
//        Intent intent = getIntent();
//        String isValue = intent.getStringExtra("LOGOUT");
//        if (isValue != null && isValue.equals("Y")) {
//            mHandler.sendEmptyMessage(LOGOUT);
//            return;
//        }
//
//        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
//        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                //Toast.makeText(getApplicationContext(), "sucess", Toast.LENGTH_SHORT).show();
//                //Toast.makeText(getApplicationContext(), "loginResult.getAccessToken().getToken()" + loginResult.getAccessToken().getToken(), Toast.LENGTH_SHORT).show();
//                //Toast.makeText(getApplicationContext(), "FacebookLoginActivity 유효기간" + loginResult.getAccessToken().getExpires().toString(), Toast.LENGTH_SHORT).show();
//
//                //로그인 성공(user_id , sns , img
//                GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
//                    @Override
//                    public void onCompleted(JSONObject object, GraphResponse response) {
//                        try {
//                            CSLog.d(this.getClass().getSimpleName(), object.toString());
//
//                            String id = object.getString("id");
//                            String email = object.getString("email");
//                            String profileImg = "http://graph.facebook.com/" + id + "/picture?type=normal";
//                            String name = object.getString("name");
//
//                            mLoginData.setSNS(ConstUtils.KEY_LOGIN_FACEBOOK);
//                            mLoginData.setEmail(email);
//                            mLoginData.setImgUrl(profileImg);
//                            mLoginData.setUserName(name);
//                            mLoginData.setToken(AccessToken.getCurrentAccessToken().getToken());
//                            mLoginData.setExpiredDate(AccessToken.getCurrentAccessToken().getExpires().toString());
//
//                            SQLQuery sqlQuery = new SQLQuery();
//                            SettingData settingData = sqlQuery.getUserSetting(mRefObj);
//                            mLoginData.setPushSetting(settingData.getPushSetting());
//                            mLoginData.setGCMToken(settingData.getGcmToken());
//                            mLoginData.setVersionCode(AppUtils.getVersionCode(mRefObj));
//                            mLoginData.setVersionName(AppUtils.getVersionName(mRefObj));
//                            mLoginData.setMarketType(ConstUtils.KEY_LOGIN_MARKET_GOOGLE);
//                            mLoginData.setPhone(AppUtils.getUserPhoneNumber(mRefObj));
//                            mLoginData.setLocaleCounty(AppUtils.getUserPhoneLanuage(mRefObj));
//                            sqlQuery.insertUserLogin(mRefObj, mLoginData.getAutoLogin(), mLoginData.getEmail(), mLoginData.getImgUrl(),
//                                    mLoginData.getPhone(), mLoginData.getToken(), mLoginData.getmefreshTokenToken(), mLoginData.getExpiredDate(),
//                                    mLoginData.getUserName(), mLoginData.getNickname(), mLoginData.getSNS());
//
//                            mHandler.sendEmptyMessage(LOGIN_OK);
//
//                        } catch (Exception e) {
//                            CSLog.d(this.getClass().getSimpleName(), "@@ Exception : " + e.toString());
//                            mHandler.sendEmptyMessage(LOGIN_FAIL);
//                        }
//                    }
//                });
//
//                Bundle parameters = new Bundle();
//                parameters.putString("fields", "id, name, email,gender, birthday");
//                request.setParameters(parameters);
//                request.executeAsync();
//            }
//
//            @Override
//            public void onCancel() {
//                CSLog.d(this.getClass().getSimpleName(), "@@ FacebookLoginActivity onCancel");
//                mHandler.sendEmptyMessage(LOGIN_FAIL);
//            }
//
//            @Override
//            public void onError(FacebookException exception) {
//                CSLog.d(this.getClass().getSimpleName(), "@@ FacebookLoginActivity onError : " + exception.getMessage());
//                mHandler.sendEmptyMessage(LOGIN_FAIL);
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        CSLog.d(this.getClass().getSimpleName(), "@@ FacebookLoginActivity onActivityResult" + requestCode + "::" + resultCode + "::" + data);
//        super.onActivityResult(requestCode, resultCode, data);
//        mCallbackManager.onActivityResult(requestCode, resultCode, data);
//    }
//
//    @Override
//    public boolean handleMessage(Message message) {
//        SQLQuery sqlQuery = new SQLQuery();
//        switch (message.what) {
//            case LOGIN_OK://로그인 성공
//                mLoginData.setSNS(ConstUtils.KEY_LOGIN_FACEBOOK);
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
//                //화면 이동
//                MainActivity.mWebView.loadUrl("javascript:loginAfter('" + mLoginData.getJsonLoginData() + "')");
//                finish();
//                break;
//            case LOGIN_FAIL://로그인 실패
//                finish();
//                break;
//            case LOGOUT://로그아웃
//                //프로필 정보 SQLite 정보삭제
//                LoginManager loginManager = LoginManager.getInstance();
//                loginManager.logOut();
//
//                sqlQuery.deleteUserLogin(mContext);
//                //화면 이동
//                MainActivity.mWebView.loadUrl("javascript:logoutAfter()");
//                finish();
//                break;
//        }
//        return false;
//    }
//}
