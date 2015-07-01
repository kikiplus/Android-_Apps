package main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.mihye.R;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Objects;

import Interface.IHttpReceive;
import Managers.HttpFileUploadTaskMangaer;
import Managers.ImageManager;
import UIComfonent.ProgressBar;
import Utils.StringUtils;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : FileUploadActivity
 * @Description : 파일업로드 클래스
 * @since 2015-07-01.
 */
public class HttpFileUploadActivity extends Activity implements View.OnClickListener, Handler.Callback, IHttpReceive {

    /**
     * 업로드 버튼
     */
    private Button mSendBtn = null;

    /**
     * 사진찍기 버튼
     */
    private Button mCametraBtn = null;

    /**
     * 이미지뷰
     */
    private ImageView mImageView = null;

    /**
     * 텍스트뷰
     */
    private TextView mTextView = null;

    /**
     * 이미지 결과 요청
     */
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    /**
     * 핸들러
     */
    private Handler mHandler = null;

    /**
     * 프로그래스바
     */
    private ProgressBar mProgressDialog = null;

    /**
     * 파일 경로
     */
    private String mFilePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fileupload_main_layout);

        mSendBtn = (Button) findViewById(R.id.fileupload_main_sendButton);
        mCametraBtn = (Button) findViewById(R.id.fileupload_main_cametraButton);
        mImageView = (ImageView) findViewById(R.id.fileupload_main_imagview);
        mTextView = (TextView) findViewById(R.id.fileupload_main_textview);
        mSendBtn.setOnClickListener(this);
        mCametraBtn.setOnClickListener(this);
        mHandler = new Handler(this);
        mProgressDialog = new ProgressBar(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fileupload_main_sendButton:
                mProgressDialog.setDataLoadingDialog(true, "파일을 전송하고 있습니다.");
                ImageManager imageManager = new ImageManager(this);


//                Bitmap bitmap = imageManager.getDrawbleToBitmap(mImageView.getDrawable());

                Bitmap bitmap = imageManager.getImageDrawbleToBitmap(R.drawable.tutorial01);
                byte[] imgbyteArr = imageManager.getBitmapToByteArray(bitmap);

                //파일 업로드
                HttpFileUploadTaskMangaer fileUploadTaskMangaer = new HttpFileUploadTaskMangaer("http://safety.congnavi.com/serverApi/banner/bannerWriteProc.do", this);
//                fileUploadTaskMangaer.execute("/storage/emulated/0/KIKI/1435741263548.jpg");
                fileUploadTaskMangaer.execute(imgbyteArr);
                Log.d(conf.Log.LOG_NAME, this.getClass() + " @@ 파일 업로드");
                break;
            case R.id.fileupload_main_cametraButton:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/KIKI");
                    if (!path.exists()) {
                        path.mkdirs();
                    }
                    mFilePath = path.getPath() + "/" + System.currentTimeMillis() + ".jpg";
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mFilePath)));
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Log.d(conf.Log.LOG_NAME, this.getClass() + "onActivityResult");
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 0:
                Bitmap img = (Bitmap) msg.obj;
                mImageView.setImageBitmap(img);
                break;
            case 1:
                mProgressDialog.setDataLoadingDialog(false, null);
                String result = (String) msg.obj;
                mTextView.setText(result);
                break;
        }
        return false;
    }

    @Override
    public void onHttpReceive(Object obj) {
        mHandler.sendMessage(mHandler.obtainMessage(1, obj));
    }
}
