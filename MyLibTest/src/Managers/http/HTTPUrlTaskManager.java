/**
 *
 */
package Managers.http;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

import Interface.IHttpReceive;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : HTTPManager.java
 * @Description : HTTP 통신 매니저 클래스
 * @since 2014.08.01
 */
public class HTTPUrlTaskManager extends AsyncTask<String, Void, Void> {

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
     * 생성자
     */
    public HTTPUrlTaskManager(String url, boolean post, IHttpReceive receive) {
        mURl = url;
        isPost = post;
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
            URL   url = new URL(mURl);
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

            if(isPost){//Post 방식으로 데이타 전달시
                OutputStream outputStream = httpURLConnection.getOutputStream();
                if(params != null){
                    String sendData = (String)params[0];
                    outputStream.write(sendData.getBytes("UTF-8"));
                    outputStream.flush();
                    outputStream.close();
                }
            }

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String buffer = null;
                BufferedReader bufferedReader;
                if(isPost){
                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
                }else{
                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "EUC-KR"));
                }
                while ((buffer = bufferedReader.readLine()) != null) {
                    data += buffer;
                }
                bufferedReader.close();
                mIHttpReceive.onHttpReceive(data);
                httpURLConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d(conf.Log.LOG_NAME, this.getClass() + "MalformedURLException");
        } catch (ProtocolException e) {
            e.printStackTrace();
            Log.d(conf.Log.LOG_NAME, this.getClass() + "ProtocolException");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.d(conf.Log.LOG_NAME, this.getClass() + "UnsupportedEncodingException");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(conf.Log.LOG_NAME, this.getClass() + "IOException");
        }
        return  null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
