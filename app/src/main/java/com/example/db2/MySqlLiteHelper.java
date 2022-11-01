package com.example.db2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class MySqlLiteHelper extends SQLiteOpenHelper {
    //declare the class's instance variables
    private static final String DATABASE_NAME = "products.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "product";
    private static final String COL_ID ="id" ;
    private static final String COL_NAME = "productName";
    private static final String COL_PRICE = "productPrice";



    public MySqlLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(" +
                COL_ID + " Integer PRIMARY KEY AUTOINCREMENT," +
                COL_NAME + " Text NOT NULL,"+
                COL_PRICE + " number DEFAULT 0)" +";" ;
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table " + TABLE_NAME+ ";");
        this.onCreate(db);

    }

    //add record method
    public void addRecord(Products product){
        ContentValues values = new ContentValues();
        values.put(COL_NAME,product.getProductName());
        values.put(COL_PRICE,product.getProductPrice());


        //SQLiteDatabase db = this.getWritableDatabase();
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            // Insert the new entry into the DB.
            db.insert(TABLE_NAME,null,values);
        } finally {
            db.close();
        }

    }

    //delete record method
    public void  deleteRecord(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete from " + TABLE_NAME + " where " + COL_NAME + "='"  + name +"';");

        db.close();
    }


    //select all records method
    @SuppressLint("Range")
    public  String databaseToString(){

        String result= "";

        SQLiteDatabase db = getReadableDatabase();
        String query ="Select "+ COL_NAME + "  from " + TABLE_NAME +";";
        Cursor c = db.query(TABLE_NAME,new String[]{COL_NAME},null,null,null,null,null);
        c.moveToFirst();

        while( !c.isAfterLast()){
            result += c.getString(c.getColumnIndex(COL_NAME));
            result +="\n";
            Log.d("select","value : "+result);
            c.moveToNext();
        }
        db.close();
        return result;
    }
}

