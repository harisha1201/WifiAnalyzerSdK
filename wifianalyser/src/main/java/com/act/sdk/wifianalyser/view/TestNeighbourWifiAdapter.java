package com.act.sdk.wifianalyser.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.act.sdk.wifianalyser.ActWifiManager;
import com.act.sdk.wifianalyser.R;
import com.act.sdk.wifianalyser.model.ConnectedNetworkWifi;
import com.act.sdk.wifianalyser.model.NetworkInfo;

import java.util.ArrayList;


/**
 * Created by Noorul Harisha on 10,February,2021
 * Qubercomm
 */
public class TestNeighbourWifiAdapter extends
        RecyclerView.Adapter<TestNeighbourWifiAdapter.ViewHolder> {

    private final ArrayList<NetworkInfo> NetworkInfoList;
    private final Context context;
    ActWifiManager actWifiManager;

    public TestNeighbourWifiAdapter(Context context, ArrayList<NetworkInfo> NetworkInfoList) {
        this.context = context;
        this.NetworkInfoList = NetworkInfoList;
    }

    @Override
    public int getItemCount() {
        return NetworkInfoList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.
                row_neighbourwifi_layout, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.setIsRecyclable(false);

        NetworkInfo networkInfo = NetworkInfoList.get(position);
        actWifiManager = new ActWifiManager(context);
        ConnectedNetworkWifi connectedNetworkWifi = actWifiManager.
                getConnectedNetworkInfo(context);

        if (networkInfo != null) {
            if (networkInfo.getSSID() != null)
                Log.i("conn ssid", connectedNetworkWifi.getSSID());

            String connSSID = connectedNetworkWifi.getSSID();
            connSSID = connSSID.replace("\"", "");

            if (connSSID.equalsIgnoreCase(networkInfo.getSSID())) {
                if (connectedNetworkWifi.getLinkSpeed() != null) {
                    holder.txtLinkSpeed.setText(context.getResources().getString(R.string.link_speed) + ": "
                            + connectedNetworkWifi.getLinkSpeed());
                    holder.txtLinkSpeed.setVisibility(View.VISIBLE);
                }
            } else
                holder.txtLinkSpeed.setVisibility(View.GONE);

            holder.txtSSID.setText(networkInfo.getSSID());
            holder.txtMacAddress.setText(context.getResources().getString(R.string.mac_address) + ": " + networkInfo.getMacAddress());
            holder.txtChannelName.setText(context.getResources().getString(R.string.channel) +
                    ": " + networkInfo.getChannel());
            holder.txtBandwidth.setText(context.getResources().getString(R.string.band) +
                    ": " + networkInfo.getBandWidth());
            holder.txtChannelNumber.setText(context.getResources().getString(R.string.channel_num) +
                    ": " + networkInfo.getChannelNumber());
            holder.txtSignalLevel.setText(context.getResources().getString(R.string.signal_level) +
                    ": " + networkInfo.getSignalLevel());
            holder.txtSignalStrength.setText(context.getResources().getString(R.string.signal_strength) +
                    ": " + networkInfo.getSignalStrength());
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtSSID;
        private final TextView txtMacAddress;
        private final TextView txtChannelName;
        private final TextView txtBandwidth;
        private final TextView txtChannelNumber;
        private final TextView txtLinkSpeed;
        private final TextView txtSignalStrength;
        private final TextView txtSignalLevel;

        public ViewHolder(View view) {
            super(view);
            this.txtSSID = view.findViewById(R.id.ssid);
            this.txtMacAddress = view.findViewById(R.id.macAddress);
            this.txtChannelName = view.findViewById(R.id.chanel);
            this.txtBandwidth = view.findViewById(R.id.band);
            this.txtChannelNumber = view.findViewById(R.id.chanelnumber);
            this.txtLinkSpeed = view.findViewById(R.id.linkSpeed);
            this.txtSignalStrength = view.findViewById(R.id.signalStrength);
            this.txtSignalLevel = view.findViewById(R.id.signalLevel);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
