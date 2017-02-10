package com.kikiplus.common;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class KLogger {

	private static Context mContext = null;

	private static File mLogFile = null;
	private static boolean isDebuggableFile = true;

	private static boolean isDebuggableConsole = true;
	private static final String TAG = "KLogger";

	public KLogger(Context context) {
		mContext = context;
	}

	private static void createLogFile() {
		if (mLogFile == null) {
			String strPackName = mContext.getPackageName();
			String strRootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + strPackName + "/";
			File folder = new File(strRootPath);
			if (!folder.exists()) {
				folder.mkdir();
			}

			if (folder.mkdir() || folder.isDirectory()) {
				mLogFile = new File(strRootPath, "log_" + TimeUtils.getCurrentTime() + ".txt");
				try {
					if (mLogFile.createNewFile()) {
						// 파일이 생성 되었음
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void setFileLogging(boolean isLogging) {
		isDebuggableFile = isLogging;
	}

	/**
	 * Logcat capture 기능 추가
	 */
	public static void startFileLogging() {
		if (isDebuggableFile) {
			try {
				createLogFile();
				Runtime.getRuntime().exec(new String[] { "logcat", "-d", "-f", mLogFile.getPath() });
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static final void e(String message) {
		if (isDebuggableConsole)
			Log.e(TAG, buildLogMsg(message));
	}

	public static final void w(String message) {
		if (isDebuggableConsole)
			Log.w(TAG, buildLogMsg(message));
	}

	public static final void i(String message) {
		if (isDebuggableConsole)
			Log.i(TAG, buildLogMsg(message));
	}

	public static final void d(String message) {
		if (isDebuggableConsole)
			Log.d(TAG, buildLogMsg(message));
	}

	public static final void v(String message) {
		if (isDebuggableConsole)
			Log.v(TAG, buildLogMsg(message));
	}

	public static String buildLogMsg(String message) {
		StackTraceElement ste = Thread.currentThread().getStackTrace()[4];
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(ste.getFileName().replace(".java", ""));
		sb.append("::");
		sb.append(ste.getMethodName());
		sb.append("]");
		sb.append(message);
		return sb.toString();
	}

}
