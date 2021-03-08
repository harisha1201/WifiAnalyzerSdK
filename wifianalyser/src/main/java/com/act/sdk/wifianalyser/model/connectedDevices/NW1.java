package com.act.sdk.wifianalyser.model.connectedDevices;

/**
 * Created by Sundaramoorthy on 22,February,2021
 *  
 **/
public class NW1 {
    private String Active;

    private String AddressSource;

    private String HostName;

    private String IPAddress;

    private String LeaseTimeRemaining;

    private String MACAddress;

    public void setActive(String Active) {
        this.Active = Active;
    }

    public String getActive() {
        return this.Active;
    }

    public void setAddressSource(String AddressSource) {
        this.AddressSource = AddressSource;
    }

    public String getAddressSource() {
        return this.AddressSource;
    }

    public void setHostName(String HostName) {
        this.HostName = HostName;
    }

    public String getHostName() {
        return this.HostName;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public String getIPAddress() {
        return this.IPAddress;
    }

    public void setLeaseTimeRemaining(String LeaseTimeRemaining) {
        this.LeaseTimeRemaining = LeaseTimeRemaining;
    }

    public String getLeaseTimeRemaining() {
        return this.LeaseTimeRemaining;
    }

    public void setMACAddress(String MACAddress) {
        this.MACAddress = MACAddress;
    }

    public String getMACAddress() {
        return this.MACAddress;
    }
}
