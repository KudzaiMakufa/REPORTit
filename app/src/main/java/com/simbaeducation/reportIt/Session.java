package com.simbaeducation.reportIt;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    private SharedPreferences prefs;

    public Session(Context context) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setuserid(String userid) {
        prefs.edit().putString("Loggeduser", userid).commit();
    }

    public String getuserid() {
        String userid = prefs.getString("Loggeduser","");
        return userid;
    }

    public void setgender(String gender) {
        prefs.edit().putString("Gender", gender).commit();
    }

    public String getgender() {
        String gender = prefs.getString("Gender","");
        return gender;
    }
}