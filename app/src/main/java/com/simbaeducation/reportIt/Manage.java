package com.simbaeducation.reportIt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Integer.parseInt;

public class Manage extends AppCompatActivity {
    Db_Operations myDb;
    Spinner spnType,spnTyped ;
    Button CreatePdf,btnView,btnsettle;
    EditText datefrom,dateto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);


        ManageMent();

    }


    public void ManageMent(){
        myDb = new Db_Operations(this);
        spnType = (Spinner)findViewById(R.id.spinnerType);
        final TextView txtExpense = (TextView)findViewById(R.id.txtViewExp);
        final TextView txtSubscriptions = (TextView)findViewById(R.id.txtSub);
        final TextView txtIncome = (TextView)findViewById(R.id.txtNetIncome);
        final TextView txtcashres = (TextView)findViewById(R.id.txtCash);
        final TextView txtEcores = (TextView)findViewById(R.id.txtEcocash);
        final TextView txtBankres = (TextView)findViewById(R.id.txtBank);
        datefrom = (EditText)findViewById(R.id.edtxtdatefrom);
        dateto = (EditText)findViewById(R.id.edtxtdateto);
        btnView = (Button)findViewById(R.id.btnviewdate);
        btnsettle = (Button)findViewById(R.id.btnsettle);




        String []  dropdowntype = new String[]{"None","Settle All","Settle by Date", "Subscriptions","Reconcile All","Reconcile by Date"};

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, dropdowntype);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spnType.setAdapter(adapter);



        spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String Spinnergettype = ((Spinner)findViewById(R.id.spinnerType)).getSelectedItem().toString();

                switch (position) {

                    case 1:
                        if(Spinnergettype == "Settle All" ){

                            Integer settleExpenses = myDb.SettleAccount("Expenses");
                            Integer settleSubscriptions = myDb.SettleAccount("Subscriptions");


                            if(settleExpenses > 0 && settleSubscriptions <= 0 ){
                                Toast.makeText(Manage.this,"Expenses Settled",Toast.LENGTH_LONG).show();
                            }
                            else if(settleExpenses <= 0 && settleSubscriptions > 0 ){
                                Toast.makeText(Manage.this,"Subscriptions Settled",Toast.LENGTH_LONG).show();
                            }
                            else if(settleExpenses <= 0 && settleSubscriptions <= 0 ){
                                Toast.makeText(Manage.this,"No data to delete",Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(Manage.this,"Accounts settled",Toast.LENGTH_LONG).show();
                            }


                            }


                        break;
                    case 2:
                        if(Spinnergettype == "Settle by Date"){
                            btnView = (Button)findViewById(R.id.btnviewdate);
                            EditText datefrom = (EditText)findViewById(R.id.edtxtdatefrom);
                            EditText dateto = (EditText)findViewById(R.id.edtxtdateto);
                            datefrom.setText(" enter date ");
                            dateto.setText(" enter date ");
                            btnView.setText("Settle by date");

                        }
                        break;
                    case 3:
                        if(Spinnergettype == "Subscriptions") {

                        Cursor res = myDb.GetSumOfColumns("Subscriptions","amount");

                        if (res.moveToFirst()){

                            String alldata = res.getString(0);
                            if(alldata == "0"){
                                txtSubscriptions.setText("0.00");

                                txtExpense.setText("0.00");
                                txtIncome.setText("0.00");
                            }
                            else {
                                txtSubscriptions.setText(alldata);
                                txtExpense.setText("0.00");
                                txtIncome.setText("0.00");

                            }




                        }

                    }
                        break;
                    case 4:
                        if(Spinnergettype == "Reconcile All"){

                            Cursor res = myDb.GetSumOfColumns("Subscriptions","amount");
                            Cursor result = myDb.GetSumOfColumns("Expenses","amount");
                            Cursor cashResult = myDb.GetPayModeTotals("Subscriptions","paymode","Cash");
                            Cursor EcocashResult = myDb.GetPayModeTotals("Subscriptions","paymode","EcoCash");
                            Cursor BankcashResult = myDb.GetPayModeTotals("Subscriptions","paymode","Bank");

                            if (res.moveToFirst() && result.moveToFirst() && cashResult.moveToFirst() && EcocashResult.moveToFirst()
                                    && BankcashResult.moveToFirst()){

                                String alldata = res.getString(0);
                                String allexpe = result.getString(0);
                                String cashres = cashResult.getString(0);
                                String ecores = EcocashResult.getString(0);
                                String bankres = BankcashResult.getString(0);

                                int income = 0;
                                try{

                                 income = parseInt(alldata) - parseInt(allexpe);
                                }
                                catch(Exception e){
                                  income = 0;
                                }

                                txtIncome.setText(Integer.toString(income));
                                txtExpense.setText("("+allexpe+")");
                                txtSubscriptions.setText(alldata);
                                txtcashres.setText(cashres);
                                txtEcores.setText(ecores);
                                txtBankres.setText(bankres);




                            }
                        }
                        break;
                    case 5:
                        if(Spinnergettype == "Reconcile by Date"){
                            btnView = (Button)findViewById(R.id.btnviewdate);


                            datefrom.setText(" enter date ");
                            dateto.setText(" enter date ");
                            btnView.setText("Reconcile by date");

                        }
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });

            /*
            *
            *
            *
            *
            *
            * here are the defined onclick actions
            *
            *
            *
            *
            *
            * */
        btnsettle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(".initialSystemView");
                startActivity(intent);
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String from =  datefrom.getText().toString();
                String to =  dateto.getText().toString();
                Cursor res = myDb.GetFromTodata("Subscriptions",from,to);
                Cursor result = myDb.GetFromTodata("Expenses",datefrom.getText().toString(),dateto.getText().toString());
                Cursor cashResult = myDb.GetPayModeTotals("Subscriptions","paymode","Cash");
                Cursor EcocashResult = myDb.GetPayModeTotals("Subscriptions","paymode","EcoCash");
                Cursor BankcashResult = myDb.GetPayModeTotals("Subscriptions","paymode","Bank");

                if (res.moveToFirst() && result.moveToFirst() && cashResult.moveToFirst() && EcocashResult.moveToFirst()
                        && BankcashResult.moveToFirst()){

                    String alldata = res.getString(0);
                    String allexpe = result.getString(0);
                    String cashres = cashResult.getString(0);
                    String ecores = EcocashResult.getString(0);
                    String bankres = BankcashResult.getString(0);

                    int income = 0;
                    try{

                        income = parseInt(alldata) - parseInt(allexpe);

                    }
                    catch(Exception e){
                        income = 0;
                    }

                    txtIncome.setText(Integer.toString(income));
                    txtExpense.setText("("+allexpe+")");
                    txtSubscriptions.setText(alldata);
                    txtcashres.setText(cashres);
                    txtEcores.setText(ecores);
                    txtBankres.setText(bankres);




                }
            }
        });


        Button btnCalculate = (Button)findViewById(R.id.btnClac);
        btnCalculate.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Spinnergettype = ((Spinner)findViewById(R.id.spinnerType)).getSelectedItem().toString();
                        if(Spinnergettype == "None"){


                            AlertDialog.Builder alertWrong = new AlertDialog.Builder(Manage.this);

                            alertWrong.setMessage("Pick Field by TYpe").setCancelable(false)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                        }
                                    });
                            AlertDialog alert = alertWrong.create();
                            alert.setTitle("Warning");
                            alert.show();

                            txtIncome.setText("0.00");
                            txtExpense.setText("0.00");
                            txtSubscriptions.setText("0.00");


                        }

                    }
                }
        );

        CreatePdf  = (Button)findViewById(R.id.btnPrintPdf);
        CreatePdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(".Pdf_Operations");

                startActivity(intent);
            }
        });
        //Onclick listener for button view on total subscriptions

        Button ViewSubs = (Button)findViewById(R.id.btnviewsub);
        ViewSubs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(".ShowSubscriptions");
                startActivity(intent);
            }
        });
            //Onclick listener for button view on total expenses

        Button ViewExp = (Button)findViewById(R.id.btnviewexp);
        ViewExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(".ShowExpenses");
                startActivity(intent);
            }
        });

            //from date onclick listener

        datefrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datefrom.setText("");

                Date_Operations dateop = new Date_Operations();
                dateop.appDate(datefrom , Manage.this);
            }
        });

        //to date onclick listener

        dateto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateto.setText("");

                Date_Operations dateop = new Date_Operations();
                dateop.appDate(dateto , Manage.this);

            }
        });

    }


    public String sum(){

        Cursor res = myDb.GetSumOfColumns("Expenses","amount");
        res.getColumnIndex("amount");
        String result = "";

        int index_SUM = res.getColumnIndex("SUM(amount)");
        for (res.moveToFirst(); !(res.isAfterLast()); res.moveToNext()) {
            result = result + res.getString(index_SUM)  + "\n";
        }
        return result;
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
