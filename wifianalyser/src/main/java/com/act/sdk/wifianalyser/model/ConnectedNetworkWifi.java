package com.act.sdk.wifianalyser.model;

/**
 * Created by Noorul Harisha on 9,February,2021
 *  Qubercomm
 */
public class ConnectedNetworkWifi {

    private String SSID;
    private String macAddress;
    private String ipAddress;
    private String netMask;
    private String serverAddress;
    private String gateway;
    private String linkSpeed;

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

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getNetMaskk() {
        return netMask;
    }

    public void setNetMask(String netMask) {
        this.netMask = netMask;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getLinkSpeed() {
        return linkSpeed;
    }

    public void setLinkSpeed(String linkSpeed) {
        this.linkSpeed = linkSpeed;
    }
}
