package com.kiki.View.Activity.TestB;

import android.app.Activity;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.kiki.View.Bean.GpsLocation;
import com.kiki.View.R;
import com.kiki.android.Listener.IGPSReceive;
import com.kiki.android.Listener.IHttpReceive;
import com.kiki.android.Logic.Managers.GPSManager;
import com.kiki.android.Logic.Managers.http.HttpUrlTaskManager;
import com.kiki.android.Utils.AppUtils;
import com.kiki.android.Utils.ContextUtils;
import com.kiki.android.Utils.KLog;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;


/***
 * @author grape girl
 * @version 1.0
 * @Class Name : DaumMapActivity
 * @Description : GPS 이용하여 다음 지도에 표시하기
 * @since 2015-10-20
 */
public class DaumMapActivity extends Activity implements View.OnClickListener, IGPSReceive {

    private String TAG = this.getClass().getSimpleName();

    private MapView mapView;

    private ToggleButton mToggleButton;

    private GPSManager mGpsMgr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daumamp_layout);

        mapView = new MapView(this);
        mapView.setDaumMapApiKey(ContextUtils.KEY_DAUM_MAP_KEY);
        //축소 기능 사용
        mapView.zoomIn(true);
        // 확대 기능 사용
        mapView.zoomOut(true);
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        mGpsMgr = new GPSManager(getApplicationContext(), this);

        mToggleButton = (ToggleButton) findViewById(R.id.map_button);
        mToggleButton.setOnClickListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGpsMgr.stopLocation();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.map_button:
                if (mToggleButton.isChecked()) {
                    AppUtils.toast(this, "startLocation");
                    mGpsMgr.startLocation();
                } else {
                    AppUtils.toast(this, "stopLocation");
                    mGpsMgr.stopLocation();
                }
                break;
        }
    }

    /**
     * 마커 추가
     */
    private void setMarker(MapPoint mapPoint) {
        if (mapPoint != null) {
            MapPOIItem marker = new MapPOIItem();
            marker.setItemName("Default Marker");
            marker.setTag(0);
            marker.setMapPoint(mapPoint);
            marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

            mapView.addPOIItem(marker);
            mapView.setMapCenterPoint(mapPoint, true);
        }
    }

    @Override
    public void onGpsReceive(int type, Object obj) {
        switch (type) {
            case RECEIVE_OK:
            case RECEIVE_UPDATE:
                GpsLocation location = (GpsLocation) obj;
                if (location != null) {
                    setMarker(MapPoint.mapPointWithGeoCoord(location.getLatitude(), location.getLongitude()));
                }
                break;
            case RECEIVE_FAIL:
                break;
        }
    }
}
