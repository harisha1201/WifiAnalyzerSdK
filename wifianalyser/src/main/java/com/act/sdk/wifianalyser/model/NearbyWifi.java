package com.act.sdk.wifianalyser.model;

import java.util.ArrayList;


/**
 * Created by Noorul Harisha on 9,February,2021
 *  Qubercomm
 */
public class NearbyWifi {


    public ArrayList<NetworkInfo> networkinfo;


    public ArrayList<NetworkInfo> getNetworkinfo() {
        return networkinfo;
    }

    public void setNetworkinfo(ArrayList<NetworkInfo> networkinfo) {
        this.networkinfo = networkinfo;
    }
}
