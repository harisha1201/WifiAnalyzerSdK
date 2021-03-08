package com.act.sdk.wifianalyser.model.connectedDevices;

/**
 * Created by Sundaramoorthy on 22,February,2021
 *  
 **/
public class ConnectedDeviceRoot {
    private ConnectedDeviceResponse connectedDeviceResponse;

    public void setConnectedDeviceResponse(ConnectedDeviceResponse connectedDeviceResponse) {
        this.connectedDeviceResponse = connectedDeviceResponse;
    }

    public ConnectedDeviceResponse getConnectedDeviceResponse() {
        return this.connectedDeviceResponse;
    }
}
