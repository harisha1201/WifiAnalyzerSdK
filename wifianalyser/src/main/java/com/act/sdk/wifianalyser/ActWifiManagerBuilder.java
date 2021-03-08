package com.act.sdk.wifianalyser;

import android.content.Context;
import android.net.wifi.WifiManager;

/**
 * This Class contains WifiManager builder Creation
 */

public class ActWifiManagerBuilder {

    /**
     * Create Wifi Manager base to access wifi attributes.
     *
     * @param context Application context.
     * @return wifiManagerBuilder Object.
     */
    public WifiManager create(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().
                getSystemService(Context.WIFI_SERVICE);
        return wifiManager;
    }
}
