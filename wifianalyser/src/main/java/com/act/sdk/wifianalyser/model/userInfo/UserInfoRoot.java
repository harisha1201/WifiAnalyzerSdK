package com.act.sdk.wifianalyser.model.userInfo;

/**
 * Created by Sundaramoorthy on 22,February,2021
 *  
 **/
public class UserInfoRoot {
    private UserInfoResponse userInfoResponse;

    public void setUserInfoResponse(UserInfoResponse userInfoResponse) {
        this.userInfoResponse = userInfoResponse;
    }

    public UserInfoResponse getUserInfoResponse() {
        return this.userInfoResponse;
    }
}
