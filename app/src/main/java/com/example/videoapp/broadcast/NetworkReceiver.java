package com.example.videoapp.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;
public class NetworkReceiver  extends BroadcastReceiver {
    private Context context;

    private static boolean is_check_network = true;


    public NetworkReceiver(Context context) {
        this.context = context;
    }

    public void start() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(this, intentFilter);
    }

    public void stop() {
        context.unregisterReceiver(this);
    }

    public static boolean isIs_check_network() {
        return is_check_network;
    }

    public static void setIs_check_network(boolean is_check_network) {
        NetworkReceiver.is_check_network = is_check_network;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            if (isNetworkAvailable(context)) {
                // Mạng đã kết nối
                if(is_check_network == false){
                    Toast.makeText(context, "Có kết nối mạng", Toast.LENGTH_SHORT).show();
                }
                is_check_network = true;
            } else {
                // Không có kết nối mạng
                Toast.makeText(context, "Không có kết nối mạng", Toast.LENGTH_SHORT).show();
                is_check_network = false;
            }
        }
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }
}
