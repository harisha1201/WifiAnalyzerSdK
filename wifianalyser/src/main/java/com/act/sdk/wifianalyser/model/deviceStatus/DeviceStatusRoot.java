package com.act.sdk.wifianalyser.model.deviceStatus;

/**
 * Created by Sundaramoorthy on 22,February,2021
 *  
 **/
public class DeviceStatusRoot {
    private DeviceStatusResponse deviceStatusResponse;

    public void setDeviceStatusResponse(DeviceStatusResponse deviceStatusResponse) {
        this.deviceStatusResponse = deviceStatusResponse;
    }

    public DeviceStatusResponse getDeviceStatusResponse() {
        return this.deviceStatusResponse;
    }
}
