/**
 *
 */
package View.UIComfonent;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.test.mihye.R;

import Listener.UIEvent.OnButtonSelectedListener;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : Dialog
 * @Description : 다이얼로그
 * @since 2015. 1. 5.
 */
public class EditDialog {

    /**
     * 다이얼로그 빌드
     */
    private Builder mDialog = null;

    /**
     * 버튼 리스너
     */
    private OnButtonSelectedListener mButtonListener;

    /**
     * 컨텍스트
     */
    private Context mContext;

    /**
     * 입력한 내용
     */
    private String mEditString;

    /**
     * 생성자
     */
    public EditDialog(Context context, String title, String message, OnButtonSelectedListener buttonListener) {
        mContext = context;
        mButtonListener = buttonListener;

        mDialog = new Builder(mContext);
        mDialog.setTitle(title);
        mDialog.setMessage(message);

        //뷰 생성
        LayoutInflater inflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        final View innerView = inflater.inflate(R.layout.dialog_layout, null);
        mDialog.setView(innerView);

        //확인 버튼 선택시
        mDialog.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                EditText editText = (EditText)innerView.findViewById(R.id.dialog_layout_edittext);
                mEditString = editText.getText().toString();
                mButtonListener.onButtonItemSelected(OnButtonSelectedListener.BUTTON_OK, mEditString);
            }
        });

        //취소 버튼 선택시
        mDialog.setNegativeButton(R.string.dialog_cancle, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                mButtonListener.onButtonItemSelected(OnButtonSelectedListener.BUTTON_CANCLE, null);
            }
        });
    }

    /***
     * 다이얼로그 보기
     */
    public void showDialog() {
        mDialog.show();
    }
}
