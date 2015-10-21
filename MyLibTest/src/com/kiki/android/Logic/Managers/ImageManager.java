/**
 * 
 */
package com.kiki.android.Logic.Managers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kiki.View.R;

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

    /**
     * 뷰로부터 비트맵 이미지를 생성하여 반환하는 메소드
     * @param  view 뷰
     * @return 비트맵 이미지
     */
    public Bitmap getBitmapFromView(View view){
        Bitmap bitmap = Bitmap.createBitmap( view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888 );
        Canvas canvas = new Canvas( bitmap );
        view.draw( canvas );
        return bitmap;
    }

    /**
     * 열차 이미지와 열차번호 를 쓴 비트맵 반홤 메소드
     * @param bitmap 열차 이미지
     * @param text 열차번호
     * @param degree 글자 이미지 방향(각도값)
     * @return
     */
    private Bitmap getImageBitmap(Bitmap bitmap, String text, float degree){
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.train_image_layout, null);
        ((ImageView)view.findViewById( R.id.train_image )).setImageBitmap(bitmap);
        ((TextView)view.findViewById( R.id.train_num )).setText( text );
        ((TextView)view.findViewById( R.id.train_num )).setRotation( degree );

        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        Bitmap newBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.draw(canvas);
        return newBitmap;

    }
}
