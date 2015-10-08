package com.kiki.View.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kiki.View.R;
import com.kiki.View.UIComfonent.ProgressBar;
import com.kiki.android.Listener.IHttpReceive;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : FileUploadActivity
 * @Description : 파일업로드 클래스
 * @since 2015-07-01.
 */
public class FileUploadActivity extends Activity implements View.OnClickListener, Handler.Callback, IHttpReceive {

    /**
     * 업로드 버튼
     */
    private Button mSendBtn = null;

    /**
     * 다운로드 버튼
     */
    private Button mDownLoadBtn = null;

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
     * 이미지 사진찍고 파일 저장 결과 요청
     */
    private static final int REQUEST_SAVE_IMAGE = 2;

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

    /**
     * 비트맵 이미지
     */
    private Bitmap mBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.fileupload_main_layout);
//        // 버튼 초기화
//        mSendBtn = (Button) findViewById(R.id.fileupload_main_sendButton);
//        mCametraBtn = (Button) findViewById(R.id.fileupload_main_cametraButton);
//        mDownLoadBtn = (Button) findViewById(R.id.fileupload_main_downloadButton);
//        mSendBtn.setOnClickListener(this);
//        mCametraBtn.setOnClickListener(this);
//        mDownLoadBtn.setOnClickListener(this);
//
//        //이미지뷰, 텍스트뷰 초기화
//        mImageView = (ImageView) findViewById(R.id.fileupload_main_imagview);
//        mTextView = (TextView) findViewById(R.id.fileupload_main_textview);

        //핸들러, 프로그래스바 생성
        mHandler = new Handler(this);
        mProgressDialog = new ProgressBar(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 전송 버튼
        //    case R.id.fileupload_main_sendButton:// 웹 전송 업로드
                //mProgressDialog.setDataLoadingDialog(true, "파일을 전송하고 있습니다.");
                //ApacheFileUploadManager fileUploadTaskMangaer = new ApacheFileUploadManager(this);
                //fileUploadTaskMangaer.execute("url", mBitmap, "test.jpg");

                //HttpUrlFileUploadManager manager = new HttpUrlFileUploadManager(this);
                //manager.execute("url", mBitmap, "test.jpg");
             //   break;
            //case R.id.fileupload_main_cametraButton://사진찍기
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if (intent.resolveActivity(getPackageManager()) != null) {
//                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
//                }
//                break;
          //  case R.id.fileupload_main_downloadButton: // 다운로드
                //mProgressDialog.setDataLoadingDialog(true, "파일을 다운로드 하고 있습니다.");
                //HttpUrlFileDownloadManager fileDownloadManager = new HttpUrlFileDownloadManager(this);
                //fileDownloadManager.execute("url", "filename.txt");

                //ApacheFileDownloadManager fileDownloadManager = new ApacheFileDownloadManager(this);
                //fileDownloadManager.execute("url","finame.txt");
                //break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Log.d(com.kiki.android.Utils.conf.Log.LOG_NAME, this.getClass() + "onActivityResult");
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mHandler.sendMessage(mHandler.obtainMessage(0, imageBitmap));
        } else if (requestCode == REQUEST_SAVE_IMAGE && resultCode == RESULT_OK) {
            System.out.println("@@ MH " + mFilePath);
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 0:// 이미지 표시
                mBitmap = (Bitmap) msg.obj;
                mImageView.setImageBitmap(mBitmap);
                break;
            case 1:// 텍스트 결과 표시
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
