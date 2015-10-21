package com.kiki.View.Activity.TestB;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kiki.View.R;
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
public class DaumMapActivity extends Activity implements View.OnClickListener {

    private MapView mapView;

    private Location mLocation;

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

        findViewById(R.id.button).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
                break;
        }
    }

    LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            // Called when a new location is found by the network location provider.
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            Toast.makeText(getApplicationContext(), latitude + ", " + longitude, Toast.LENGTH_LONG).show();
            mapView.setMapCenterPoint(MapPoint.mapPointWithCONGCoord(latitude, longitude), true);
            setMarker(MapPoint.mapPointWithCONGCoord(latitude, longitude));
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };

    /**
     * 마커 추가
     */
    public void setMarker(MapPoint mapPoint){
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("Default Marker");
        marker.setTag(0);
        marker.setMapPoint(mapPoint);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

        mapView.addPOIItem(marker);
    }
}
