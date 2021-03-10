package com.act.sdk.wifianalyser.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.act.sdk.wifianalyser.R;
import com.act.sdk.wifianalyser.model.NearbyWifi;
import com.act.sdk.wifianalyser.model.connectedDevices.ConnectedDeviceResponse;
import com.act.sdk.wifianalyser.model.deviceInfo.DeviceInfoResponse;
import com.act.sdk.wifianalyser.model.deviceReboot.DeviceRebootResponse;
import com.act.sdk.wifianalyser.model.deviceStatus.DeviceStatusResponse;
import com.act.sdk.wifianalyser.model.getSSID.GetSSIDResponse;
import com.act.sdk.wifianalyser.model.updateDetails.UpdateDetailsResponse;
import com.act.sdk.wifianalyser.model.userInfo.UserInfoResponse;
import com.act.sdk.wifianalyser.presenter.ActWifiListener;

public class OptimizeWifiActivity extends AppCompatActivity implements ActWifiListener {

    CardView updateWifiCard, rebootWifiCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optimize_wifi);
        updateWifiCard = findViewById(R.id.update_wifi);
        rebootWifiCard = findViewById(R.id.reboot);

        updateWifiCard.setOnClickListener(v -> {


            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = (LayoutInflater) this.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View dialogView = inflater.inflate(R.layout.alert_update, null);
            dialogBuilder.setView(dialogView);
            AlertDialog alertDialog = dialogBuilder.create();

            EditText editText = (EditText) dialogView.findViewById(R.id.ssid);
            ImageView imgClose = (ImageView) dialogView.findViewById(R.id.close);
            //imgClose.setOnClickListener(v -> alertDialog.dismiss());
            imgClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

            Button btnUpdate = (Button) dialogView.findViewById(R.id.update);
            /*btnUpdate.setOnClickListener(v -> {
                if (editText.getText().toString().isEmpty()) {
                    Toast.makeText(this, getResources().getString(R.string.ssid),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Intent updateDetailsIntent = new Intent(this,
                            UpdateDetailsActivity.class);
                    updateDetailsIntent.putExtra("ssid", editText.getText().
                            toString());
                    startActivity(updateDetailsIntent);
                }
            });*/


            btnUpdate.setOnClickListener(v1 -> {
                if (editText.getText().toString().isEmpty()) {
                    Toast.makeText(this, getResources().getString(R.string.ssid),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Intent updateDetailsIntent = new Intent(this,
                            UpdateDetailsActivity.class);
                    updateDetailsIntent.putExtra("ssid", editText.getText().
                            toString());
                    startActivity(updateDetailsIntent);
                }
            });


            alertDialog.show();
        });
        rebootWifiCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deviceRebootIntent = new Intent(OptimizeWifiActivity.this,
                        DeviceRebootActivity.class);
               startActivity(deviceRebootIntent);
            }
        });


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