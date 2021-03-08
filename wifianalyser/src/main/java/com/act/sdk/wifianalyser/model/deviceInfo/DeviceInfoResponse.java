package com.act.sdk.wifianalyser.model.deviceInfo;

/**
 * Created by Sundaramoorthy on 22,February,2021
 *  
 **/
public class DeviceInfoResponse {
    private int Id;

    private String Sn;

    private String RootObject;

    private int ProductClassId;

    private String Oui;

    private String Manufacturer;

    private String ModelName;

    private String Created;

    private String Updated;

    private String FirmwareVersion;

    private String ProtocolType;

    private String Message;

    private int Code;

    private boolean isSuccess;

    private boolean isFailure;

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

    public void setRootObject(String RootObject) {
        this.RootObject = RootObject;
    }

    public String getRootObject() {
        return this.RootObject;
    }

    public void setProductClassId(int ProductClassId) {
        this.ProductClassId = ProductClassId;
    }

    public int getProductClassId() {
        return this.ProductClassId;
    }

    public void setOui(String Oui) {
        this.Oui = Oui;
    }

    public String getOui() {
        return this.Oui;
    }

    public void setManufacturer(String Manufacturer) {
        this.Manufacturer = Manufacturer;
    }

    public String getManufacturer() {
        return this.Manufacturer;
    }

    public void setModelName(String ModelName) {
        this.ModelName = ModelName;
    }

    public String getModelName() {
        return this.ModelName;
    }

    public void setCreated(String Created) {
        this.Created = Created;
    }

    public String getCreated() {
        return this.Created;
    }

    public void setUpdated(String Updated) {
        this.Updated = Updated;
    }

    public String getUpdated() {
        return this.Updated;
    }

    public void setFirmwareVersion(String FirmwareVersion) {
        this.FirmwareVersion = FirmwareVersion;
    }

    public String getFirmwareVersion() {
        return this.FirmwareVersion;
    }

    public void setProtocolType(String ProtocolType) {
        this.ProtocolType = ProtocolType;
    }

    public String getProtocolType() {
        return this.ProtocolType;
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

