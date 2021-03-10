package com.example.sdklibraryapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.act.sdk.wifianalyser.ActWifiManager;
import com.act.sdk.wifianalyser.model.NearbyWifi;
import com.act.sdk.wifianalyser.model.connectedDevices.ConnectedDeviceResponse;
import com.act.sdk.wifianalyser.model.deviceInfo.DeviceInfoResponse;
import com.act.sdk.wifianalyser.model.deviceReboot.DeviceRebootResponse;
import com.act.sdk.wifianalyser.model.deviceStatus.DeviceStatusResponse;
import com.act.sdk.wifianalyser.model.getSSID.GetSSIDResponse;
import com.act.sdk.wifianalyser.model.updateDetails.UpdateDetailsResponse;
import com.act.sdk.wifianalyser.model.userInfo.UserInfoResponse;
import com.act.sdk.wifianalyser.presenter.ActWifiListener;
import com.act.sdk.wifianalyser.view.SDKHomeMainActivity;

public class AppMainActivity extends AppCompatActivity implements ActWifiListener{
    ActWifiManager actWifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);
/*

        Fragment fragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_home, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
*/

        Intent i=new Intent(AppMainActivity.this, SDKHomeMainActivity.class);
        startActivity(i);


    }

    @Override
    public void neighbourWIFI(NearbyWifi responseObj) {

        Log.i("response wifi",responseObj.getNetworkinfo().toString());
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
}