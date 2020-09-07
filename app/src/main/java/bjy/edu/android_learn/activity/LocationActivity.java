package bjy.edu.android_learn.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.openxmlformats.schemas.drawingml.x2006.main.STAdjAngle;

import java.util.List;

import bjy.edu.android_learn.R;

public class LocationActivity extends AppCompatActivity {
    private static final String TAG = "LocationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 10001);
            return;
        }

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> allProviders = locationManager.getAllProviders();
        for (String s : allProviders){
            Log.i(TAG, "[all provider] " + s);
        }
        List<String> enableProviders = locationManager.getProviders(true);
        for (String s : enableProviders){
            Log.i(TAG, "[enable provider] " + s);
            if (LocationManager.NETWORK_PROVIDER.equals(s)){
                Location locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (locationNet != null){

                }
            }
        }
    }
}