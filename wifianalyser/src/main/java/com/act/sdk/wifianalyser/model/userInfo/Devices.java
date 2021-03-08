package com.act.sdk.wifianalyser.model.userInfo;

/**
 * Created by Sundaramoorthy on 22,February,2021
 *  
 **/
public class Devices {
    private int Id;

    private String Sn;

    private String Oui;

    private String ModelName;

    private int ErrorCode;

    private String Message;

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getId() {
        return this.Id;
    }

    public void setSn(String Sn) {
        this.Sn = Sn;
    }

    public String getSn() {
        return this.Sn;
    }

    public void setOui(String Oui) {
        this.Oui = Oui;
    }

    public String getOui() {
        return this.Oui;
    }

    public void setModelName(String ModelName) {
        this.ModelName = ModelName;
    }

    public String getModelName() {
        return this.ModelName;
    }

    public void setErrorCode(int ErrorCode) {
        this.ErrorCode = ErrorCode;
    }

    public int getErrorCode() {
        return this.ErrorCode;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public String getMessage() {
        return this.Message;
    }
}

