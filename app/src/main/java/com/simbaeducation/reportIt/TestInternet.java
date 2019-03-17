package com.simbaeducation.reportIt;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class TestInternet {


    public boolean checkOnlineState(Context context) {
        ConnectivityManager CManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo NInfo = CManager.getActiveNetworkInfo();
        if (NInfo != null && NInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}
