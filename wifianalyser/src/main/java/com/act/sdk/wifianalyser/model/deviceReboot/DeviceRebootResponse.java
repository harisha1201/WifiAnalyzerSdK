package com.act.sdk.wifianalyser.model.deviceReboot;

public class DeviceRebootResponse {
    private int Id;
    private String Message;
    private int Code;
    private String Sn;
    private String Oui;
    private Boolean isSuccess;
    private Boolean isFailure;
    private Boolean Push;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getSn() {
        return Sn;
    }

    public void setSn(String sn) {
        Sn = sn;
    }

    public String getOui() {
        return Oui;
    }

    public void setOui(String oui) {
        Oui = oui;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public Boolean getFailure() {
        return isFailure;
    }

    public void setFailure(Boolean failure) {
        isFailure = failure;
    }

    public Boolean getPush() {
        return Push;
    }

    public void setPush(Boolean push) {
        Push = push;
    }
}
