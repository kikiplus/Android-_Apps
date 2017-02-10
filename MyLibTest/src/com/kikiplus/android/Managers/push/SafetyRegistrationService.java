package com.kikiplus.android.Managers.push;

import android.app.IntentService;
import android.content.Intent;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.kikiplus.android.Utils.ContextUtils;
import com.kikiplus.android.andUtils.KLog;
import com.kikiplus.android.andUtils.SharedPreferenceUtils;

/***
 * @Class Name : SafetyRegistrationService
 * @Description :GCM 서버로부터 토큰 요청 서비스
 * @author grape girl
 * @since 2015. 6. 8.
 * @version 1.0
 */
public class SafetyRegistrationService extends IntentService {

	private static final String	TAG	= SafetyRegistrationService.class.getSimpleName();


	public SafetyRegistrationService() {
		super( TAG );
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		try {
			synchronized ( TAG ) {
				InstanceID instanceID = InstanceID.getInstance( this );
				String token = instanceID.getToken( ContextUtils.KEY_PROJECT_ID, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null );
				KLog.d(this.getClass().getSimpleName(), "@@ SafetyRegistrationService token : " + token);
				SharedPreferenceUtils.write(this, ContextUtils.KEY_GCM_REGID, token);
			}
		} catch ( Exception e ) {
			KLog.d(this.getClass().getSimpleName(), "SafetyRegistrationService 발급 Exception");
		}
	}

	@Override
	public void onDestroy() {
		KLog.d(this.getClass().getSimpleName(), "SafetyRegistrationService onDestroy");
	}

	
}
