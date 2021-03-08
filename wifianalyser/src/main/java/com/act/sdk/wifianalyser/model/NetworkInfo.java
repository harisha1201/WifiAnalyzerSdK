package com.act.sdk.wifianalyser.model;


import java.io.Serializable;

/**
 * Created by Noorul Harisha on 9,February,2021
 *  Qubercomm
 */

public class NetworkInfo implements Serializable {

    private String SSID;
    private String macAddress;
    private String bandWidth;
    private String channel;
    private String signalStrength;
    private String channelNumber;
    private String signalLevel;

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getBandWidth() {
        return bandWidth;
    }

    public void setBandWidth(String bandWidth) {
        this.bandWidth = bandWidth;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannelNumber() {
        return channelNumber;
    }

    public void setChannelNumber(String channelNumber) {
        this.channelNumber = channelNumber;
    }

    public String getSignalLevel() {
        return signalLevel;
    }

    public void setSignalLevel(String signalLevel) {
        this.signalLevel = signalLevel;
    }

    public String getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(String signalStrength) {
        this.signalStrength = signalStrength;
    }
}
