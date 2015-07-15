package Managers;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : FileControlMgr
 * @Description : 파일 관리 클래스
 * @since 2015-07-15.
 */
public class FileControlMgr {

    /**
     * 비트맵 이미지를 파일로 생성하기
     *
     * @param bitmap
     * @param strFilePath
     */
    public void saveBitmapToFileCache(Bitmap bitmap, String strFilePath) {
        String ex_storage = Environment.getExternalStorageDirectory().getAbsolutePath() + "/safetykeeper";

        String filePath = ex_storage + "/" + strFilePath + ".jpg";
        File fileCacheItem = new File(filePath);
        Log.d(conf.Log.LOG_NAME, this.getClass() + " @@ Path : " + fileCacheItem.getPath());
        Log.d(conf.Log.LOG_NAME, this.getClass() + " @@ 이미지 파일 존재 여부 : " + fileCacheItem.exists());
        if (!fileCacheItem.exists()) {
            File folder = new File(ex_storage);
            folder.mkdirs();
        } else {//기존에 있으면
            android.util.Log.d(conf.Log.LOG_NAME, this.getClass() + " @@ 이미지 기존 파일 삭제후 다시쓰기");
            fileCacheItem.delete();
            File folder = new File(ex_storage);
            folder.mkdirs();
            fileCacheItem = new File(filePath);
        }
        OutputStream out = null;
        try {
            out = new FileOutputStream(fileCacheItem);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            android.util.Log.d(conf.Log.LOG_NAME, this.getClass() + " @@ 이미지  파일 생성완료 ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
