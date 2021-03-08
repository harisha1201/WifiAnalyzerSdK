package com.act.sdk.wifianalyser.model.getSSID;

/**
 * Created by Sundaramoorthy on 22,February,2021
 *  
 **/
public class GetSSIDResponse {
    private String Message;

    private int Code;

    private boolean isSuccess;

    private boolean isFailure;

  //  private NW NW;
    private NW1 NW1;

    private NW2 NW2;

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

    public void setNW1(NW1 NW1) {
        this.NW1 = NW1;
    }

    public NW1 getNW1() {
        return this.NW1;
    }

    public void setNW2(NW2 NW2) {
        this.NW2 = NW2;
    }

    public NW2 getNW2() {
        return this.NW2;
    }

  /*  public NW getNW() {
        return NW;
    }

    public void setNW(NW NW) {
        this.NW = NW;
    }*/
}

