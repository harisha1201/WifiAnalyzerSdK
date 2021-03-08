package com.act.sdk.wifianalyser.presenter;

import com.act.sdk.wifianalyser.model.NearbyWifi;
import com.act.sdk.wifianalyser.model.connectedDevices.ConnectedDeviceResponse;
import com.act.sdk.wifianalyser.model.deviceInfo.DeviceInfoResponse;
import com.act.sdk.wifianalyser.model.deviceReboot.DeviceRebootResponse;
import com.act.sdk.wifianalyser.model.deviceStatus.DeviceStatusResponse;
import com.act.sdk.wifianalyser.model.getSSID.GetSSIDResponse;
import com.act.sdk.wifianalyser.model.updateDetails.UpdateDetailsResponse;
import com.act.sdk.wifianalyser.model.userInfo.UserInfoResponse;
import com.act.sdk.wifianalyser.model.vendorDetails.VendorDetailsRoot;


/**
 * Created by Sundaramoorthy on 10,February,2021
 * Qubercomm
 **/
public interface ActWifiListener {


    void neighbourWIFI(NearbyWifi responseObj);
    void userInfo(UserInfoResponse userInfoResponseObj);
    void deviceStatus(DeviceStatusResponse responseObj);
    void deviceInfo(DeviceInfoResponse responseObj);
    void getSSID(GetSSIDResponse responseObj);
    void connectedDevices(ConnectedDeviceResponse responseObj);
    void allJsonResponse(String responseObj);
    void updateDetails(UpdateDetailsResponse responseObj);
    void deviceReboot(DeviceRebootResponse responseObj);


}
