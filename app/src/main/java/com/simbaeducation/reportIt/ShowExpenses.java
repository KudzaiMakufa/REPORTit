package com.simbaeducation.reportIt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowExpenses extends AppCompatActivity {
    Db_Operations myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expenses);
        myDb = new Db_Operations(this);

        ViewExpenseDetails();


    }


    public void ViewExpenseDetails(){
        Cursor res = myDb.getAllData("Expenses");
        if (res.getCount() == 0) {
            // show message
            showMessage("Error", "Nothing found");
            return;
        } else {
            while (res.moveToNext()) {

                ArrayList<String> data0 = new ArrayList<String>();
                ArrayList<String> data = new ArrayList<String>();
                ArrayList<String> data1 = new ArrayList<String>();
                ArrayList<String> data2 = new ArrayList<String>();
                TableLayout table;
                table = (TableLayout) findViewById(R.id.mytable);


                data0.add(res.getString(0));
                data.add(" "+res.getString(1));
                data1.add("  " + res.getString(2));
                data2.add("  " + res.getString(3));



                for (int i = 0; i < data.size(); i++) {
                    TableRow row = new TableRow(this);
                    final String id = data0.get(i);
                    String date = data.get(i);
                    String amount = data1.get(i);
                    String description = data2.get(i);

                    final TextView tv0 = new TextView(this);
                    tv0.setText("        "+id);
                    TextView tv1 = new TextView(this);
                    tv1.setText(date);
                    TextView tv2 = new TextView(this);
                    tv2.setText(amount);
                    TextView tv3 = new TextView(this);
                    tv3.setText(description);

                    Button btn = new Button(this);
                    btn.setText("Delete");
                    btn.setMinHeight(0);
                    btn.setHeight(5);
                    btn.setMinHeight(8);
                    btn.setTextColor(Color.parseColor("#D30B0B"));
                    btn.setBackgroundColor(Color.parseColor("#00000000"));


                    btn.setOnClickListener(
                            new View.OnClickListener() {
                                public void onClick(View v) {
                                    AlertDialog.Builder alertWrong = new AlertDialog.Builder(ShowExpenses.this);

                                    alertWrong.setMessage("delete data?").setCancelable(false)
                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Cursor res = myDb.getAllData("Expenses");
                                                    Integer deletedRows = myDb.deleteData("Expenses" , id);
                                                    if(deletedRows > 0) {

                                                        Toast.makeText(ShowExpenses.this, "Data Deleted", Toast.LENGTH_LONG).show();
                                                        dialog.cancel();

                                                    }
                                                    else {
                                                        Toast.makeText(ShowExpenses.this, "Data not Deleted", Toast.LENGTH_LONG).show();

                                                    }

                                                }
                                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();

                                        }
                                    });
                                    AlertDialog alert = alertWrong.create();
                                    alert.setTitle("Warning");
                                    alert.show();



                                }
                            });
                    row.addView(tv0);
                    row.addView(tv1);
                    row.addView(tv2);
                    row.addView(tv3);
                    row.addView(btn);
                    table.addView(row);
                }


            }


        }
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
