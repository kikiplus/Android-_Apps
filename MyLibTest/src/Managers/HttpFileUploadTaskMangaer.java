package Managers;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import Interface.IHttpReceive;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : FileUploadTaskMangaer
 * @Description : 파일 업로더
 * @since 2015-07-01.
 */
public class HttpFileUploadTaskMangaer extends AsyncTask<Object, Void, Void> {

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
    public HttpFileUploadTaskMangaer(String url, IHttpReceive receive) {
        mURl = url;
        mIHttpReceive = receive;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Object... params) {
        Log.d(conf.Log.LOG_NAME, this.getClass() + "@@ doInBackground() 호출");
        String crlf = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        String attachmentName = "bitmap";
        String attachmentFileName = "bitmap.bmp";

        String data = null;
        try {
            URL url = new URL(mURl);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection", "Keep_Alive");
            httpURLConnection.setRequestProperty("Content-Type", "MultiPart/Form-Data;boundary=" + BOUNDARY_STRING);

            DataOutputStream dos = new DataOutputStream(urlConnection.getOutputStream());
            byte[] filePath = (byte[]) params[0];

            String sendData = getTest();
            //문자열 전송
            dos.writeBytes(sendData);
            //파일 전송

//            Log.d(conf.Log.LOG_NAME, this.getClass() + " @@ 파일 업로드 1");
//
//            FileInputStream fileInputStream = new FileInputStream(filePath);

            ByteArrayInputStream  bos = new ByteArrayInputStream(filePath);
            Log.d(conf.Log.LOG_NAME, this.getClass() + " @@ 파일 업로드 12");
            int bytesAvailable = bos.available();
            Log.d(conf.Log.LOG_NAME, this.getClass() + " @@ 파일 업로드 13");
            int maxBufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);
            byte[] buffer = new byte[bufferSize];
            int bytesRead = bos.read(buffer, 0, bufferSize);
            while (bytesRead > 0) {
                // Upload file part(s)
                DataOutputStream dataWrite = new DataOutputStream(httpURLConnection.getOutputStream());
                dataWrite.write(buffer, 0, bufferSize);
                bytesAvailable = bos.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = bos.read(buffer, 0, bufferSize);
            }


            String delimiter = "\r\n--" + BOUNDARY_STRING + "\r\n";
            dos.writeBytes(delimiter); // 반드시 작성해야 한다.
            dos.flush();
            dos.close();


            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String buffer2 = null;
                BufferedReader bufferedReader;
                bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
                while ((buffer2 = bufferedReader.readLine()) != null) {
                    data += buffer2;
                }
                bufferedReader.close();
                Log.d(conf.Log.LOG_NAME, this.getClass() + "doInBackground() 리시브 완료");
                mIHttpReceive.onHttpReceive(data);
                httpURLConnection.disconnect();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d(conf.Log.LOG_NAME, this.getClass() + "MalformedURLException");
            mIHttpReceive.onHttpReceive(this.getClass() + "MalformedURLException ERROR");
        } catch (ProtocolException e) {
            e.printStackTrace();
            Log.d(conf.Log.LOG_NAME, this.getClass() + "ProtocolException");
            mIHttpReceive.onHttpReceive(this.getClass() + "ProtocolException ERROR");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.d(conf.Log.LOG_NAME, this.getClass() + "UnsupportedEncodingException");
            mIHttpReceive.onHttpReceive(this.getClass() + "UnsupportedEncodingException ERROR");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(conf.Log.LOG_NAME, this.getClass() + "IOException");
            mIHttpReceive.onHttpReceive(this.getClass() + "IOException ERROR");
        }
        return null;
    }

    private String getTest() {
        StringBuffer postDataBuilder = new StringBuffer();
        // 데이터 경계선
        String delimiter = "\r\n--" + BOUNDARY_STRING + "\r\n";

        //문자열
        postDataBuilder.append(delimiter);
        postDataBuilder.append(setValue("gubun", "H"));
        postDataBuilder.append(delimiter);
        postDataBuilder.append(setValue("title", "테스트입니다_김미혜"));
        postDataBuilder.append(delimiter);
        postDataBuilder.append(setValue("firstIdx", "1"));
        postDataBuilder.append(delimiter);

        // 파일 첨부
        postDataBuilder.append(setFile("uploadedFile", "temp.jpg"));
        postDataBuilder.append("\r\n");

        return postDataBuilder.toString();
    }

    /**
     * Map 형식으로 Key와 Value를 셋팅한다.
     *
     * @param key   : 서버에서 사용할 변수명
     * @param value : 변수명에 해당하는 실제 값
     * @return
     */
    public static String setValue(String key, String value) {
        return "Content-Disposition: form-data; name=\"" + key + "\"r\n\r\n"
                + value;
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

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

}
