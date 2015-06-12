/**
 * 
 */
package UIFrame;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressBar {

	private ProgressDialog m_Dialog = null;

	private Context m_Context = null;

	public ProgressBar(Context m_context) {
		m_Context = m_context;
	}

	public void setDataLoadingDialog(boolean flag, String msg) {
		if (flag) {
			m_Dialog = new ProgressDialog(m_Context);
			m_Dialog.setMessage(msg);
			m_Dialog.setIndeterminate(true);
			m_Dialog.setCancelable(false);
			m_Dialog.show();
		} else {
			if (m_Dialog != null) {
				m_Dialog.dismiss();
				m_Dialog = null;
			}
		}
	}

	public boolean getDialogStatus() {
		return m_Dialog.isShowing();
	}
}
