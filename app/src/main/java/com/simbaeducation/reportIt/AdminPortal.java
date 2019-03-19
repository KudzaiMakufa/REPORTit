package com.simbaeducation.reportIt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class AdminPortal extends AppCompatActivity {
    Db_Operations myDb;
    EditText editText5,editText9,editText12 ;
    EditText myid,myname,myemail,mypassword ;
    Button btnAddUser,btnView ,btndelete,btnUpdate;
    Switch adminswitch ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_portal);
        myDb = new Db_Operations(this);

        myid = (EditText)findViewById(R.id.editText13);
        myname = (EditText)findViewById(R.id.editText5);
        myemail = (EditText)findViewById(R.id.editText9);
        mypassword = (EditText)findViewById(R.id.editText12);
        btnAddUser = (Button) findViewById(R.id.btnAddUser);
        btnView = (Button) findViewById(R.id.btnviewAll);
        btndelete = (Button) findViewById(R.id.btnDelete);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        //myDb.onUpgrade(,1,2);
        AddData();
        viewAll();
        UpdateData();
        DeleteData();
    }
    public  void AddData() {
        adminswitch = (Switch)findViewById(R.id.switchAdmin) ;
        btnAddUser.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int usertype;
                        if(adminswitch.isChecked()){
                            usertype = 1 ;
                        }
                        else{
                            usertype = 0 ;
                        }

                        boolean isInserted = myDb.insertUser("Users",myname.getText().toString(),
                                myemail.getText().toString(),
                                mypassword.getText().toString() , usertype,1,"male");
                        if(isInserted == true)
                            Toast.makeText(AdminPortal.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(AdminPortal.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void viewAll() {
        btnView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData("Users");
                        if(res.getCount() == 0) {
                            // show message
                            showMessage("Error","Nothing found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Id :"+ res.getString(0)+"\n");
                            buffer.append("Name :"+ res.getString(1)+"\n");
                            buffer.append("Email :"+ res.getString(2)+"\n");
                            buffer.append("Password :"+ res.getString(3)+"\n");
                            //buffer.append("Password : ********** \n");
                            buffer.append("UserType :"+ res.getString(4)+"\n\n");
                        }

                        // Show all data
                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }

    public void DeleteData() {
        btndelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData("Users" , "3");
                        if(deletedRows > 0)
                            Toast.makeText(AdminPortal.this,"Data Deleted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(AdminPortal.this,"Data not Deleted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public void UpdateData() {
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateData(myid.getText().toString(),
                                myname.getText().toString(),
                                myemail.getText().toString(),mypassword.getText().toString());
                        if(isUpdate == true)
                            Toast.makeText(AdminPortal.this,"Data Update",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(AdminPortal.this,"Data not Updated",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Main Menu")
                .setMessage("Go to Main Menu ?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        AdminPortal.super.onBackPressed();
                    }
                }).create().show();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

}
