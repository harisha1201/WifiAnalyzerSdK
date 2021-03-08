package com.act.sdk.wifianalyser.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.act.sdk.wifianalyser.R;
import com.act.sdk.wifianalyser.Util;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.List;

public class PermissionActivity extends AppCompatActivity {

    private GoogleApiClient googleApiClient;
    final static int REQUEST_LOCATION = 199;
    String[] permissionArray = {"Location Permission", "Write External Storage ",
            "Read External Storage"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
        };
        getSupportActionBar().setTitle(getResources().getString(R.string.permissions));

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, permissionArray);

        ListView listView = (ListView) findViewById(R.id.permission_list);
        listView.setAdapter(adapter);
        Util util = new Util();
        if (!util.hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        this.setFinishOnTouchOutside(false);

        // Todo Location Already on  ... start
        final LocationManager manager = (LocationManager) PermissionActivity.this.
                getSystemService(Context.LOCATION_SERVICE);
        // Todo Location Already on  ... end

        if (!hasGPSDevice(PermissionActivity.this)) {
            Toast.makeText(PermissionActivity.this, "Gps not Supported",
                    Toast.LENGTH_SHORT).show();
        }

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) &&
                hasGPSDevice(PermissionActivity.this)) {
            Log.e("TAG", "Gps not enabled");
            Toast.makeText(PermissionActivity.this, "Gps not enabled",
                    Toast.LENGTH_SHORT).show();
            enableLoc();
        } else {
            Log.w("TAG", "Gps already enabled");

        }

    }


    /**
     * Method to check the device has GPS or Not
     *
     * @param context Activity Context
     * @return true if has GPS otherwise returns false
     */
    private boolean hasGPSDevice(Context context) {
        final LocationManager mgr = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        if (providers == null)
            return false;
        return providers.contains(LocationManager.GPS_PROVIDER);
    }

    /**
     * Method to enable the GPS Location
     */
    private void enableLoc() {

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(PermissionActivity.this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {

                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            googleApiClient.connect();
                        }
                    })
                    .addOnConnectionFailedListener(connectionResult -> Log.d("Location error",
                            "Location error " + connectionResult.getErrorCode())).build();
            googleApiClient.connect();
        }

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient,
                        builder.build());
        result.setResultCallback(result1 -> {
            final Status status = result1.getStatus();
            if (status.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    status.startResolutionForResult(PermissionActivity.this,
                            REQUEST_LOCATION);
                } catch (IntentSender.SendIntentException e) {
                    // Ignore the error.
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOCATION) {
            if (resultCode == Activity.RESULT_CANCELED) {
                enableLoc();
            }
        }
    }
}
