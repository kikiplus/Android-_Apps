package Managers;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import Interface.IHttpReceive;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : MultiRequestTaskManager
 * @Description : 생성자
 * @since 2015-07-02.
 */
public class HttpFileUploadTaskManager extends AsyncTask<Object, Void, Void> {

    private String mUrl = null;

    /** 응답 리시버 객체*/
    private IHttpReceive mHttpReceive = null;

    /**
     * 생성자
     * @param url url
     * @param receive 응답 리시버 객체
     */
    public HttpFileUploadTaskManager(String url, IHttpReceive receive) {
        mUrl = url;
        mHttpReceive = receive;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Object... params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(mUrl);
        httpPost.setHeader("Connection", "Keep-Alive");
        httpPost.setHeader("Accept-Charset", "UTF-8");

        Bitmap saveFile = (Bitmap) params[0];
        String saveFileName = (String) params[1];

        MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        reqEntity.addPart("", new ByteArrayBody(getByteArrayFromBitmap(saveFile), saveFileName + ".jpg"));
        httpPost.setEntity(reqEntity);
        HttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response == null) {
            mHttpReceive.onHttpReceive("HttpResponse Null");
            return null;
        }

        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            mHttpReceive.onHttpReceive("Http Error [ Code : " + response.getStatusLine().getStatusCode() + " ]");
            return null;
        }

        HttpEntity resEntity = response.getEntity();
        if (resEntity == null) {
            mHttpReceive.onHttpReceive("HttpResponse Entity Null");
            return null;
        }
        mHttpReceive.onHttpReceive("Upload Success");
        return null;
    }


    private static byte[] getByteArrayFromBitmap(Bitmap map) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        map.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
