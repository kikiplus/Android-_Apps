package com.kiki.View.Activity.TestB;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kiki.View.R;

/**
 * Created by cs on 2015-11-16.
 */
public class KCanvasView extends View {

    private Bitmap[][] imgTrain;
    private Paint m_Paint;
    private RelativeLayout view;

    public KCanvasView(Context context, AttributeSet attr) {
        super(context, attr);

        m_Paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float[] m_TextDirection = {0f, -45f, 90f, 90f, 45f, 0f, -45f, -90f, -90f, 45f, 0f};
        PointF f = new PointF(300, 300);
        int img = 1;
        Bitmap m_train = getTrainBitmap(R.drawable.train_nomal_0);
        canvas.drawBitmap(m_train, 300, 300, m_Paint);

        Bitmap b = getImageBitmap(m_train, "1234", m_TextDirection[0]);


        if (b != null) {
            canvas.drawBitmap(b, 300, 600, m_Paint);
        } else {
            Toast.makeText(getContext(), "없음", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * 열차 이미지와 열차번호 를 쓴 비트맵 반홤 메소드
     *
     * @param bitmap 열차 이미지
     * @param trainC 열차번호
     * @param degree 글자 이미지 방향(각도값)
     * @return
     */
    private Bitmap getImageBitmap(Bitmap bitmap, String trainC, float degree) {

        view = new RelativeLayout(getContext());
        view.setLayoutParams(new RelativeLayout.LayoutParams(75, 75));
        view.setDrawingCacheEnabled(true);

        ImageView imageView = new ImageView(getContext());
        view.addView(imageView);

        TextView textView = new TextView(getContext());
        textView.setText(trainC);
        textView.setRotation(degree);
        textView.setGravity(Gravity.CENTER);

        view.addView(textView);

        view.buildDrawingCache();
        Bitmap newbitmap = view.getDrawingCache();
        view.setDrawingCacheEnabled(false);

        return newbitmap;
    }

    /**
     * 비트맵 이미지 가져오는 메소드
     *
     * @return
     */
    public Bitmap getTrainBitmap(int id) {
        Bitmap bitmap = null;
        String key = getContext().getString(id);
        bitmap = BitmapFactory.decodeResource(getContext().getResources(), id);
        return bitmap;
    }

}
