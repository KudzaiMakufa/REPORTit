package com.simbaeducation.reportIt;

import android.app.AlertDialog;
import android.content.Context;

public class ShowMessage {

    public void showMessage(Context context ,String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
