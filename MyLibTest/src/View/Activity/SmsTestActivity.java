/**
 * 
 */
package View.Activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.test.mihye.R;

import java.io.UnsupportedEncodingException;

public class SmsTestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.test_layout );

		regPendingIntent();

		String[] stringArr = { "test" };

		for ( int i = 0; i < stringArr.length; i++ ) {

			String[] msg;
			try {
				msg = getArrayToString( stringArr[i], 80 );
			} catch ( UnsupportedEncodingException e ) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}

			sendSMS( "01050666713", msg );
			System.out.println( "====================================" );
			for ( int j = 0; j < msg.length; j++ ) {
				System.out.println( "msg [ " + j + "] : " + msg[j] );
			}
			System.out.println( "====================================" );
		}
	}

	/**
	 * 여러 문자보내기
	 * 
	 * @param phone
	 *            보낼사람들
	 * @param msg
	 *            보낼문자들
	 */
	private void sendSMS(final String phone, final String[] msg) {
		final PendingIntent sentIntent = PendingIntent.getBroadcast( this, 0, new Intent( "SMS_SENT_ACTION" ), 0 );
		final PendingIntent deliveredIntent = PendingIntent.getBroadcast( this, 0, new Intent( "SMS_DELIVERED_ACTION" ), 0 );

		for ( int i = 0; i < msg.length; i++ ) {
			try {
				Thread.sleep( 1000 );
			} catch ( InterruptedException e ) {
				Log.d( "ddddddddd", "@@ InterruptedException Error!! " );
			}
			SmsManager sms = SmsManager.getDefault();
			if ( msg[i].length() > 0 ) sms.sendTextMessage( phone, null, msg[i], sentIntent, deliveredIntent );
		}

	}

	public void regPendingIntent() {
		registerReceiver( new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				System.out.println( "intent : " + intent.toString() );
				switch ( getResultCode() ) {
				case Activity.RESULT_OK :
					// 전송 성공
					Toast.makeText( getApplicationContext(), "전송 완료", Toast.LENGTH_SHORT ).show();
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE :
					// 전송 실패
					Toast.makeText( getApplicationContext(), "전송 실패", Toast.LENGTH_SHORT ).show();
					break;
				case SmsManager.RESULT_ERROR_NO_SERVICE :
					// 서비스 지역 아님
					Toast.makeText( getApplicationContext(), "서비스 지역이 아닙니다", Toast.LENGTH_SHORT ).show();
					break;
				case SmsManager.RESULT_ERROR_RADIO_OFF :
					// 무선 꺼짐
					Toast.makeText( getApplicationContext(), "무선(Radio)가 꺼져있습니다", Toast.LENGTH_SHORT ).show();
					break;
				case SmsManager.RESULT_ERROR_NULL_PDU :
					// PDU 실패
					Toast.makeText( getApplicationContext(), "PDU Null", Toast.LENGTH_SHORT ).show();
					break;
				}
			}
		}, new IntentFilter( "SMS_SENT_ACTION" ) );
		registerReceiver( new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				switch ( getResultCode() ) {
				case Activity.RESULT_OK :
					// 도착 완료
					Toast.makeText( getApplicationContext(), "SMS 도착 완료", Toast.LENGTH_SHORT ).show();
					break;
				case Activity.RESULT_CANCELED :
					// 도착 안됨
					Toast.makeText( getApplicationContext(), "SMS 도착 실패", Toast.LENGTH_SHORT ).show();
					break;
				}
			}
		}, new IntentFilter( "SMS_DELIVERED_ACTION" ) );

	}

	/**
	 * 문자 바이트길이많금 잘라 배열로 가져오기
	 * 
	 * @param data
	 * @param len
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String[] getArrayToString(String data, int len) throws UnsupportedEncodingException {

		if ( data == null ) return null;
		String[] ary = null;
		// try {
		// raw 의 byte
		byte[] rawBytes = data.getBytes( "euc-kr" );
		int rawLength = rawBytes.length;

		System.out.println( "byte length : " + rawLength );

		if ( rawLength > len ) {
			int aryLength = ( rawLength / len ) + ( rawLength % len != 0 ? 1 : 0 );
			ary = new String[aryLength];

			int endCharIndex = 0; // 문자열이 끝나는 위치
			String tmp;
			for ( int i = 0; i < aryLength; i++ ) {

				if ( i == ( aryLength - 1 ) ) {
					tmp = data.substring( endCharIndex );
				} else {

					int useByteLength = 0;
					int rSize = 0;
					for ( ; endCharIndex < data.length(); endCharIndex++ ) {

						if ( data.charAt( endCharIndex ) > 0x007F ) {
							useByteLength += 2;
						} else {
							useByteLength++;
						}
						if ( useByteLength > len ) {
							break;
						}
						rSize++;
					}
					tmp = data.substring( ( endCharIndex - rSize ), endCharIndex );
				}

				ary[i] = tmp;
			}

		} else {
			ary = new String[] { data };
		}

		return ary;
	}
}
