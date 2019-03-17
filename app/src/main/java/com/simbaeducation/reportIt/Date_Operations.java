package com.simbaeducation.reportIt;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Date_Operations {


    public void appDate(final EditText dateassignarea , Context theclass){

        DatePickerDialog.OnDateSetListener dpd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                int s=monthOfYear+1;
                String a = dayOfMonth+"/"+s+"/"+year;
                dateassignarea.setText(""+a);
            }
        };

        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("UTC"));
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog =
                new DatePickerDialog(theclass , dpd, mYear, mMonth, mDay);
        dialog.show();
    }

    public String GetCurrentTimeAndDate(){


       String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
       return currentDateTimeString;

    }
}
