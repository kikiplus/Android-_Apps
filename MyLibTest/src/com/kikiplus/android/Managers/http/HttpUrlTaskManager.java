/**
 *
 */
package com.kikiplus.android.Managers.http;

import android.os.AsyncTask;

import com.kikiplus.android.Listener.IHttpReceive;
import com.kikiplus.android.andUtils.KLog;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : HTTPManager.java
 * @Description : HTTP 통신 매니저 클래스
 * @since 2014.08.01
 */
public class HttpUrlTaskManager extends AsyncTask<String, Void, Void> {

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
    public HttpUrlTaskManager(String url, boolean post, String encoding, IHttpReceive receive) {
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
        String data = null;
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
            }
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDefaultUseCaches(false);

            if (isPost) {//Post 방식으로 데이타 전달시
                OutputStream outputStream = httpURLConnection.getOutputStream();
                if (params != null) {
                    String sendData = (String) params[0];
                    outputStream.write(sendData.getBytes(mEncoding));
                    outputStream.flush();
                    outputStream.close();
                }
            }
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String buffer = null;
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), mEncoding));
                while ((buffer = bufferedReader.readLine()) != null) {
                    data += buffer;
                }
                bufferedReader.close();
                mIHttpReceive.onHttpReceive(mIHttpReceive.HTTP_OK, data);
                httpURLConnection.disconnect();

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
