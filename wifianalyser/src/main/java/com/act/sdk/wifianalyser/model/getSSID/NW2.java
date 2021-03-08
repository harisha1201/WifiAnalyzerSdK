package com.act.sdk.wifianalyser.model.getSSID;

/**
 * Created by Sundaramoorthy on 22,February,2021
 *  
 **/
public class NW2 {
   /* private String Name;

    private String Value;*/

    private int ErrorCode;

    private String Message;

    private String SSID;

   /* public void setName(String Name) {
        this.Name = Name;
    }

    public String getName() {
        return this.Name;
    }

    public void setValue(String Value) {
        this.Value = Value;
    }

    public String getValue() {
        return this.Value;
    }*/

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

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

}
