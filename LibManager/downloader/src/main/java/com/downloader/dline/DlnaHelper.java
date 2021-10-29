package com.downloader.dline;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class DlnaHelper {
    public static void startDlna(Context context) {
        Intent intent = new Intent(context, DLNAService.class);
        context.startService(intent);
    }

    public static void stopDlna(Context context) {
        Intent intent = new Intent(context, DLNAService.class);
        context.stopService(intent);
        DLNAContainer.getInstance().clear();
    }

    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifiNetworkInfo.isConnected();
    }
}
