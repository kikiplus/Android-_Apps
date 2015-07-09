/**
 * 
 */
package UIComfonent;

import android.R;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;

/***
 * 
 * @Class Name : Dialog
 * @Description : 다이얼로그
 * @since 2015. 1. 5.
 * @author grapegirl
 * @version 1.0
 */
public class KDialog implements android.content.DialogInterface.OnClickListener {

	/** 다이얼로그 빌드 */
	private AlertDialog.Builder mDialog = null;

	/** 버튼 */
	private int[] mButtons;

	/**
	 * 생성자
	 */
	public KDialog(Context context, String title, String message) {
		mDialog = new Builder(context);
		mDialog.setTitle(title);
		mDialog.setMessage(message);

		mDialog.setPositiveButton(R.string.ok, (android.content.DialogInterface.OnClickListener) this);
		mDialog.setNegativeButton(R.string.cancel, (android.content.DialogInterface.OnClickListener) this);

		mDialog.show();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
	}
}
