package com.act.sdk.wifianalyser;


import android.content.Context;
import android.content.SharedPreferences;

public class LocalStore {
    public static final String PREFE_FILE_NAME = "LocalStorageFile";


    public static String getLogedName(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                PREFE_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPref.getString("loggedname", "");
    }

    public static void setLogedName(Context context, String is_loged_in) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                PREFE_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("loggedname", is_loged_in);
        editor.apply();
    }

}
