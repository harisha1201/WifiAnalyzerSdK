package com.act.sdk.wifianalyser.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.act.sdk.wifianalyser.R;

import java.util.ArrayList;


/**
 * Created by Noorul Harisha on 16,February,2021
 * Qubercomm
 */
public class SDKMainAdapter extends
        RecyclerView.Adapter<SDKMainAdapter.ViewHolder> {

    private final ArrayList<String> mainList;
    private final Context context;

    public SDKMainAdapter(Context context, ArrayList<String> mainList) {
        this.context = context;
        this.mainList = mainList;
    }

    @Override
    public int getItemCount() {
        return mainList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.
                row_layout, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.name.setText(mainList.get(position));

        holder.itemView.setOnClickListener(view -> {

            switch (position) {
                case 0:
                    Intent intent1 = new Intent(context, PermissionActivity.class);
                    context.startActivity(intent1);
                    break;
                case 1:
                    Intent intent = new Intent(context, TestNeighbourWifiActivity.class);
                    context.startActivity(intent);
                    break;
                case 2:
                    Intent userInfointent = new Intent(context, UserInfoActivity.class);
                    context.startActivity(userInfointent);
                    break;
                case 3:
                    Intent deviceStatusintent = new Intent(context, DeviceStatusActivity.class);
                    context.startActivity(deviceStatusintent);
                    break;
                case 4:
                    Intent deviceInfointent = new Intent(context, DeviceInfoActivity.class);
                    context.startActivity(deviceInfointent);
                    break;
                case 5:
                    Intent getSSIDintent = new Intent(context, GetSSIDActivity.class);
                    context.startActivity(getSSIDintent);
                    break;
                case 6:
                    Intent connectedDevicesintent = new Intent(context,
                            ConnectedDeviceActivity.class);
                    context.startActivity(connectedDevicesintent);
                    break;
                case 7:

                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                    LayoutInflater inflater = (LayoutInflater) context.
                            getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    View dialogView = inflater.inflate(R.layout.alert_update, null);
                    dialogBuilder.setView(dialogView);
                    AlertDialog alertDialog = dialogBuilder.create();

                    EditText editText = (EditText) dialogView.findViewById(R.id.ssid);
                    ImageView imgClose = (ImageView) dialogView.findViewById(R.id.close);
                    imgClose.setOnClickListener(v -> alertDialog.dismiss());

                    Button btnUpdate = (Button) dialogView.findViewById(R.id.update);
                    btnUpdate.setOnClickListener(v -> {
                        if (editText.getText().toString().isEmpty()) {
                            Toast.makeText(context, context.getResources().getString(R.string.ssid),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Intent updateDetailsIntent = new Intent(context,
                                    UpdateDetailsActivity.class);
                            updateDetailsIntent.putExtra("ssid", editText.getText().
                                    toString());
                            context.startActivity(updateDetailsIntent);
                        }
                    });
                    alertDialog.show();

                    break;
                case 8:
                    Intent deviceRebootIntent = new Intent(context,
                            DeviceRebootActivity.class);
                    context.startActivity(deviceRebootIntent);
                    break;

            }


        });

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;

        public ViewHolder(View view) {
            super(view);
            this.name = view.findViewById(R.id.name);

        }
    }


}
