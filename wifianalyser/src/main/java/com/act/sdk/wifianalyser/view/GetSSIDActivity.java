package com.act.sdk.wifianalyser.view;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.act.sdk.wifianalyser.ActWifiManager;
import com.act.sdk.wifianalyser.ActWifiManagerBuilder;
import com.act.sdk.wifianalyser.R;
import com.act.sdk.wifianalyser.WifiConstants;
import com.act.sdk.wifianalyser.model.NearbyWifi;
import com.act.sdk.wifianalyser.model.connectedDevices.ConnectedDeviceResponse;
import com.act.sdk.wifianalyser.model.deviceInfo.DeviceInfoResponse;
import com.act.sdk.wifianalyser.model.deviceReboot.DeviceRebootResponse;
import com.act.sdk.wifianalyser.model.deviceStatus.DeviceStatusResponse;
import com.act.sdk.wifianalyser.model.getSSID.GetSSIDResponse;
import com.act.sdk.wifianalyser.model.updateDetails.UpdateDetailsResponse;
import com.act.sdk.wifianalyser.model.userInfo.UserInfoResponse;
import com.act.sdk.wifianalyser.presenter.ActWifiListener;

import org.json.JSONException;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by Sundaramoorthy on 22,February,2021
 **/
public class GetSSIDActivity extends AppCompatActivity implements ActWifiListener {

    TextView txt_view;
    TextView txt_request;
    TextView txt_body;
    ActWifiManager actWifiManager;
    ActWifiManagerBuilder actWifiManagerBuilder;
    WifiManager wifiManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo_activity);

        txt_view = (TextView) findViewById(R.id.txt_view);
        txt_request = (TextView) findViewById(R.id.txt_request);
        actWifiManager = new ActWifiManager(getApplicationContext());
        actWifiManagerBuilder = new ActWifiManagerBuilder();
        wifiManager = actWifiManagerBuilder.create(this);
        txt_request.setText(WifiConstants.GET_SSID_URL);

        try {
            actWifiManager.getSSIDJsonRequest(GetSSIDActivity.this);
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

        getSupportActionBar().setTitle(getResources().getString(R.string.get_ssid));
    }

    @Override
    public void neighbourWIFI(NearbyWifi responseObj) {

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
        Log.i("VOLLEY", responseObj.toString());
    }

    @Override
    public void connectedDevices(ConnectedDeviceResponse responseObj) {

    }

    @Override
    public void allJsonResponse(String responseObj) {
        txt_view.setText(responseObj);

    }

    @Override
    public void updateDetails(UpdateDetailsResponse responseObj) {

    }

    @Override
    public void deviceReboot(DeviceRebootResponse responseObj) {

    }
}
