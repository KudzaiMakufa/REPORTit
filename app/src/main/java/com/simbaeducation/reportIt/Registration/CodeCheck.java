package com.simbaeducation.reportIt.Registration;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.simbaeducation.reportIt.Db_Operations;
import com.simbaeducation.reportIt.MainActivity;
import com.simbaeducation.reportIt.R;

public class CodeCheck extends AppCompatActivity {
    Db_Operations myDb ;
    EditText edtxtcode ;
    Button Verify;
    ProgressBar progbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_check);
        myDb = new Db_Operations(this);
        CheckCode();
    }

    public void CheckCode(){
        progbar = (ProgressBar)findViewById(R.id.progress);
        edtxtcode = (EditText)findViewById(R.id.edtxtCode) ;
        edtxtcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progbar.setVisibility(View.INVISIBLE);
            }
        });
        Verify = (Button)findViewById(R.id.btnVerify);
        Verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor res = myDb.VerifyCode("Temp_Codes" ,edtxtcode.getText().toString());
                if(res.getCount() == 0) {
                    new AlertDialog.Builder(CodeCheck.this)
                            .setTitle("Notice")
                            .setMessage("Wrong code supplied")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface arg0, int arg1) {

                                }
                            }).create().show();
                    edtxtcode.setText("");
                }
                else {
                    Register reg = new Register();
                    Cursor rez = myDb.gettempCodeEmali("Temp_Codes" ,edtxtcode.getText().toString());
                    String temp_email = null;
                             if (res.moveToFirst())
                             {
                                  temp_email = res.getString(2);
                             }


                   // Toast.makeText(CodeCheck.this, temp_email, Toast.LENGTH_LONG).show();
                  boolean isUpdate = myDb.ActivateUser("Users", 1,temp_email);

                    if (isUpdate == true) {
                        Toast.makeText(CodeCheck.this, "Verification Successful ", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(CodeCheck.this, MainActivity.class);
                        CodeCheck.this.startActivity(intent);
                    } else {
                        Toast.makeText(CodeCheck.this, "User Activation failed", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });



    }
}
