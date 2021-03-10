package com.act.sdk.wifianalyser;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.act.sdk.wifianalyser.model.ConnectedNetworkWifi;
import com.act.sdk.wifianalyser.model.connectedDevices.ConnectedDeviceResponse;
import com.act.sdk.wifianalyser.model.deviceInfo.DeviceInfoResponse;
import com.act.sdk.wifianalyser.model.deviceReboot.DeviceRebootResponse;
import com.act.sdk.wifianalyser.model.deviceStatus.DeviceStatusResponse;
import com.act.sdk.wifianalyser.model.getSSID.GetSSIDResponse;
import com.act.sdk.wifianalyser.model.updateDetails.UpdateDetailsResponse;
import com.act.sdk.wifianalyser.model.userInfo.UserInfoResponse;
import com.act.sdk.wifianalyser.presenter.ActWifiListener;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * This class contains the methods for wifi connectivity state checking and getting the details of
 * connected wifi
 */
public class ActWifiManager {
    Context context;
  // ActWifiListener actWifiListener;


    private RequestQueue queue;

    public ActWifiManager(Context context) {
        this.context = context;
    }

  /*  public ActWifiManager(Context context, ActWifiListener actWifiListener) {
        this.context = context;
        this.actWifiListener = actWifiListener;
    }*/

    /**
     * This method checks the wifi is on or off and returns the boolean value.
     *
     * @return true if the wifi is on and return false if the wifi is off.
     */
    public Boolean isWifiOnOrOff() {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().
                getSystemService(Context.WIFI_SERVICE);
        return wifiManager.isWifiEnabled();

    }

    /**
     * This method checks the wifi is connected to Access Point or not and returns the boolean
     * value.
     *
     * @return true if the wifi connected to the Access Point and return false if the wifi is not
     * connected to an Access Point
     */
    public Boolean isWifiConnected() {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().
                getSystemService(Context.WIFI_SERVICE);

        if (wifiManager.isWifiEnabled()) { // Wi-Fi adapter is ON

            WifiInfo wifiInfo = wifiManager.getConnectionInfo();

            return wifiInfo.getNetworkId() != -1; // Not connected to an access point
        } else {
            return false; // Wi-Fi adapter is OFF
        }
    }

    /**
     * This method returns the connected network details with object that contains SSID, Gateway,
     * Ip Address, Mac Address, Netmask, ServerAddress.
     *
     * @param context an application context for creating the WifiManager
     * @return the connected wifi information object
     * @throws NullPointerException Occurs if wifi is not connected
     */
    public ConnectedNetworkWifi getConnectedNetworkInfo(Context context) throws
            NullPointerException {
        DhcpInfo dhcpInfo;
        ConnectedNetworkWifi connectedNetworkWifi = new ConnectedNetworkWifi();

        WifiManager manager = (WifiManager) context.getApplicationContext().
                getSystemService(Context.WIFI_SERVICE);
        dhcpInfo = manager.getDhcpInfo();

        if (manager.isWifiEnabled()) {  // Wi-Fi adapter is ON


            WifiInfo wifiInfo = manager.getConnectionInfo(); //Currently connected connection


            if (wifiInfo != null) {
                if (wifiInfo.getNetworkId() != -1) {

                    NetworkInfo.DetailedState state = WifiInfo.getDetailedStateOf(wifiInfo.
                            getSupplicantState());
                    if (state == NetworkInfo.DetailedState.CONNECTED ||
                            state == NetworkInfo.DetailedState.OBTAINING_IPADDR) {
                        connectedNetworkWifi.setSSID(wifiInfo.getSSID());
                        connectedNetworkWifi.setGateway(formatGateway(dhcpInfo.gateway));
                        connectedNetworkWifi.setIpAddress(formatIpAddress(dhcpInfo.ipAddress));
                        connectedNetworkWifi.setMacAddress(wifiInfo.getBSSID());
                        connectedNetworkWifi.setNetMask(formatNetmask(dhcpInfo.netmask));
                        connectedNetworkWifi.setServerAddress(String.valueOf(dhcpInfo.serverAddress));
                        connectedNetworkWifi.setLinkSpeed(String.valueOf(wifiInfo.getLinkSpeed()));


                        Log.i("SSID", wifiInfo.getSSID());
                        Log.i("Gateway", String.valueOf(dhcpInfo.gateway));
                        Log.i("Ip Address", String.valueOf(dhcpInfo.ipAddress));
                        Log.i("Mac Address", wifiInfo.getBSSID());
                        Log.i("Net Mask", String.valueOf(dhcpInfo.netmask));
                        Log.i("Server Address", String.valueOf(dhcpInfo.serverAddress));
                        return connectedNetworkWifi;
                    }
                } else if (wifiInfo.getNetworkId() == -1) {
                    Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                }
            }
        }
        return connectedNetworkWifi;
    }

