package com.kiki.android.Logic.Managers.http;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.kiki.android.Listener.IHttpReceive;
import com.kiki.android.Utils.ByteUtils;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : 파일 업로드 & 다운로드
 * @Description : 파일 업로더
 * @since 2015-07-01.
 */
public class HttpUrlFileUploadManager extends AsyncTask<Object, Void, Void> {

    /**
     * 접속할 URL
     */
    private String mURl = null;

    /**
     * HTTP 리시브 콜백 메소드 객체
     */
    private IHttpReceive mIHttpReceive = null;

    /**
     * 데이터 경계선 문자열
     */
    private String BOUNDARY_STRING = "== DATA END ===";

    /**
     * 생성자
     */
    public HttpUrlFileUploadManager(IHttpReceive receive) {
        mIHttpReceive = receive;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Object... params) {
        mURl = (String) params[0];
        Bitmap bitmap = (Bitmap) params[1];
        try {
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";

            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(ByteUtils.getByteArrayFromBitmap(bitmap));
            URL url = new URL(mURl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);


            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            StringBuffer postDataBuilder = new StringBuffer();
            // 데이터 경계선
            String delimiter = "\r\n--" + BOUNDARY_STRING + "\r\n";
            // 파일 첨부
            postDataBuilder.append(setFile("전달인자", "값"));
            postDataBuilder.append("\r\n");

            dos.writeBytes(postDataBuilder.toString());
            dos.writeBytes(lineEnd);

            bytesAvailable = byteArrayInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            bytesRead = byteArrayInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = byteArrayInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = byteArrayInputStream.read(buffer, 0, bufferSize);
            }

            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            int serverResponseCode = conn.getResponseCode();
            String serverResponseMessage = conn.getResponseMessage();
            if (serverResponseCode == 200) {
                mIHttpReceive.onHttpReceive(this.getClass() + " @@ OK  " + serverResponseMessage);
            } else {
                mIHttpReceive.onHttpReceive(this.getClass() + " @@ Not OK " + serverResponseMessage);
            }
            byteArrayInputStream.close();
            dos.flush();
            dos.close();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            mIHttpReceive.onHttpReceive(this.getClass() + " @@ MalformedURLException ");

        } catch (Exception e) {
            mIHttpReceive.onHttpReceive(this.getClass() + " @@ Exception ");
        }
        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }



    /**
     * Map 형식으로 Key와 Value를 셋팅한다.
     *
     * @param key   : 서버에서 사용할 변수명
     * @param value : 변수명에 해당하는 실제 값
     * @return
     */
    public static String setValue(String key, String value) {
        return "Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n" + value;
    }

    /**
     * 업로드할 파일에 대한 메타 데이터를 설정한다.
     *
     * @param key      : 서버에서 사용할 파일 변수명
     * @param fileName : 서버에서 저장될 파일명
     * @return
     */
    public static String setFile(String key, String fileName) {
        return "Content-Disposition: form-data; name=\"" + key
                + "\";filename=\"" + fileName + "\"\r\n";
    }
}
