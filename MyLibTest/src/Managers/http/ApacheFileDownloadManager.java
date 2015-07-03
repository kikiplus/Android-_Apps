package Managers.http;

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

import Interface.IHttpReceive;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : ApacheFileDownloadManager
 * @Description : 아파치 파일 http 파일 다운로드 매니저
 * @since 2015-07-03.
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

        Log.d(conf.Log.LOG_NAME, "@@ url : " + mUrl);
        HttpGet httpGet = new HttpGet(mUrl);
        HttpClient httpClient = new DefaultHttpClient();
        int count = 0;

        try {
            HttpResponse response = httpClient.execute(httpGet);
            InputStream inputStream = new BufferedInputStream(response.getEntity().getContent());
            if (response == null) {
                mHttpReceive.onHttpReceive(this.getClass() + " HttpResponse Null");
                return null;
            }
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                mHttpReceive.onHttpReceive(this.getClass() + " Http Error [ Code : " + response.getStatusLine().getStatusCode() + " ]");
                return null;
            }

            File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/KIKI");
            if (!path.isFile()) {
                path.mkdir();
            }

            Log.d(conf.Log.LOG_NAME, this.getClass() + " @@ saveFile : " + saveFileName);
            Log.d(conf.Log.LOG_NAME, this.getClass() + " @@ saveFile  path: " + path.getPath() + "/" + saveFileName);
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
                mHttpReceive.onHttpReceive(this.getClass() + " HttpResponse Download Ok");
            } else {
                mHttpReceive.onHttpReceive(this.getClass() + " HttpResponse InputStream null");
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