    /**
     * This method format the ip address with . separated and return the correct format.
     *
     * @param ip Unformatted integer Ip getting from connected wifi
     * @return correct formatted Ip Address
     */
    public String formatIpAddress(int ip) {

        return ((ip >> 24) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                (ip & 0xFF);
    }

    /**
     * This method format the gateway with . separated and return the correct format.
     *
     * @param gateway Unformatted integer Gateway getting from connected wifi
     * @return correct formatted gateway
     */
    public String formatGateway(int gateway) {

        return String.format(Locale.getDefault(), "%d.%d.%d.%d", (gateway & 0xff),
                (gateway >> 8 & 0xff),
                (gateway >> 16 & 0xff), (gateway >> 24 & 0xff));

    }

    private String formatNetmask(int netmask) {
        return String.format(Locale.getDefault(), "%d.%d.%d.%d", (netmask & 0xff),
                (netmask >> 8 & 0xff), (netmask >> 16 & 0xff),
                (netmask >> 24 & 0xff));

    }


    /**
     * This method will decrypt the encoded string and returns the original string
     *
     * @param encodedRequest Encoded string
     * @return Decrypted original String
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws UnsupportedEncodingException
     */
    public String decryptRequest(String encodedRequest) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        byte[] encrypted1 = Base64.decode(encodedRequest, Base64.DEFAULT);
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        SecretKeySpec keySpec = new SecretKeySpec(WifiConstants.secretkey.
                getBytes("utf-8"),
                "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(WifiConstants.iv.
                getBytes("utf-8"));
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] original = cipher.doFinal(encrypted1);
        String originalString = new String(original);
        return originalString.trim();

    }


    /**
     * This method will encrypt the string and returns the decoded string
     *
     * @param input String to encrypt
     * @return Encrypted string
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public String encrypt(String input) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
        SecretKeySpec keySpec = new SecretKeySpec(WifiConstants.secretkey.getBytes(),
                "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(WifiConstants.iv.getBytes());
        Cipher cipher = Cipher.getInstance(WifiConstants.algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.encodeToString(cipherText, Base64.DEFAULT);
    }


    /**
     * This method will return the user info details with json format request
     *
     * @param ctx Application context
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws JSONException
     */
    public void UserInfoJsonRequest(Context ctx) throws NoSuchPaddingException, InvalidKeyException,
            NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException,
            InvalidAlgorithmParameterException, JSONException {
        final int timeout = 60000;

        ActWifiListener actWifiListener;
        actWifiListener = (ActWifiListener) ctx;

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);

        JSONObject object = new JSONObject();
        try {
            object.put("loginname", LocalStore.getLogedName(context));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String encodedRequestBody = object.toString();
        JSONObject requestJson = new JSONObject();
        requestJson.put("request", encrypt(encodedRequestBody));
        Log.i("encrypted post", encrypt(encodedRequestBody));

        String url = WifiConstants.USER_INFO_URL;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                requestJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("UserinfoJsonResponse", response.toString());

                        try {
                            String decryptedJson = decryptRequest(response.get("response").
                                    toString());
                            Log.i("Decrypted json new", decryptedJson);

                            Gson gson = new Gson();
                            UserInfoResponse userInfoResponse = gson.fromJson(decryptedJson,
                                    UserInfoResponse.class);
                            actWifiListener.userInfo(userInfoResponse);
                            actWifiListener.allJsonResponse(decryptedJson);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (BadPaddingException e) {
                            e.printStackTrace();
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        } catch (InvalidKeyException e) {
                            e.printStackTrace();
                        } catch (InvalidAlgorithmParameterException e) {
                            e.printStackTrace();
                        } catch (NoSuchPaddingException e) {
                            e.printStackTrace();
                        } catch (IllegalBlockSizeException e) {
                            e.printStackTrace();
                        }


                    }

                }, error -> {
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }


    /**
     * This method will return the connected device info details with json format request
     *
     * @param ctx Application context
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws JSONException
     */
    public void connectedDeviceJsonRequest(Context ctx) throws NoSuchPaddingException,
            InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException, JSONException {
        ActWifiListener actWifiListener;
        actWifiListener = (ActWifiListener) ctx;

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);

        JSONObject object = new JSONObject();
        try {
            object.put("loginname", LocalStore.getLogedName(context));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String encodedRequestBody = object.toString();
        JSONObject requestJson = new JSONObject();
        requestJson.put("request", encrypt(encodedRequestBody));
        Log.i("encrypted conn post", encrypt(encodedRequestBody));

        String url = WifiConstants.CONNECTED_DEVICES_URL;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                requestJson,
                response -> {
                    Log.i("connectedInfoResponse", response.toString());

                    try {
                        String decryptedJson = decryptRequest(response.get("response").
                                toString());
                        Log.i("conncectedDecJsonnew", decryptedJson);
                        Gson gson = new Gson();
                        ConnectedDeviceResponse connectedDeviceResponse = gson.
                                fromJson(decryptedJson, ConnectedDeviceResponse.class);
                        actWifiListener.connectedDevices(connectedDeviceResponse);
                        actWifiListener.allJsonResponse(decryptedJson);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (BadPaddingException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    } catch (InvalidAlgorithmParameterException e) {
                        e.printStackTrace();
                    } catch (NoSuchPaddingException e) {
                        e.printStackTrace();
                    } catch (IllegalBlockSizeException e) {
                        e.printStackTrace();
                    }

                }, error -> {
        });
        requestQueue.add(jsonObjectRequest);
    }

    /**
     * This method will return the ssid info details with json format request
     *
     * @param ctx Application context
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws JSONException
     */
    public void getSSIDJsonRequest(Context ctx) throws NoSuchPaddingException, InvalidKeyException,
            NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException,
            InvalidAlgorithmParameterException, JSONException {
        final int timeout = 60000;

        ActWifiListener actWifiListener;
        actWifiListener = (ActWifiListener) ctx;

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);

        JSONObject object = new JSONObject();
        JSONArray arrSSID = new JSONArray();
        arrSSID.put("SSID_2");
        arrSSID.put("SSID_5");
        try {
            object.put("loginname", LocalStore.getLogedName(context));
            object.put("parameterType", arrSSID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String encodedRequestBody = object.toString();
        JSONObject requestJson = new JSONObject();
        requestJson.put("request", encrypt(encodedRequestBody));
        Log.i("ssid encrypted post", encrypt(encodedRequestBody));

        String url = WifiConstants.GET_SSID_URL;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                requestJson,
                response -> {
                    Log.i("getssidresponse", response.toString());

                    try {
                        String decryptedJson = decryptRequest(response.get("response").toString());
                        Log.i("Decrypted json new", decryptedJson);

                        Gson gson = new Gson();
                        GetSSIDResponse getSSIDResponse = gson.fromJson(decryptedJson,
                                GetSSIDResponse.class);
                        actWifiListener.getSSID(getSSIDResponse);
                        actWifiListener.allJsonResponse(decryptedJson);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (BadPaddingException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    } catch (InvalidAlgorithmParameterException e) {
                        e.printStackTrace();
                    } catch (NoSuchPaddingException e) {
                        e.printStackTrace();
                    } catch (IllegalBlockSizeException e) {
                        e.printStackTrace();
                    }


                }, error -> {
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }


    /**
     * This method will return the device status details with json format request
     *
     * @param ctx Application context
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws JSONException
     */
    public void deviceStatusJsonRequest(Context ctx) throws NoSuchPaddingException,
            InvalidKeyException,
            NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException,
            InvalidAlgorithmParameterException, JSONException {
        final int timeout = 60000;

        ActWifiListener actWifiListener;
        actWifiListener = (ActWifiListener) ctx;

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);

        JSONObject object = new JSONObject();
        try {
            object.put("loginname", LocalStore.getLogedName(context));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String encodedRequestBody = object.toString();
        JSONObject requestJson = new JSONObject();
        requestJson.put("request", encrypt(encodedRequestBody));
        Log.i("encrypted post", encrypt(encodedRequestBody));

        String url = WifiConstants.DEVICE_STATUS_URL;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                requestJson,
                response -> {
                    Log.i("devicestatusresponse", response.toString());

                    try {
                        String decryptedJson = decryptRequest(response.get("response").
                                toString());
                        Log.i("Decrypted json new", decryptedJson);

                        Gson gson = new Gson();
                        DeviceStatusResponse deviceStatusResponse = gson.fromJson(decryptedJson,
                                DeviceStatusResponse.class);
                        actWifiListener.deviceStatus(deviceStatusResponse);
                        actWifiListener.allJsonResponse(decryptedJson);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (BadPaddingException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    } catch (InvalidAlgorithmParameterException e) {
                        e.printStackTrace();
                    } catch (NoSuchPaddingException e) {
                        e.printStackTrace();
                    } catch (IllegalBlockSizeException e) {
                        e.printStackTrace();
                    }


                }, error -> {
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }


    /**
     * This method will return the device info details with json format request
     *
     * @param ctx Application context
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws JSONException
     */
    public void deviceInfoJsonRequest(Context ctx) throws NoSuchPaddingException,
            InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException,
            BadPaddingException,
            InvalidAlgorithmParameterException, JSONException {
        final int timeout = 60000;

        ActWifiListener actWifiListener;
        actWifiListener = (ActWifiListener) ctx;

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);

        JSONObject object = new JSONObject();
        try {
            object.put("loginname", LocalStore.getLogedName(context));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String encodedRequestBody = object.toString();
        JSONObject requestJson = new JSONObject();
        requestJson.put("request", encrypt(encodedRequestBody));
        Log.i("encrypted post", encrypt(encodedRequestBody));

        String url = WifiConstants.DEVICE_INFO_URL;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                requestJson,
                response -> {
                    Log.i("UserinfoJsonResponse", response.toString());

                    try {
                        String decryptedJson = decryptRequest(response.get("response").
                                toString());
                        Log.i("Decrypted json new", decryptedJson);
                        Gson gson = new Gson();
                        DeviceInfoResponse deviceInfoResponse =
                                gson.fromJson(decryptedJson, DeviceInfoResponse.class);
                        actWifiListener.deviceInfo(deviceInfoResponse);
                        actWifiListener.allJsonResponse(decryptedJson);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (BadPaddingException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    } catch (InvalidAlgorithmParameterException e) {
                        e.printStackTrace();
                    } catch (NoSuchPaddingException e) {
                        e.printStackTrace();
                    } catch (IllegalBlockSizeException e) {
                        e.printStackTrace();
                    }


                }, error -> {
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }


    /**
     * This method is used to get the router details of neighbouring wifi networks
     *
     * @param macAddress Mac address of neighbouring wifi
     * @param ctx        The Application context
     */
    public void getRouterDetails(String macAddress, Context ctx) throws IOException {
        String URL = "http://macvendors.co/api/" + macAddress;

        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = httpclient.execute(new HttpGet(URL + macAddress));
        StatusLine statusLine = response.getStatusLine();
        if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            response.getEntity().writeTo(out);
            String responseString = out.toString();
            Log.i("response vend", responseString);
            out.close();
            //..more logic
        } else {
            //Closes the connection.
            response.getEntity().getContent().close();
            throw new IOException(statusLine.getReasonPhrase());
        }


    }


    /**
     * This method will return the ssid info details with json format request
     *
     * @param ctx Application context
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws JSONException
     */
    public void updateDetailsJsonRequest(Context ctx, String ssid) throws NoSuchPaddingException,
            InvalidKeyException,
            NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException,
            InvalidAlgorithmParameterException, JSONException {
        final int timeout = 400000;
        ActWifiListener actWifiListener;
        actWifiListener = (ActWifiListener) ctx;

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);

        JSONObject object = new JSONObject();
        try {
            object.put("loginname", LocalStore.getLogedName(context));
            object.put("parameterType", "SSID_2");
            object.put("value", ssid);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String encodedRequestBody = object.toString();
        JSONObject requestJson = new JSONObject();
        requestJson.put("request", encrypt(encodedRequestBody));
        Log.i("update details post", encrypt(encodedRequestBody));
        String url = WifiConstants.UPDATE_DETAILS_URL;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                requestJson,
                response -> {
                    Log.i("updatedetailsresponse", response.toString());

                    try {
                        String decryptedJson = decryptRequest(response.get("response").toString());
                        Log.i("Decrypted json new", decryptedJson);

                        Gson gson = new Gson();
                        UpdateDetailsResponse updateDetailsResponse = gson.fromJson(decryptedJson,
                                UpdateDetailsResponse.class);
                        actWifiListener.updateDetails(updateDetailsResponse);
                        actWifiListener.allJsonResponse(decryptedJson);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (BadPaddingException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    } catch (InvalidAlgorithmParameterException e) {
                        e.printStackTrace();
                    } catch (NoSuchPaddingException e) {
                        e.printStackTrace();
                    } catch (IllegalBlockSizeException e) {
                        e.printStackTrace();
                    }


                }, error -> {
            if (error.networkResponse == null) {
                if (error.getClass().equals(TimeoutError.class)) {
                    // Show timeout error message
                    Toast.makeText(ctx,
                            context.getResources().getString(R.string.oops),
                            Toast.LENGTH_LONG).show();
                    Log.d("error", error.toString());
                }
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }


    /**
     * This method will return the device reboot information details with json format request
     *
     * @param ctx Application context
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws JSONException
     */
    public void deviceRebootJsonRequest(Context ctx) throws Exception {
        final int timeout = 60000;

        ActWifiListener actWifiListener;
        actWifiListener = (ActWifiListener) ctx;

        if (queue == null) {
            queue = Volley.newRequestQueue(ctx);
        }

        JSONObject object = new JSONObject();
        object.put("loginname", LocalStore.getLogedName(context));
        JSONObject requestJson = new JSONObject();
        requestJson.put("request", encrypt(object.toString()));

        String url = WifiConstants.DEVICE_REBOOT_URL;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                requestJson,
                response -> {

                    try {
                        String decryptedJson = decryptRequest(response.get("response").toString());
                        Log.i("Decrypted json new", decryptedJson);

                        Gson gson = new Gson();
                        DeviceRebootResponse deviceRebootResponse = gson.fromJson(decryptedJson,
                                DeviceRebootResponse.class);
                        actWifiListener.deviceReboot(deviceRebootResponse);
                        actWifiListener.allJsonResponse(decryptedJson);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }, error -> {

            if (error.networkResponse == null) {
                if (error.getClass().equals(TimeoutError.class)) {
                    // Show timeout error message
                    Toast.makeText(ctx,
                            context.getResources().getString(R.string.oops),
                            Toast.LENGTH_LONG).show();
                }
            }

        });

        try {
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(timeout,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(jsonObjectRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

/*



    */
/**
     * This method will return the connected device info details with json format request
     *
     * @param ctx Application context
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws JSONException
     *//*

    public void connectedDeviceJsonRequestnew(Context ctx) throws NoSuchPaddingException,
            InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException, JSONException {
      //  ActWifiListener actWifiListener;
        actWifiListener = (ActWifiListener) ctx;

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);

        JSONObject object = new JSONObject();
        try {
            object.put("loginname", LocalStore.getLogedName(context));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String encodedRequestBody = object.toString();
        JSONObject requestJson = new JSONObject();
        requestJson.put("request", encrypt(encodedRequestBody));
        Log.i("encrypted conn post", encrypt(encodedRequestBody));

        String url = WifiConstants.CONNECTED_DEVICES_URL;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                requestJson,
                response -> {
                    Log.i("connectedInfoResponse", response.toString());

                    try {
                        String decryptedJson = decryptRequest(response.get("response").
                                toString());
                        Log.i("conncectedDecJsonnew", decryptedJson);
                        Gson gson = new Gson();
                        ConnectedDeviceResponse connectedDeviceResponse = gson.
                                fromJson(decryptedJson, ConnectedDeviceResponse.class);
                        actWifiListener.connectedDevices(connectedDeviceResponse);
                        actWifiListener.allJsonResponse(decryptedJson);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (BadPaddingException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    } catch (InvalidAlgorithmParameterException e) {
                        e.printStackTrace();
                    } catch (NoSuchPaddingException e) {
                        e.printStackTrace();
                    } catch (IllegalBlockSizeException e) {
                        e.printStackTrace();
                    }

                }, error -> {
        });
        requestQueue.add(jsonObjectRequest);
    }
*/

}
