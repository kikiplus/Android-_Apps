/**
 * 
 */
package UIFrame;

import android.R;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;

/***
 * 
 * @Class Name : Dialog
 * @Description : �˾�â Ŭ����
 * @since 2015. 1. 5.
 * @author grapegirl
 * @version 1.0
 */
public class TestDialog implements android.content.DialogInterface.OnClickListener {

	/** ���̾�α� â */
	private AlertDialog.Builder m_dialog = null;

	/** ���̾�α� â �ϴ� ��ư�� */
	private int[] m_buttons;

	/**
	 * ������
	 */
	public TestDialog(Context context, String title, String message) {
		m_dialog = new Builder(context);
		m_dialog.setTitle(title);
		m_dialog.setMessage(message);

		m_dialog.setPositiveButton(R.string.ok, (android.content.DialogInterface.OnClickListener) this);
		m_dialog.setNegativeButton(R.string.cancel, (android.content.DialogInterface.OnClickListener) this);

		m_dialog.show();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		
	}


}
