package com.act.sdk.wifianalyser.view;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import org.json.JSONException;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class HomeFragment extends Fragment implements MainActivityListener {

    final static int REQUEST_LOCATION = 199;
    ProgressDialog progressDialog;
    SegmentedProgressBar segmentedProgressBar;
    TextView conn_band, conn_channel, conn_signal,txtDeviceConnected;
    CardView neighbourWifiCard, optimize_wifiCard,diagnostics_card,wifiSpot_card;
    Button btnScanAgain,btnOptimize;
    ActWifiManager actWifiManager;
    View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.activity_neighbour_wifi, container, false);

        btnScanAgain=root.findViewById(R.id.scanagain);
        conn_band=root.findViewById(R.id.connected_band);
        conn_channel=root.findViewById(R.id.connected_channel);
        conn_signal=root.findViewById(R.id.connected_signal);
        btnScanAgain=root.findViewById(R.id.scanagain);
        txtDeviceConnected=root.findViewById(R.id.device_txt);

        neighbourWifiCard=root.findViewById(R.id.neighbour_wifi_card);
        optimize_wifiCard=root.findViewById(R.id.optimize_wifi);
        diagnostics_card=root.findViewById(R.id.diagonstic_card);
        wifiSpot_card=root.findViewById(R.id.best_wifispot);
        btnOptimize=root.findViewById(R.id.optimize);
        actWifiManager = new ActWifiManager(getContext());
        segmentedProgressBar = (SegmentedProgressBar) root.findViewById(R.id.segmented_progressbar);
        segmentedProgressBar.setSegmentCount(12);
        for (int count = 0; count < 4; count++) {
            segmentedProgressBar.setFillColor(getResources().getColor(R.color.red));
            segmentedProgressBar.setCompletedSegments(count);
            segmentedProgressBar.playSegment(30000);

        }



        //empty segment color
        segmentedProgressBar.setContainerColor(Color.WHITE);
        segmentedProgressBar.playSegment(5000);


        ((SDKHomeMainActivity)getActivity()).setOnDataListener(this);


        return root;
    }


    @Override
    public void neighbourWIFI(NearbyWifi responseObj) {

        //   Log.i("fragment neighbour",responseObj.getNetworkinfo().get(0).getBandWidth());

        ArrayList<NetworkInfo> networkInfo = responseObj.getNetworkinfo();
        ArrayList<NetworkInfo> mainList = null;
        mainList = responseObj.getNetworkinfo();
        ConnectedNetworkWifi connectedNetworkWifi = actWifiManager.
                getConnectedNetworkInfo(getContext());
        for (int i = 0; i < mainList.size(); i++) {


            String connSSID = connectedNetworkWifi.getSSID();
            connSSID = connSSID.replace("\"", "");


            if (connSSID.equalsIgnoreCase(mainList.get(i).getSSID())) {
                if (connectedNetworkWifi.getLinkSpeed() != null) {

                    int signalLevel = Integer.parseInt(mainList.get(i).getSignalLevel());

                    if (signalLevel <= 3)
                        segmentedProgressBar.setFillColor(getResources().getColor(R.color.red));
                    else if (signalLevel > 3 && signalLevel <= 6)
                        segmentedProgressBar.setFillColor(getResources().getColor(R.color.yellow));
                    else if (signalLevel > 6 && signalLevel <= 9)
                        segmentedProgressBar.setFillColor(getResources().getColor(R.color.light_green));
                    else
                        segmentedProgressBar.setFillColor(getResources().getColor(R.color.green));


                    segmentedProgressBar.setCompletedSegments(signalLevel);

                    if (mainList.get(i).getBandWidth() != null)
                        conn_band.setText(mainList.get(i).getBandWidth());
                    if (mainList.get(i).getChannel() != null)
                        conn_channel.setText(mainList.get(i).getChannel());
                    if (mainList.get(i).getSignalStrength() != null)
                        conn_signal.setText(mainList.get(i).getSignalStrength());

                }

            }


        }
    }

}

