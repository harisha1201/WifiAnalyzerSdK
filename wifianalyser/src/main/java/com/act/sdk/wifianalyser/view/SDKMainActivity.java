package com.act.sdk.wifianalyser.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SDKMainActivity extends AppCompatActivity {

    ArrayList<String> mainList = new ArrayList<>(Arrays.asList("App Permissions",
            "Neighbouring Wi-Fi networks",
            "User Info",
            "Device Status",
            "Device Info",
            "Get SSID",
            "Connected Devices",
            "Update Details",
            "Device Reboot"


    ));


    SDKMainAdapter SDKMainAdapter;
    private GoogleApiClient googleApiClient;
    final static int REQUEST_LOCATION = 199;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sdk_activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SDKMainAdapter = new SDKMainAdapter(this, mainList);
        recyclerView.setAdapter(SDKMainAdapter);

        getSupportActionBar().setTitle(getResources().getString(R.string.wifi_settings));

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
        };

        Util util = new Util();
        if (!util.hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        this.setFinishOnTouchOutside(false);

        // Todo Location Already on  ... start
        final LocationManager manager = (LocationManager) SDKMainActivity.this.
                getSystemService(Context.LOCATION_SERVICE);
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) &&
                hasGPSDevice(SDKMainActivity.this)) {
        }
        // Todo Location Already on  ... end

        if (!hasGPSDevice(SDKMainActivity.this)) {
            Toast.makeText(SDKMainActivity.this, "Gps not Supported",
                    Toast.LENGTH_SHORT).show();
        }

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) &&
                hasGPSDevice(SDKMainActivity.this)) {
            Log.e("TAG", "Gps not enabled");
            Toast.makeText(SDKMainActivity.this, "Gps not enabled",
                    Toast.LENGTH_SHORT).show();
            enableLoc();
        } else {
            Log.e("TAG", "Gps already enabled");

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
            googleApiClient = new GoogleApiClient.Builder(SDKMainActivity.this)
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
                    status.startResolutionForResult(SDKMainActivity.this,
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_home) {   //this item has your app icon
            Intent homeIntent = new Intent(SDKMainActivity.this,
                    SdkLoginActivity.class);
            startActivity(homeIntent);
        }
        return super.onOptionsItemSelected(item);
    }


}