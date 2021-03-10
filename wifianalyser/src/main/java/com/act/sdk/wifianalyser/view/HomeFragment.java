package com.act.sdk.wifianalyser.view;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import com.act.sdk.wifianalyser.ActWifiManager;
import com.act.sdk.wifianalyser.R;
import com.act.sdk.wifianalyser.model.ConnectedNetworkWifi;
import com.act.sdk.wifianalyser.model.NearbyWifi;
import com.act.sdk.wifianalyser.model.NetworkInfo;
import com.act.sdk.wifianalyser.presenter.MainActivityListener;
import com.carlosmuvi.segmentedprogressbar.SegmentedProgressBar;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements MainActivityListener {

    final static int REQUEST_LOCATION = 199;
    ProgressDialog progressDialog;
    SegmentedProgressBar segmentedProgressBar;
    TextView conn_band, conn_channel, conn_signal,txtDeviceConnected;
    CardView neighbourWifiCard, optimize_wifiCard,diagnostics_card,wifiSpot_card;
    Button btnScanAgain,btnOptimize;
    LinearLayout ll_optimizie, ll_scan_again;
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
        ll_optimizie=root.findViewById(R.id.optimize_layout);
        ll_scan_again=root.findViewById(R.id.scan_again);

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


        btnScanAgain.setOnClickListener(v -> {
            for (int count = 0; count < 3; count++) {
                segmentedProgressBar.setFillColor(getResources().getColor(R.color.red));
                segmentedProgressBar.setCompletedSegments(count);
                segmentedProgressBar.playSegment(30000);

            }

            ((SDKHomeMainActivity)getActivity()).getWifi();
        });

        ll_scan_again.setOnClickListener(v -> {
            for (int count = 0; count < 3; count++) {
                segmentedProgressBar.setFillColor(getResources().getColor(R.color.red));
                segmentedProgressBar.setCompletedSegments(count);
                segmentedProgressBar.playSegment(30000);

            }

            ((SDKHomeMainActivity)getActivity()).getWifi();
        });


        return root;
    }


    @Override
    public void neighbourWIFI(NearbyWifi responseObj) {

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

