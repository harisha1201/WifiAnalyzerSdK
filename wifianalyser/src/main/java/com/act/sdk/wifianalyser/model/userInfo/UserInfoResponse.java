package com.act.sdk.wifianalyser.model.userInfo;

/**
 * Created by Sundaramoorthy on 22,February,2021
 *  
 **/

import java.util.List;

public class UserInfoResponse {
    private List<Devices> Devices;

    private String Message;

    private int Code;

    private boolean isSuccess;

    private boolean isFailure;

    public void setDevices(List<Devices> Devices) {
        this.Devices = Devices;
    }

    public List<Devices> getDevices() {
        return this.Devices;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public String getMessage() {
        return this.Message;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public int getCode() {
        return this.Code;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean getIsSuccess() {
        return this.isSuccess;
    }

    public void setIsFailure(boolean isFailure) {
        this.isFailure = isFailure;
    }

    public boolean getIsFailure() {
        return this.isFailure;
    }
}

