package Managers;

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

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : HttpFileDownloadManager
 * @Description : 파일 다운로드 매니저
 * @since 2015-07-02.
 */
public class HttpFileDownloadManager extends AsyncTask<Object, Void, Void>{

    @Override
    protected Void doInBackground(Object... params) {

        int count = 0;

        try {
            URL url = new URL((String )params[0]);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.connect();

            int lenghtOfFile = urlConnection.getContentLength();

            InputStream input = new BufferedInputStream(urlConnection.getInputStream());

            File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/KIKI");

            OutputStream output = new FileOutputStream(path.getPath()+"/1.jpg");

            byte[] data = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                System.out.println("@@ data loading = " + (int) total);
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();

            // 작업이 진행되면서 호출하며 화면의 업그레이드를 담당하게 된다

            urlConnection.disconnect();
        }  catch (IOException e) {
            e.printStackTrace();
        }
        // 수행이 끝나고 리턴하는 값은 다음에 수행될 onProgressUpdate 의 파라미터가 된다

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
