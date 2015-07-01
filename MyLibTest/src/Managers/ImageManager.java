/**
 * 
 */
package Managers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;

/**
 * @Class Name : ImageManager.java
 * @Description : 이미지 관련 변환 매니저
 *
 * @author grapegirl
 * @since 2014.08.01
 * @version 1.0
 */
public class ImageManager {

    /*** 컨텍스트 */
    private Context mContext;
    /**
     * 생성자
     */
    public ImageManager(Context context){
        mContext = context;
    }

    /***
     * 비트맵 변환 메소드
     * @param resultId 리소스 아이디
     * @return 비트맵 이미지
     */
    public Bitmap getImageDrawbleToBitmap(int resultId){
        BitmapDrawable bitmapDrawable = (BitmapDrawable)mContext.getResources().getDrawable(resultId);
        Bitmap bitmap = bitmapDrawable.getBitmap();
        return bitmap;
    }

    /***
     * 비트맵 변환 메소드
     * @param drawable 드로어블
     * @return 비트맵 이미지
     */
    public Bitmap getDrawbleToBitmap(Drawable drawable){
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        return bitmap;
    }
    /**
     * 비트맵을 바이트 배열로 변환하여 반환하는메소드
     * @param bitmap 비트맵
     * @return 바이트 배열
     */
    public byte[] getBitmapToByteArray(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}
