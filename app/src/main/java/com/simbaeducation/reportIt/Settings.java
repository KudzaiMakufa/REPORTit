package com.simbaeducation.reportIt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.simbaeducation.reportIt.Registration.CodeCheck;

import org.w3c.dom.Text;

public class Settings extends AppCompatActivity {

    private static Button buttonLogin;
    private static Db_Operations myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        myDb = new Db_Operations(Settings.this);

        final Button btndelete = (Button) findViewById(R.id.btndel);
        final TextView txttitile = (TextView)findViewById(R.id.texttitle);
        final TextView txtemailmessage = (TextView)findViewById(R.id.txtemailmessage);
        final EditText edemail = (EditText)findViewById(R.id.edtxtemail);
        final Button btnsave = (Button)findViewById(R.id.btn_save);


        final ShowMessage sm = new ShowMessage();
        final Cursor res = myDb.getAllData("hodmail");
        if(res.getCount() > 0){
            res.moveToFirst();
            edemail.setVisibility(View.INVISIBLE);
            btnsave.setVisibility(View.INVISIBLE);
            btndelete.setVisibility(View.VISIBLE);
            String email = res.getString(1);
            txtemailmessage.setVisibility(View.VISIBLE);
            txttitile.setText(email);
        }
        else{
            edemail.setVisibility(View.VISIBLE);
            btnsave.setVisibility(View.VISIBLE);
            btndelete.setVisibility(View.INVISIBLE);


        }

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean inserted = myDb.insertHod("hodmail", edemail.getText().toString());
                if (inserted) {
                    sm.showMessage(Settings.this, "Success", "HOD Email Set");
                }
                Intent intent = new Intent(".Settings");
                startActivity(intent);


            }

        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(Settings.this)
                        .setTitle("Confirm")
                        .setMessage("Are you sure u want to delete \n and add new HOD email address")
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {
                                Integer deletehod =  myDb.deleteHod("hodmail",txttitile.getText().toString());
                                if(deletehod > 0){
                                    edemail.setVisibility(View.VISIBLE);
                                    btnsave.setVisibility(View.VISIBLE);
                                    btndelete.setVisibility(View.INVISIBLE);
                                    txtemailmessage.setVisibility(View.GONE);
                                    txttitile.setText("Enter new HOD Email Account");
                                }
                                else{
                                    Toast.makeText(Settings.this, "failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).create().show();




            }
        });


    }


}
