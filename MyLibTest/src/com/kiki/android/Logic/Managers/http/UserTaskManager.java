package com.kiki.android.Logic.Managers.http;

import android.os.AsyncTask;

import com.kiki.android.Listener.IHttpReceive;
import com.kiki.android.Utils.KLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author graepe girl
 * @version 1.0
 * @Class Name : HTTPManager.java
 * @Description : 사용자 정보 HTTP 통신 매니저 클래스(네이버/구글/카카오)
 * @since 2014.08.01
 */
public class UserTaskManager extends AsyncTask<String, Void, Void> {

    /**
     * 접속할 URL
     */
    private String mURl = null;

    /**
     * post방식 true, get방식-false
     */
    private boolean isPost = false;

    /**
     * HTTP 리시브 콜백 메소드 객체
     */
    private IHttpReceive mIHttpReceive = null;

    /**
     * 인코딩 방식
     */
    private String mEncoding = null;


    /**
     * 생성자
     */
    public UserTaskManager(String url, boolean post, String encoding, IHttpReceive receive) {
        mURl = url;
        isPost = post;
        mEncoding = encoding;
        mIHttpReceive = receive;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Void doInBackground(String... params) {
        String data = "";
        String sendData = (String) params[0];
        try {
            URL url = new URL(mURl);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            if (isPost) {
                try {
                    httpURLConnection.setRequestMethod("POST");
                } catch (ProtocolException e) {
                    e.printStackTrace();
                }
                httpURLConnection.setDoOutput(true);
            } else {
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setRequestProperty("Authorization", sendData);
            }
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDefaultUseCaches(false);

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String buffer = null;
                BufferedReader bufferedReader;
                bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), mEncoding));

                while ((buffer = bufferedReader.readLine()) != null) {
                    data += buffer;
                }
                bufferedReader.close();
                mIHttpReceive.onHttpReceive(mIHttpReceive.HTTP_OK, data);
                httpURLConnection.disconnect();
                return null;
            } else {
                mIHttpReceive.onHttpReceive(mIHttpReceive.HTTP_FAIL, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            KLog.d(this.getClass().getSimpleName(), e.toString());
            mIHttpReceive.onHttpReceive(mIHttpReceive.HTTP_FAIL, null);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
