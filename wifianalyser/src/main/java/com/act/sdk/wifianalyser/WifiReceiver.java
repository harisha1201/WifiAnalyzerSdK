package com.act.sdk.wifianalyser;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;

import com.act.sdk.wifianalyser.model.NearbyWifi;
import com.act.sdk.wifianalyser.model.NetworkInfo;
import com.act.sdk.wifianalyser.presenter.ActWifiListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This Class contains broadcast receiver to scan and get the neighbour wifi details
 */

public class WifiReceiver extends BroadcastReceiver {
    WifiManager wifiManager;
    StringBuilder sb;
    NetworkInfo networkinfoDetails;
    NearbyWifi nearbyWifi;
    ActWifiListener actWifiListener;
    ActWifiManager actWifiManager;
    @SuppressWarnings("boxing")
    private final static ArrayList<Integer> channelsFrequency = new ArrayList<Integer>(
            Arrays.asList(0, 2412, 2417, 2422, 2427, 2432, 2437, 2442, 2447,
                    2452, 2457, 2462, 2467, 2472, 2484));

    public WifiReceiver(){

    }
    public WifiReceiver(WifiManager wifiManager) {
        this.wifiManager = wifiManager;

    }

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        wifiManager.startScan(); // we need to start scan again to get fresh results ASAP
        if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(action)) {
            sb = new StringBuilder();
            List<ScanResult> wifiList = wifiManager.getScanResults();

            if (wifiList != null) {
                ArrayList<NetworkInfo> networkInfoDetailsList = new ArrayList<>();
                nearbyWifi = new NearbyWifi();

                for (ScanResult scanResult : wifiList) {
                    networkinfoDetails = new NetworkInfo();
                    networkinfoDetails.setSSID(scanResult.SSID);
                    networkinfoDetails.setMacAddress(scanResult.BSSID);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        networkinfoDetails.setChannel(String.valueOf(scanResult.channelWidth));
                    }
                    float bandwidth = (float) (scanResult.frequency) / 1000;
                    double  bandwidthrounded= (Math.floor(bandwidth* 10) / 10);

                    networkinfoDetails.setBandWidth(bandwidthrounded + "GHz");
                    int level = calculateSignalLevel(scanResult.level, 12);
                    networkinfoDetails.setSignalLevel(String.valueOf(level));
                    networkinfoDetails.setSignalStrength(scanResult.level + "dBm");
                    int channelnum = getChannelFromFrequency(scanResult.frequency);
                    networkinfoDetails.setChannelNumber(String.valueOf(channelnum));
                    actWifiManager = new ActWifiManager(context);
                    networkInfoDetailsList.add(networkinfoDetails);
                }

                if (networkInfoDetailsList.size() > 0) {
                    nearbyWifi.setNetworkinfo(networkInfoDetailsList);
                    actWifiListener = (ActWifiListener) context;
                    actWifiListener.neighbourWIFI(nearbyWifi);
                }


            }

        }

    }

    /**
     * This method returns the signal strength which ranges from 0 to numLevels
     *
     * @param rssi      Level getting from ScanResult
     * @param numLevels Number of Level ranges to determine the signal
     * @return The signal level number
     */
    public int calculateSignalLevel(int rssi, int numLevels) {

        // Anything worse than or equal to this will show 0 bars.
        final int MIN_RSSI = -100;

        // Anything better than or equal to this will show the max bars.
        final int MAX_RSSI = -55;
        if (rssi <= MIN_RSSI) {
            return 0;
        } else if (rssi >= MAX_RSSI) {
            return numLevels - 1;
        } else {
            float inputRange = (MAX_RSSI - MIN_RSSI);
            float outputRange = (numLevels - 1);
            return (int) ((float) (rssi - MIN_RSSI) * outputRange / inputRange);
        }
    }

    /**
     * This method returns the channel number
     *
     * @param frequency Frequency of scan results
     * @return the channel number
     */
    public int getChannelFromFrequency(int frequency) {
        return channelsFrequency.indexOf(Integer.valueOf(frequency));
    }

}