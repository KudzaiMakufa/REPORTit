package com.simbaeducation.reportIt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class adminFromSettings extends AppCompatActivity {

    private static Button buttonLogin;
    private static Db_Operations myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_from_settings);


            MainActivity main = new MainActivity();
            main.AdminFromSettings(adminFromSettings.this);
    }


}
