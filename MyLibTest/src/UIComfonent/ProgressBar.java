/**
 * 
 */
package UIComfonent;

import android.app.ProgressDialog;
import android.content.Context;


/***
 *
 * @Class Name : ProgressBar
 * @Description : 프로그래스바
 * @since 2015. 6. 30.
 * @author grapegirl
 * @version 1.0
 */
public class ProgressBar {

	/** 다이얼로그*/
	private ProgressDialog mDialog = null;

	/** 컨텍스트*/
	private Context m_Context = null;

	/**
	 * 생성자
	 * @param m_context 컨텍스트
	 */
	public ProgressBar(Context m_context) {
		m_Context = m_context;
	}

	/**
	 * 로딩설정 여부 메소드
	 * @param flag 로딩 설정 여부
	 * @param msg 메시지
	 */
	public void setDataLoadingDialog(boolean flag, String msg) {
		if (flag) {
			mDialog = new ProgressDialog(m_Context);
			mDialog.setMessage(msg);
			mDialog.setIndeterminate(true);
			mDialog.setCancelable(false);
			mDialog.show();
		} else {
			if (mDialog != null) {
				mDialog.dismiss();
				mDialog = null;
			}
		}
	}

	public boolean getDialogStatus() {
		return mDialog.isShowing();
	}
}
