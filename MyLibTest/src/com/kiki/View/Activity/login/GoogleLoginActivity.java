//package com.kiki.View.Activity.login;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//
//import com.congnavi.android.http.IHttpReceive;
//import com.congnavi.android.http.UserTaskManager;
//import com.congnavi.utils.AppUtils;
//import com.congnavi.utils.CSLog;
//import com.congnavi.utils.ConstUtils;
//import com.congnavi.utils.sqlite.SQLQuery;
//import com.google.android.gms.auth.GoogleAuthException;
//import com.google.android.gms.auth.GoogleAuthUtil;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.Scopes;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.common.api.ResultCallback;
//import com.google.android.gms.common.api.Scope;
//import com.google.android.gms.common.api.Status;
//import com.google.android.gms.plus.Plus;
//import com.google.android.gms.plus.model.people.Person;
//import com.itw.android.R;
//import com.itw.android.activity.MainActivity;
//import com.itw.android.data.Login;
//import com.itw.android.data.SettingData;
//
//import java.io.IOException;
//
//
///**
// * Minimal activity demonstrating basic Google Sign-In.
// */
//public class GoogleLoginActivity extends Activity implements IHttpReceive, Handler.Callback,
//        GoogleApiClient.ConnectionCallbacks,
//        GoogleApiClient.OnConnectionFailedListener {
//
//    private GoogleApiClient mGoogleApiClient;
//    private static Handler mGoogleHandler;
//
//    private Login mLoginData = null;
//
//    private boolean mResolvingError = false;
//
//    private static final int REQUEST_RESOLVE_ERROR = 1010;
//    private static final int LOGIN_OK = 1001;
//    private static final int LOGIN_FAIL = 1002;
//    private static final int REQUEST_TOKEN = 1003;
//    private static final int REQUEST_TOKEN_INFO = 1004;
//    private static final int LOGOUT = 1005;
//
//    private static final String STATE_RESOLVING_ERROR = "STATE_RESOLVING_ERROR";
//
//    /**
//     * 컨텍스트
//     */
//    private static Context mContext;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_google_login);
//
//        mLoginData = new Login();
//
//        mGoogleHandler = new Handler(this);
//        mLoginData = new Login();
//        mContext = this;
//        mResolvingError = savedInstanceState != null && savedInstanceState.getBoolean(STATE_RESOLVING_ERROR, false);
//
//        initBuild();
//    }
//
//    public void initBuild() {
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(Plus.API)
//                .addScope(new Scope(Scopes.PLUS_LOGIN))
//                .build();
//
//        mGoogleApiClient.connect();
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        mGoogleApiClient.disconnect();
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        outState.putBoolean(STATE_RESOLVING_ERROR, mResolvingError);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        CSLog.d(this.getClass().getSimpleName(), "onActivityResult:" + requestCode + ":" + resultCode + ":" + data);
//
//        if (requestCode == REQUEST_RESOLVE_ERROR) {
//            mResolvingError = false;
//            if (resultCode == RESULT_OK) {
//                // Make sure the app is not already connected or attempting to connect
//                if (!mGoogleApiClient.isConnecting() && !mGoogleApiClient.isConnected())
//                    mGoogleApiClient.connect();
//            }
//        }
//
//        if (resultCode == RESULT_CANCELED) {
//            finish();
//        }
//    }
//
//    @Override
//    public void onConnected(Bundle bundle) {
//        CSLog.d(this.getClass().getSimpleName(), "@@ onConnected:" + bundle);
//
//        Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
//        if (currentPerson != null) {
//            String personPhoto = currentPerson.getImage().getUrl();
//            String nickName = currentPerson.getNickname();
//            String name = currentPerson.getName().getFamilyName() + currentPerson.getName().getGivenName();
//            mLoginData.setImgUrl(personPhoto);
//            mLoginData.setNickname(nickName);
//            mLoginData.setUserName(name);
//        }
//
//        String accountName = Plus.AccountApi.getAccountName(mGoogleApiClient);
//        if (accountName != null) {
//            CSLog.d(this.getClass().getSimpleName(), "@@ accountName:" + accountName);
//            mLoginData.setEmail(accountName);
//        }
//
//        Intent intent = getIntent();
//        String isValue = intent.getStringExtra("LOGOUT");
//        if (isValue != null && isValue.equals("Y")) {
//            CSLog.d(this.getClass().getSimpleName(), "@@ 구글 액티비티 로그아웃 시작 ");
//            requestLogout();
//
//        } else {
//            mGoogleHandler.sendEmptyMessage(REQUEST_TOKEN);
//        }
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//        CSLog.d(this.getClass().getSimpleName(), "@@ onConnectionSuspended:" + i);
//        mGoogleHandler.sendEmptyMessage(LOGIN_FAIL);
//    }
//
//    // [START on_connection_failed]
//    @Override
//    public void onConnectionFailed(ConnectionResult result) {
//        CSLog.d(this.getClass().getSimpleName(), "@@ onConnectionFailed:" + result.toString());
//        Log.i("@@ onConnectionFailed", result.hasResolution() + "==" + mResolvingError);
//        if (mResolvingError) {
//            return;
//        } else if (result.hasResolution()) {
//            try {
//                mResolvingError = true;
//                result.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
//            } catch (Exception e) {
//                Log.i("@@ ==", e.toString());
//                // There was an error with the resolution intent. Try again.
//                mGoogleApiClient.connect();
//            }
//        } else {
//            //Toast.makeText(this, result.getErrorCode() , Toast.LENGTH_SHORT).show();
//            Log.i("@@ getErrorCode->", result.getErrorCode() + "---" + "" + result.toString());
//
//            mResolvingError = true;
//        }
//
//    }
//
//    @Override
//    public boolean handleMessage(Message message) {
//        SQLQuery sqlQuery = new SQLQuery();
//        switch (message.what) {
//            case LOGIN_OK://로그인 성공
//                mLoginData.setSNS(ConstUtils.KEY_LOGIN_GOOGLE);
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
//                finish();
//                break;
//            case LOGIN_FAIL:
//                //Toast.makeText(getApplicationContext(), "구글 로그인 실패", Toast.LENGTH_LONG).show();
//                break;
//            case REQUEST_TOKEN://토큰요청
//                AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
//                    @Override
//                    protected String doInBackground(Void... params) {
//                        String token = null;
//                        String accountName = Plus.AccountApi.getAccountName(mGoogleApiClient);
//                        String mScope = "oauth2:https://www.googleapis.com/auth/plus.login";
////                        String scopes = "audience:server:client_id:" + ConstUtils.KEY_GOOGLE_CLIENT_ID+":api_scope:https://www.googleapis.com/auth/plus.login"; // Not the app's client ID.
//
//                        try {
//                            CSLog.d(this.getClass().getSimpleName(), "@@ getApplicationContext : " + getApplicationContext());
//                            CSLog.d(this.getClass().getSimpleName(), "@@ accountName : " + accountName);
//                            CSLog.d(this.getClass().getSimpleName(), "@@ scopes : " + mScope);
//
//                            token = GoogleAuthUtil.getToken(getApplicationContext(), accountName, mScope);
//
//                        } catch (IOException e) {
//                            CSLog.d(this.getClass().getSimpleName(), "@@ Error retrieving ID token. 1");
//                            return null;
//                        } catch (GoogleAuthException e) {
//                            CSLog.d(this.getClass().getSimpleName(), "@@ Error retrieving ID token. 2");
//                            return null;
//                        }
//                        CSLog.d(this.getClass().getSimpleName(), "@@ token : " + token);
//                        mLoginData.setToken(token);
//                        return token;
//                    }
//
//                    @Override
//                    protected void onPostExecute(String token) {
//                        CSLog.d(this.getClass().getSimpleName(), "Access token retrieved:" + token);
//                        mGoogleHandler.sendEmptyMessage(REQUEST_TOKEN_INFO);
//                    }
//                };
//
//                task.execute();
//
//                break;
//
//            case REQUEST_TOKEN_INFO://토큰 정보
//                UserTaskManager taskManager = new UserTaskManager("https://www.googleapis.com/auth/drive", false, "UTF-8", this);
//                taskManager.execute("Bearer " + mLoginData.getToken());
//                CSLog.d(this.getClass().getSimpleName(), " 구글 정보 가져오기");
//                break;
//            case LOGOUT://로그아웃
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
//        CSLog.d(this.getClass().getSimpleName(), "@@ receive : " + obj);
//        switch (type) {
//            case HTTP_OK:
//                String data = (String) obj;
//
//                mLoginData.setSNS(ConstUtils.KEY_LOGIN_GOOGLE);
//                mGoogleHandler.sendEmptyMessage(LOGIN_OK);
//
//                break;
//
//            case HTTP_FAIL:
//                mGoogleHandler.sendEmptyMessage(LOGIN_FAIL);
//
//                break;
//        }
//    }
//
//    /**
//     * 구글 로그아웃
//     */
//    private void requestLogout() {
//        CSLog.d(this.getClass().getSimpleName(), "@@ 구글 로그아웃 요청 : " + mGoogleApiClient.getContext());
//        Log.i("@@ logout", mGoogleApiClient.isConnected() + "====" + mGoogleApiClient.isConnecting());
//
//        if (mGoogleApiClient.isConnected()) {
//            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
//            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
//                @Override
//                public void onResult(Status status) {
//                    CSLog.d(this.getClass().getSimpleName(), "@@ user aceesss revoked !!!");
//                    mGoogleApiClient.disconnect();
//                    mGoogleHandler.sendEmptyMessage(LOGOUT);
//                }
//            });
//        }
//    }
//}