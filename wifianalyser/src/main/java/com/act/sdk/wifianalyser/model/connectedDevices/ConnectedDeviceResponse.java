package com.act.sdk.wifianalyser.model.connectedDevices;

/**
 * Created by Sundaramoorthy on 22,February,2021
 *  
 **/

import java.util.List;

public class ConnectedDeviceResponse {
    private String Message;

    private int Code;

    private boolean isSuccess;

    private boolean isFailure;

    private List<String> NW2;

    private List<NW1> NW1;

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

    public void setNW2(List<String> NW2) {
        this.NW2 = NW2;
    }

    public List<String> getNW2() {
        return this.NW2;
    }

    public void setNW1(List<NW1> NW1) {
        this.NW1 = NW1;
    }

    public List<NW1> getNW1() {
        return this.NW1;
    }
}
