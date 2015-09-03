package View.UIComfonent;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import Listener.UIEvent.OnPopupEventListener;

/***
 * 
 * @Class Name : Dialog
 * @Description : 다이얼로그
 * @since 2015. 1. 5.
 * @author grapegirl
 * @version 1.0
 */
public abstract class KDialog implements View.OnClickListener {

	/** 다이얼로그 */
	private Dialog mDialog = null;

	/** 컨텍스트 */
	protected Context mContext = null;

	/** 다이얼로그 뷰*/
	protected View mDialogView = null;

	/** 다이얼로그 버튼리스너 */
	protected OnPopupEventListener mPopupEventListener = null;

	/**
	 * 생성자 - 다이얼로그 생성
	 * @param context
	 * @param title
	 * @param contentView
	 */
	public KDialog(Context context, String title, int contentView, OnPopupEventListener  popupEventListener) {
		mContext = context;
		mPopupEventListener = popupEventListener;
		mDialog = new Dialog(mContext);
		mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//뷰 생성
		LayoutInflater inflater = (LayoutInflater) context.getSystemService
				(Context.LAYOUT_INFLATER_SERVICE);
		mDialogView = inflater.inflate(contentView, null);
		mDialog.setContentView(mDialogView);
		initDialog();
	}

	/**
	 * 다이얼로그 초기화 메소드
	 */
	protected abstract void initDialog();

	/**
	 * 다이얼로그 해제 메소드
	 */
	protected abstract void destroyDialog();
	/**
	 * 다이얼로그 열기
	 */
	public void showDialog(){
		if(mDialog != null){
			mDialog.show();
		}
	}

	/**
	 * 다이얼로그 닫기
	 */
	public void closeDialog(){
		if(mDialog != null){
			destroyDialog();
			mDialog.dismiss();
		}
	}
}
