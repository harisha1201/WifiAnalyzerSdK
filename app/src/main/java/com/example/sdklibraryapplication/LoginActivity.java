package com.example.sdklibraryapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.act.sdk.wifianalyser.ActWifiManager;
import com.act.sdk.wifianalyser.ActWifiManagerBuilder;
import com.act.sdk.wifianalyser.LocalStore;
import com.act.sdk.wifianalyser.Util;
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
import com.act.sdk.wifianalyser.view.SdkLoginActivity;
import com.act.sdk.wifianalyser.view.TestNeighbourWifiActivity;

import org.json.JSONException;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class LoginActivity extends Activity implements ActWifiListener {
    EditText ed_name;
    Button btnTest;
    ActWifiManager actWifiManager;
    ActWifiManagerBuilder actWifiManagerBuilder;
    WifiManager wifiManager;
    Util util;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ed_name = findViewById(com.act.sdk.wifianalyser.R.id.et_login_name);
        btnTest = findViewById(com.act.sdk.wifianalyser.R.id.btn_test);

        progressDialog = new ProgressDialog(this, com.act.sdk.wifianalyser.R.style.MyAlertDialogStyle);
        progressDialog.setMessage(getResources().getString(com.act.sdk.wifianalyser.R.string.loading));

        util = new Util();

        actWifiManager = new ActWifiManager(getApplicationContext());
        actWifiManagerBuilder = new ActWifiManagerBuilder();
        wifiManager = actWifiManagerBuilder.create(this);

        btnTest.setOnClickListener(v -> {
            progressDialog.show();

            LocalStore.setLogedName(LoginActivity.this, ed_name.getText().toString());
            Util.hideKeyboardFrom(this, ed_name);
            try {
                actWifiManager.UserInfoJsonRequest(LoginActivity.this);
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void neighbourWIFI(NearbyWifi responseObj) {

    }

    @Override
    public void userInfo(UserInfoResponse userInfoResponseObj) {
        if (userInfoResponseObj.getCode() == 100) {
            Intent testIntent = new Intent(LoginActivity.this,
                    SDKHomeMainActivity.class);
            startActivity(testIntent);
        } else {

            util.alertWithOk(this, getResources().getString(com.act.sdk.wifianalyser.R.string.invalid_login));
        }
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