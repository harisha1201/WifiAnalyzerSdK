package com.act.sdk.wifianalyser.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.act.sdk.wifianalyser.ActWifiManager;
import com.act.sdk.wifianalyser.ActWifiManagerBuilder;
import com.act.sdk.wifianalyser.R;
import com.act.sdk.wifianalyser.Util;
import com.act.sdk.wifianalyser.WifiReceiver;
import com.act.sdk.wifianalyser.model.ConnectedNetworkWifi;
import com.act.sdk.wifianalyser.model.NearbyWifi;
import com.act.sdk.wifianalyser.model.NetworkInfo;
import com.act.sdk.wifianalyser.model.connectedDevices.ConnectedDeviceResponse;
import com.act.sdk.wifianalyser.model.deviceInfo.DeviceInfoResponse;
import com.act.sdk.wifianalyser.model.deviceReboot.DeviceRebootResponse;
import com.act.sdk.wifianalyser.model.deviceStatus.DeviceStatusResponse;
import com.act.sdk.wifianalyser.model.getSSID.GetSSIDResponse;
import com.act.sdk.wifianalyser.model.updateDetails.UpdateDetailsResponse;
import com.act.sdk.wifianalyser.model.userInfo.UserInfoResponse;
import com.act.sdk.wifianalyser.presenter.ActWifiListener;
import com.act.sdk.wifianalyser.presenter.MainActivityListener;
import com.carlosmuvi.segmentedprogressbar.SegmentedProgressBar;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.ArrayList;
import java.util.List;

public class SDKHomeMainActivity extends AppCompatActivity implements ActWifiListener {
    private final int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 1;
    WifiReceiver receiverWifi;
    WifiManager wifiManager;
    ActWifiManager actWifiManager;
    ActWifiManagerBuilder actWifiManagerBuilder;
    private GoogleApiClient googleApiClient;
    final static int REQUEST_LOCATION = 199;
     ProgressDialog progressDialog;
     Fragment fragment;
     HomeFragment homeFragment;
     MainActivityListener mainActivityListener;
     ActWifiListener actWifiListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdk_home_main);

         fragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_home, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();


        actWifiManager = new ActWifiManager(getApplicationContext());
        actWifiManagerBuilder = new ActWifiManagerBuilder();
        wifiManager = actWifiManagerBuilder.create(this);


        progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();
        this.setFinishOnTouchOutside(true);

        // Todo Location Already on  ... start
        final LocationManager manager = (LocationManager) SDKHomeMainActivity.this.
                getSystemService(Context.LOCATION_SERVICE);
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) &&
                hasGPSDevice(SDKHomeMainActivity.this)) {
            Toast.makeText(SDKHomeMainActivity.this, "Gps already enabled",
                    Toast.LENGTH_SHORT).show();
        }
        // Todo Location Already on  ... end

        if (!hasGPSDevice(SDKHomeMainActivity.this)) {
            Toast.makeText(SDKHomeMainActivity.this, "Gps not Supported",
                    Toast.LENGTH_SHORT).show();
        }

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) &&
                hasGPSDevice(SDKHomeMainActivity.this)) {
            Log.e("TAG", "Gps already enabled");
            Toast.makeText(SDKHomeMainActivity.this, "Gps not enabled",
                    Toast.LENGTH_SHORT).show();
            enableLoc();
        } else {
            Log.e("TAG", "Gps already enabled");
            Toast.makeText(SDKHomeMainActivity.this, "Gps already enabled",
                    Toast.LENGTH_SHORT).show();
        }


        // The request code used in ActivityCompat.requestPermissions()
        // and returned in the Activity's onRequestPermissionsResult()
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

        if (!actWifiManager.isWifiConnected()) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.wifi_on),
                    Toast.LENGTH_LONG).show();
            wifiManager.setWifiEnabled(true);
        }
        if (actWifiManager.isWifiOnOrOff())
            wifiManager.startScan();
        else {
            Toast.makeText(this, getResources().getString(R.string.please_connect_wifi),
                    Toast.LENGTH_SHORT).show();
            if (ActivityCompat.checkSelfPermission(SDKHomeMainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        SDKHomeMainActivity.this, new String[]
                                {Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_ACCESS_FINE_LOCATION);
            }
        }


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        receiverWifi = new WifiReceiver(wifiManager);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        registerReceiver(receiverWifi, intentFilter);
        getWifi();

    }

    private void getWifi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(SDKHomeMainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SDKHomeMainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_ACCESS_FINE_LOCATION);
            } else {
                wifiManager.startScan();
            }
        } else {
            Toast.makeText(SDKHomeMainActivity.this, getResources().
                            getString(R.string.scanning),
                    Toast.LENGTH_SHORT).show();
            wifiManager.startScan();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiverWifi);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(SDKHomeMainActivity.this,
                        getResources().getString(R.string.permission_granted), Toast.LENGTH_SHORT).
                        show();
                wifiManager.startScan();
            } else {
                Toast.makeText(SDKHomeMainActivity.this,
                        getResources().getString(R.string.permission_not_granted),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void neighbourWIFI(NearbyWifi responseObj) {


         progressDialog.cancel();
       // mainActivityListener = (MainActivityListener) this;

        if(responseObj!=null)
        {
            mainActivityListener.neighbourWIFI(responseObj);

        }



    }

    @Override
    public void userInfo(UserInfoResponse userInfoResponseObj) {

    }

    @Override
    public void deviceStatus(DeviceStatusResponse responseObj) {

    }

    @Override
    public void deviceInfo(DeviceInfoResponse responseObj) {

    }

    @Override
    public void getSSID(GetSSIDResponse responseObj) {

    }

    @Override
    public void connectedDevices(ConnectedDeviceResponse responseObj) {
        //     Log.i("connected device size", String.valueOf(responseObj.getNW1().size()));
       // txtDeviceConnected.setText(String.valueOf(responseObj.getNW1().size()));

    }

    @Override
    public void allJsonResponse(String responseObj) {

    }

    @Override
    public void updateDetails(UpdateDetailsResponse responseObj) {

    }

    @Override
    public void deviceReboot(DeviceRebootResponse responseObj) {

    }

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

    private void enableLoc() {

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(SDKHomeMainActivity.this)
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
                    .addOnConnectionFailedListener(connectionResult ->
                            Log.d("Location error", "Location error "
                                    + connectionResult.getErrorCode())).build();
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
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                if (status.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        status.startResolutionForResult(SDKHomeMainActivity.this,
                                REQUEST_LOCATION);
                    } catch (IntentSender.SendIntentException e) {
                        // Ignore the error.
                    }
                }
            }
        });
    }

    void setOnDataListener(MainActivityListener mainActivityListener){
        this.mainActivityListener=mainActivityListener;
    }
    @Override
    protected void onResume() {
        super.onResume();


    }
    public static String getDeviceDensity(Context context){
        String deviceDensity = "";
        switch (context.getResources().getDisplayMetrics().densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                deviceDensity =  0.75 + " ldpi";
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                deviceDensity =  1.0 + " mdpi";
                break;
            case DisplayMetrics.DENSITY_HIGH:
                deviceDensity =  1.5 + " hdpi";
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                deviceDensity =  2.0 + " xhdpi";
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                deviceDensity =  3.0 + " xxhdpi";
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                deviceDensity =  4.0 + " xxxhdpi";
                break;
            default:
                deviceDensity = "Not found";
        }
        return deviceDensity;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_home) {   //this item has your app icon
            Intent homeIntent = new Intent(this,
                    SdkLoginActivity.class);
            startActivity(homeIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}