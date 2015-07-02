//package Managers;
//
//import android.os.AsyncTask;
//import android.util.Log;
//
//import java.io.BufferedReader;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.DataOutputStream;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.UnsupportedEncodingException;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.ProtocolException;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.HashMap;
//
//import Interface.IHttpReceive;
//
///**
// * @author grapegirl
// * @version 1.0
// * @Class Name : 파일 업로드 & 다운로드
// * @Description : 파일 업로더
// * @since 2015-07-01.
// */
//public class HttpFileTaskMangaer extends AsyncTask<Object, Void, Void> {
//
//    /**
//     * 접속할 URL
//     */
//    private String mURl = null;
//
//    /**
//     * HTTP 리시브 콜백 메소드 객체
//     */
//    private IHttpReceive mIHttpReceive = null;
//
//    /**
//     * 데이터 경계선 문자열
//     */
//    private String BOUNDARY_STRING = "== DATA END ===";
//
//    /**
//     * 생성자
//     */
//    public HttpFileTaskMangaer(String url, IHttpReceive receive) {
//        mURl = url;
//        mIHttpReceive = receive;
//    }
//
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//    }
//
//    @Override
//    protected Void doInBackground(Object... params) {
//        try {
//            String lineEnd = "\r\n";
//            String twoHyphens = "--";
//            String boundary = "*****";
//
//            int bytesRead, bytesAvailable, bufferSize;
//            byte[] buffer;
//            int maxBufferSize = 1 * 1024 * 1024;
//
//            String sourceFile = (String) params[0];
//            FileInputStream fileInputStream = new FileInputStream(sourceFile);
//            URL url = new URL(mURl);
//
//            // Open a HTTP  connection to  the URL
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setDoInput(true); // Allow Inputs
//            conn.setDoOutput(true); // Allow Outputs
//            conn.setUseCaches(false); // Don't use a Cached Copy
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Connection", "Keep-Alive");
//            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
//
//
//            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
//            dos.writeBytes(twoHyphens + boundary + lineEnd);
//            dos.writeBytes(getTest());
//            dos.writeBytes(lineEnd);
//
////            bytesAvailable = fileInputStream.available();
////            bufferSize = Math.min(bytesAvailable, maxBufferSize);
////            buffer = new byte[bufferSize];
////
////            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
////
////            while (bytesRead > 0) {
////                dos.write(buffer, 0, bufferSize);
////                bytesAvailable = fileInputStream.available();
////                bufferSize = Math.min(bytesAvailable, maxBufferSize);
////                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
////            }
////
////            dos.writeBytes(lineEnd);
////            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
//
//            int serverResponseCode = conn.getResponseCode();
//            String serverResponseMessage = conn.getResponseMessage();
//            if (serverResponseCode == 200) {
//                mIHttpReceive.onHttpReceive("OK  " + serverResponseMessage);
//            }else{
//                mIHttpReceive.onHttpReceive("NOT OK " + serverResponseMessage);
//            }
//            fileInputStream.close();
//            dos.flush();
//            dos.close();
//        } catch (MalformedURLException ex) {
//            ex.printStackTrace();
//            mIHttpReceive.onHttpReceive(" MalformedURLException ");
//
//        } catch (Exception e) {
//            mIHttpReceive.onHttpReceive(" Exception ");
//        }
//        return null;
//    }
//
//
//    @Override
//    protected void onPostExecute(Void aVoid) {
//        super.onPostExecute(aVoid);
//    }
//
//
//    private String getTest() {
//        StringBuffer postDataBuilder = new StringBuffer();
//        // 데이터 경계선
//        String delimiter = "\r\n--" + BOUNDARY_STRING + "\r\n";
//
//        //문자열
//        postDataBuilder.append(delimiter);
//        postDataBuilder.append(setValue("gubun", "H"));
//        postDataBuilder.append(delimiter);
//        postDataBuilder.append(setValue("title", "테스트입니다_김미혜"));
//        postDataBuilder.append(delimiter);
//        postDataBuilder.append(setValue("firstIdx", "1"));
//        postDataBuilder.append(delimiter);
//
////        // 파일 첨부
////        postDataBuilder.append(setFile("bannerImg", "temp.jpg"));
////        postDataBuilder.append("\r\n");
//
//        return postDataBuilder.toString();
//    }
//
//    /**
//     * Map 형식으로 Key와 Value를 셋팅한다.
//     *
//     * @param key   : 서버에서 사용할 변수명
//     * @param value : 변수명에 해당하는 실제 값
//     * @return
//     */
//    public static String setValue(String key, String value) {
//        return "Content-Disposition: form-data; name=\""+ key+"\"\r\n\r\n" +value;
//    }
//
//    /**
//     * 업로드할 파일에 대한 메타 데이터를 설정한다.
//     *
//     * @param key      : 서버에서 사용할 파일 변수명
//     * @param fileName : 서버에서 저장될 파일명
//     * @return
//     */
//    public static String setFile(String key, String fileName) {
//        return "Content-Disposition: form-data; name=\"" + key
//                + "\";filename=\"" + fileName + "\"\r\n";
//    }
//
//}
