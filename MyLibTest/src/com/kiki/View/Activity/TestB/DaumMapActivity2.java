package com.kiki.View.Activity.TestB;

import android.app.Activity;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.kiki.View.R;
import com.kiki.android.Utils.AppUtils;
import com.kiki.android.Utils.ContextUtils;

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
public class DaumMapActivity2 extends Activity implements View.OnClickListener {

    private MapView mapView;

    private Location mLocation;

    private ToggleButton mToggleButton;

    private final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;

    private final long MIN_TIMEE_CHANGE_FOR_UPDATES = 60 * 1000 * 1;

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

        mToggleButton =  (ToggleButton)findViewById(R.id.map_button);
        mToggleButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.map_button:
                if(mToggleButton.isChecked()){
                    AppUtils.toast(this, "startLocation");
                    getLocation();
                }else{
                    AppUtils.toast(this, "stopLocation");
                    stopLocation();
                }
                break;
        }
    }

    /**
     * 내 위치 가져오기
     */
    private void getLocation(){
        AppUtils.toast(getApplicationContext(), "getLocation");
        LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);

        boolean isGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(isGps){
            if(locationManager != null){
                mLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }else{
            boolean isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if(locationManager != null){
                mLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }

        if(mLocation != null){
            setMarker(MapPoint.mapPointWithGeoCoord(mLocation.getLatitude(), mLocation.getLongitude()));
        }else{
            AppUtils.toast(this, "위치 정보가 없음");
        }
    }

    /**
     * 내 위치 가져오기 중지 메소드
     */
    private void stopLocation(){
        LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationManager.removeUpdates(mLocationListener);
    }
    /**
     * 내위치 정보 리스너
     */
    private LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            Toast.makeText(getApplicationContext(), "위치정보 값 변경 : " +  latitude + ", " + longitude, Toast.LENGTH_LONG).show();
            setMarker(MapPoint.mapPointWithGeoCoord(latitude, longitude));
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            AppUtils.toast(getApplicationContext(), "GPS 상태 변경 :");
        }

        public void onProviderEnabled(String provider) {
            AppUtils.toast(getApplicationContext(), "GPS 상태 가능 :" + provider);
        }

        public void onProviderDisabled(String provider) {
            AppUtils.toast(getApplicationContext(), "GPS 상태 불가능 :" + provider);
        }
    };

    /**
     * 마커 추가
     */
    private void setMarker(MapPoint mapPoint){
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
