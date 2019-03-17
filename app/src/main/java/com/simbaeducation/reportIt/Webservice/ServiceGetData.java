package com.simbaeducation.reportIt.Webservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class ServiceGetData extends AsyncTask<String, Void, String> {
    Context context ;

    ProgressDialog mProgressDialog;
    private String res;

    @Override
    protected void onPreExecute() {
      //  smProgressDialog = ProgressDialog.show(LoginActivity.this,
             //   "", "Please wait...");
    }

    @Override
    protected String doInBackground(String[] params) {


        res = null;
        WebserviceApi put = new WebserviceApi();

        put.setParam("id", params[0].toString());


        try {
            //res = put.postData("http://192.168.1.112/leave/public/leave/apinsert");
            res = put.getData("http://192.168.1.112/leave/public/leave/apiretrieve");
            Log.v("res", res);
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;

    }

    protected void onPostExecute(String res) {
        //"Here you get response from server in res"

//        Log.d("kid",res);

    }
}