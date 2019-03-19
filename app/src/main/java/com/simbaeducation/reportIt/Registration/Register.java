package com.simbaeducation.reportIt.Registration;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.simbaeducation.reportIt.Db_Operations;
import com.simbaeducation.reportIt.R;
import com.simbaeducation.reportIt.ShowMessage;
import com.simbaeducation.reportIt.TestInternet;

import java.util.Random;

public class Register extends AppCompatActivity {
    Db_Operations myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        myDb = new Db_Operations(this);
        AddData();
    }
    public  String GetEmail(EditText et) {

        return et.toString();
    }

    public  void AddData() {

        final Spinner spngender = (Spinner)findViewById(R.id.spinner);
        final Button Register = (Button) findViewById(R.id.btnRegister);
        final EditText full_name = (EditText)findViewById(R.id.full_name);
        final EditText email = (EditText)findViewById(R.id.edtxtEmail);
        final EditText password = (EditText)findViewById(R.id.edtxtCode);

        String []  gender = new String[]{"Select gender","female", "male"};

        // Create an ArrayAdapter using the string array and a default spinner layout

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(Register.this, android.R.layout.simple_spinner_dropdown_item, gender);
        // Specify the layout to use when the list of choices appears

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner

        spngender.setAdapter(adapter2);

        Register.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(full_name.getText().toString() =="" || email.getText().toString() == "" || password.getText().toString() == "" ){
                            Toast.makeText(Register.this, "Fill all the fields", Toast.LENGTH_SHORT).show();
                        }
                        else if(spngender.getSelectedItem().toString() == "Select gender"){

                        }
                        else{
                                 TestInternet TI = new TestInternet();


                        if(TI.checkOnlineState(com.simbaeducation.reportIt.Registration.Register.this) == true){
                            Random rand = new Random();
                            String VerifCode = String.format("%04d", rand.nextInt(10000));


                            boolean isInserted = myDb.insertUser("Users", full_name.getText().toString(),
                                    email.getText().toString(),
                                    password.getText().toString(), 0, 0,spngender.getSelectedItem().toString());

                            boolean tempcodeInsert = myDb.insertTemp_codes("Temp_Codes", VerifCode, email.getText().toString());


                            if (isInserted == true && tempcodeInsert == true) {

                                //email operation

                                new EmailSenderAsync().execute(email.getText().toString(), "Your Verificication code is "+VerifCode+" \n Regards \n Simba Education \n Email was generated by REPORTit Mobile APP","Verification Code");

                                new AlertDialog.Builder(Register.this)
                                        .setTitle("Notice")
                                        .setMessage("Check Email for verification code")
                                        .setNegativeButton(android.R.string.no, null)
                                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface arg0, int arg1) {


                                                Intent intent = new Intent(".CodeCheck");
                                                startActivity(intent);
                                            }
                                        }).create().show();

                            } else {
                                Toast.makeText(Register.this, "Failed to register account", Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            ShowMessage sm = new ShowMessage();
                            sm.showMessage(com.simbaeducation.reportIt.Registration.Register.this
                                    ,"Error","You dont have \n an active \n Internet Connection");
                        }
                        }

                    }
                }
        );
    }
}
