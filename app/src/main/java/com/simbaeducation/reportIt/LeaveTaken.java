package com.simbaeducation.reportIt;

import android.database.Cursor;

public class LeaveTaken {
    private String type, date, notes, daytype;
    int Id;

    public LeaveTaken(){

    }

    public LeaveTaken(int Id, String type, String date, String notes, String amount){
        this.Id = Id;
        this.type = type;
        this.date = date;
        this.notes = notes;
        this.daytype = amount;
    }

    public LeaveTaken(Cursor cursor) {


        this.Id =  Integer.valueOf(cursor.getString(0));
        this.type = cursor.getString(2);
        this.date = cursor.getString(3)+"    to  "+cursor.getString(4);
        this.notes = cursor.getString(6);
        this.daytype = cursor.getString(5);

    }

    public int getId(){
        return Id;
    }

    public String getType(){
        return type;
    }

    public String getDate(){
        return date;
    }

    public String getNotes(){
        return notes;
    }

    public String getDaytype(){
        return daytype;
    }


}
