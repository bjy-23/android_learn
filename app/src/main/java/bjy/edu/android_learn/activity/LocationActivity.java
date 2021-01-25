package bjy.edu.android_learn.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.openxmlformats.schemas.drawingml.x2006.main.STAdjAngle;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import bjy.edu.android_learn.R;

public class LocationActivity extends AppCompatActivity {
    private static final String TAG = "LocationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);


//        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//        startActivity(intent);

        //ACCESS_FINE_LOCATION 和 ACCESS_COARSE_LOCATION 对应获得的位置信息是不一致的！！！
//        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 10001);
            return;
        }

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> allProviders = locationManager.getAllProviders();
//        for (String s : allProviders){
//            Log.i(TAG, "[all provider] " + s);
//        }
        Log.i(TAG, "gps定位可用   " +locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER));
        Log.i(TAG, "net定位可用   " +locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER));
        Log.i(TAG, "passive定位可用   " +locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER));
        List<String> enableProviders = locationManager.getProviders(true);
        for (String s : enableProviders){
//            Log.i(TAG, "[enable provider] " + s);
            if (LocationManager.NETWORK_PROVIDER.equals(s)){
                Location locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (locationNet != null){
                    Log.i(TAG, "locationNet: " + locationNet.getLongitude());

                    Geocoder geocoder = new Geocoder(LocationActivity.this, Locale.getDefault());
                    try {
                        List<Address> address = geocoder.getFromLocation(locationNet.getLatitude(), locationNet.getLongitude(), 1);
                        for (Address address1 : address){
                            Log.i("111222", address1.toString());

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    Log.i(TAG, "locationNet  null");
//                    Log.i(TAG, "locationNet requestLocationUpdates");

                }
            }

            if (LocationManager.GPS_PROVIDER.equals(s)){
                Location locationNet = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (locationNet != null){
                    Log.i(TAG, "locationGPS: " + locationNet.getLongitude());
                }else{
                    Log.i(TAG, "locationGPS  null");
//                    Log.i(TAG, "locationGPS requestLocationUpdates");
                }
            }

            if (LocationManager.PASSIVE_PROVIDER.equals(s)){
                Location locationNet = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                if (locationNet != null){
                    Log.i(TAG, "locationPass: " + locationNet.getLongitude());
                }else {
                    Log.i(TAG, "locationPass:  null");
                }
            }
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 6000, 50, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i(TAG, " onLocationChanged  location " + (location != null));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.i(TAG, " onStatusChanged  status " + (status));
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.i(TAG, " onProviderEnabled  provider " + (provider));
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.i(TAG, " onProviderDisabled  provider " + (provider));
            }
        });

    }
}