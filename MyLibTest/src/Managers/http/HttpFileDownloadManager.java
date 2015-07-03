package Managers.http;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import Interface.IHttpReceive;
import Utils.StringUtils;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : HttpFileDownloadManager
 * @Description : 파일 다운로드 매니저
 * @since 2015-07-02.
 */
public class HttpFileDownloadManager extends AsyncTask<Object, Void, Void> {

    /**
     * 응답 리시버 객체
     */
    private IHttpReceive mHttpReceive = null;

    /**
     * 생성자
     *
     * @param receive 응답 리시버 객체
     */
    public HttpFileDownloadManager(IHttpReceive receive) {
        mHttpReceive = receive;
    }

    @Override
    protected Void doInBackground(Object... params) {

        int count = 0;

        try {
            URL url = new URL((String) params[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            int lenghtOfFile = urlConnection.getContentLength();

            InputStream input = urlConnection.getInputStream();
            File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/KIKI");

            String saveFile = (String)params[1];
            OutputStream output = new FileOutputStream(path.getPath() + "/"+ saveFile);

            byte[] data = new byte[1024];
            long total = 0;
            if (input != null) {
                while ((count = input.read(data)) != -1) {
                    total += count;
                    System.out.println("@@ data loading = " + (int) total);
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
                mHttpReceive.onHttpReceive("HttpResponse Download Ok");
            }else {
                mHttpReceive.onHttpReceive("HttpResponse InputStream null");
            }
            urlConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
