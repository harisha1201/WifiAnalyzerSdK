package com.act.sdk.wifianalyser.model.deviceStatus;

/**
 * Created by Sundaramoorthy on 22,February,2021
 *  
 **/
public class DeviceStatusResponse {
    private boolean Online;

    private String Message;

    private int Code;

    private boolean isSuccess;

    private boolean isFailure;

    public void setOnline(boolean Online) {
        this.Online = Online;
    }

    public boolean getOnline() {
        return this.Online;
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
