package com.kiki.android.Logic.Managers.http;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.kiki.android.Listener.IHttpReceive;
import com.kiki.android.Utils.KLog;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : 아파치 파일 http 파일 다운로드 매니저
 * @Description : 생성자
 * @since 2015-07-02.
 */
public class ApacheFileDownloadManager extends AsyncTask<Object, Void, Void> {

    /**
     * 접속 Url
     */
    private String mUrl = null;

    /**
     * 응답 리시버 객체
     */
    private IHttpReceive mHttpReceive = null;

    /**
     * 생성자
     */
    public ApacheFileDownloadManager(IHttpReceive receive) {
        mHttpReceive = receive;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Object... params) {
        mUrl = (String) params[0];
        String saveFileName = (String) params[1];

        KLog.d(this.getClass().getSimpleName(), "@@ url : " + mUrl);
        HttpGet httpGet = new HttpGet(mUrl);
        HttpClient httpClient = new DefaultHttpClient();
        int count = 0;

        try {
            HttpResponse response = httpClient.execute(httpGet);
            InputStream inputStream = new BufferedInputStream(response.getEntity().getContent());
            if (response == null) {
                mHttpReceive.onHttpReceive(mHttpReceive.HTTP_FAIL, this.getClass() + " HttpResponse Null");
                return null;
            }
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                mHttpReceive.onHttpReceive(mHttpReceive.HTTP_OK, this.getClass() + " Http Error [ Code : " + response.getStatusLine().getStatusCode() + " ]");
                return null;
            }

            File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/KIKI");
            if (!path.isFile()) {
                path.mkdir();
            }

            KLog.d(this.getClass().getSimpleName(),  this.getClass() + " @@ saveFile : " + saveFileName);
            KLog.d(this.getClass().getSimpleName(),  this.getClass() + " @@ saveFile  path: " + path.getPath() + "/" + saveFileName);
            OutputStream output = new FileOutputStream(path.getPath() + "/" + saveFileName);

            byte[] data = new byte[1024];
            long total = 0;
            if (inputStream != null) {
                while ((count = inputStream.read(data)) != -1) {
                    total += count;
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                inputStream.close();
                mHttpReceive.onHttpReceive(mHttpReceive.HTTP_OK, this.getClass() + " HttpResponse Download Ok");
            } else {
                mHttpReceive.onHttpReceive(mHttpReceive.HTTP_FAIL, this.getClass() + " HttpResponse InputStream null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
