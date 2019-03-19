package com.simbaeducation.reportIt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ProgrammingKnowledge on 4/3/2015.
 */
public class Db_Operations extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "SimbaEducation.db";
    public static final String TABLE_NAME = "Users";
    public static final String TABLE_NAME2 = "Expenses";
    public static final String TABLE_NAME3 = "Subscriptions";
    public static final String TABLE_NAME4 = "Leave";
    public static final String TABLE_NAME5 = "Temp_Codes";
    public static final String TABLE_NAME6 = "hodmail";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "EMAIL";
    public static final String COL_4 = "PASSWORD";
    public static final String COL_5 = "USER_TYPE";
    public static final String COL_6 = "ISACTIVATED";
    public static final String COL_7 = "GENDER";



    public Db_Operations(Context context) {
        super(context, DATABASE_NAME, null, 7);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,EMAIL TEXT,PASSWORD TEXT,USER_TYPE TEXT ,ISACTIVATED TEXT,GENDER TEXT)");
        db.execSQL("create table " + TABLE_NAME2 +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,DATE TEXT,AMOUNT INTEGER,DESCRIPTION TEXT)");
        db.execSQL("create table " + TABLE_NAME3 +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,DATE TEXT,FULL_NAME TEXT,DISTRICT TEXT,AMOUNT INTEGER,PURPOSE TEXT,PAYMODE TEXT,REFERENCE TEXT)");
        db.execSQL("create table " + TABLE_NAME4 +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,FULL_NAME TEXT,LEAVE_TYPE TEXT,STARTDATE TEXT,ENDDATE TEXT,DAY_TYPE TEXT,NOTES TEXT)");
        db.execSQL("create table " + TABLE_NAME5 +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,CODE TEXT,EMAIL TEXT)");
        db.execSQL("create table " + TABLE_NAME6 +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,EMAIL TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME3);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME4);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME5);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME6);
        onCreate(db);
    }

    public boolean insertUser(String table_name, String name, String email, String password, int usertype , int isactivated,String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,email);
        contentValues.put(COL_4,password);
        contentValues.put(COL_5,usertype);
        contentValues.put(COL_6,isactivated);
        contentValues.put(COL_7,gender);
        long result = db.insert(table_name,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    public boolean insertHod(String table_name, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL",email);
        long result = db.insert(table_name,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    public boolean ActivateUser(String table_name,int isactivated ,String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_6,isactivated);
        db.update(table_name, contentValues,COL_3 + "= ?",new String[] { email });
        return true;
    }
    public boolean insertLeave(String table_name ,String fullname,String leave_type,String startdate, String enddate ,String daytype ,String notes) {
        //_________________//________________//
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FULL_NAME",fullname);
        contentValues.put("LEAVE_TYPE",leave_type);
        contentValues.put("STARTDATE",startdate);
        contentValues.put("ENDDATE",enddate);
        contentValues.put("DAY_TYPE",daytype);
        contentValues.put("NOTES",notes);
        long result = db.insert(table_name,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    public Cursor gettempCodeEmali(String table_name , String code ) {


        SQLiteDatabase db = this.getWritableDatabase();
        String codei = "CODE";
        int isactivated = 1 ;
        Cursor res = db.rawQuery("SELECT EMAIL FROM " + table_name + " WHERE " +codei + " = '" + code+"'" ,null);

        return res;

    }

    public boolean insertTemp_codes(String table_name ,String tempcode,String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("CODE",tempcode);
        contentValues.put("EMAIL",email);
        long result = db.insert(table_name,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    public Cursor VerifyCode(String table_name ,String code) {


        SQLiteDatabase db = this.getWritableDatabase();
        int isactivated = 1 ;
        String CODE = "CODE";
        Cursor res = db.rawQuery("SELECT * FROM " + table_name + " WHERE " +CODE + " = '" + code+"'" ,null);

        return res;

    }

    public boolean insertSubscription(String table_name ,String date,String full_name,String district,int amount,String purpose,String paymode, String reference) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("DATE",date);
        contentValues.put("FULL_NAME",full_name);
        contentValues.put("DISTRICT",district);
        contentValues.put("AMOUNT",amount);
        contentValues.put("PURPOSE",purpose);
        contentValues.put("PAYMODE",paymode);
        contentValues.put("REFERENCE",reference);
        long result = db.insert(table_name,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }


    public Cursor getAllData(String table_name ) {
        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor res = db.rawQuery("select * from "+table_name,null);

       Cursor res = db.rawQuery("SELECT * FROM " + table_name  ,null);

        return res;
    }


    public Cursor GetFromTodata(String table_name , String from ,String to) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM " + table_name + " WHERE DATE  BETWEEN "+from+" AND "+to+" " ,null);
        return res;
    }

    public Cursor getLoginData(String table_name ,String email ,String password) {


        SQLiteDatabase db = this.getWritableDatabase();
        int isactivated = 1 ;
        Cursor res = db.rawQuery("SELECT * FROM " + table_name + " WHERE " +COL_3 + " = '" + email+"' AND "+COL_4+ "= '"+password+"' AND "+COL_6+ "= '"+isactivated+"'" ,null);

        return res;

    }
    public Cursor GetSumOfColumns(String table_name ,String Column) {


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT SUM(amount) AS TOTAL  FROM " + table_name,null);
        return res;
    }
    public Cursor IsAdmin(String table_name ,String email ,int usertype) {


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + table_name + " WHERE " +COL_3 + " = '" + email+"' AND "+COL_5+ "= '"+usertype+"'" ,null);
        return res;
    }

    public Cursor GetPayModeTotals(String table_name ,String Column ,String paymode ) {


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT SUM(amount) AS TOTAL FROM " + table_name + " WHERE " +Column + " = '" + paymode+"'" ,null);
        return res;
    }

    public boolean updateData(String id,String name,String email,String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,email);
        contentValues.put(COL_4,password);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }

    public Integer deleteData (String table_name , String id) {
        SQLiteDatabase db = this.getWritableDatabase();
       // return db.delete(table_name, "ID = ?",new String[] {id});
        return db.delete(table_name, "ID = ?",new String[] {id});
    }
    public Integer deleteHod (String table_name ,String email ) {
        SQLiteDatabase db = this.getWritableDatabase();
         return db.delete(table_name, "EMAIL = ?",new String[] {email});
        //return db.delete(table_name, null,null);
    }
}