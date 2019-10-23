package com.example.mymemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;


public class MemoDataSource {
    private SQLiteDatabase database;
    private MemoDBHelper dbHelper;

    public MemoDataSource(Context context){
        dbHelper = new MemoDBHelper(context);

    }
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    public void close(){
        dbHelper.close();
    }
    public boolean insertMemo(Memo c){
        boolean didSucceed = false;
        try{

            ContentValues initialValues = new ContentValues();
            initialValues.put("memoTitle", c.getMemoTitle());
            initialValues.put("memoInfo", c.getMemoInfo());
            initialValues.put("priority", c.getPriority());
            initialValues.put("memoDate", String.valueOf(c.getMemoDate().getTimeInMillis()));

            didSucceed = database.insert("memo", null, initialValues)>0;


        }
        catch(Exception e){


        }
        return didSucceed;

    }
    public boolean updateMemo(Memo c){
        boolean didSucceed = false;
        try{
            Long rowId =(long) c.getMemoId();
            ContentValues updateValues = new ContentValues();

            updateValues.put("memoTitle", c.getMemoTitle());
            updateValues.put("memoInfo", c.getMemoInfo());
            updateValues.put("priority", c.getPriority());
            updateValues.put("memoDate", String.valueOf(c.getMemoDate().getTimeInMillis()));

            didSucceed = database.update("memo",updateValues, "_id=" + rowId, null)>0;


        }
        catch(Exception e){


        }
        return didSucceed;

    }
}
