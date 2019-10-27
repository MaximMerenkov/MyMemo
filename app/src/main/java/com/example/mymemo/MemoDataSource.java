package com.example.mymemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;


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
    public boolean deleteMemo(int memoId){
        boolean didDelete = false;
        try{
            didDelete =database.delete("memo","_id=" + memoId,null)>0;
        }
        catch(Exception e){
            //do nothing
        }
        return didDelete;

    }
    public ArrayList<Memo>getMemos(String sortOrder){
        ArrayList<Memo> memos = new ArrayList<Memo>();
        try {
            String query = "SELECT * FROM memo ORDER BY " + sortOrder;
            Log.wtf("tag1",sortOrder);

            Cursor cursor = database.rawQuery(query,null);

            Memo newMemo;
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                newMemo = new Memo();
                newMemo.setMemoId(cursor.getInt(0));
                newMemo.setMemoTitle(cursor.getString(1));
                newMemo.setMemoInfo(cursor.getString(2));
                newMemo.setPriority(cursor.getString(3));
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(Long.valueOf(cursor.getString(4)));
                newMemo.setMemoDate(calendar);
                memos.add(newMemo);
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch(Exception e){
            memos = new ArrayList<Memo>();
        }
        return memos;

    }
    public int getLastMemoId(){
        int lastId = -1;
        try{
            String query = "Select MAX(_id) from memo";
            Cursor cursor = database.rawQuery(query,null);

            cursor.moveToFirst();
            lastId= cursor.getInt(0);
            cursor.close();


        }
        catch(Exception e){
            lastId = -1;

        }
        return lastId;

    }
    public ArrayList<String> getMemoTitle(){
        ArrayList<String>memoTitle = new ArrayList<>();
        try{
            String query = "Select memoTitle from memo";
            Cursor cursor = database.rawQuery(query,null);
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                memoTitle.add(cursor.getString(0));
                cursor.moveToNext();
            }
            cursor.close();

        }
        catch (Exception e){
            memoTitle = new ArrayList<String>();
        }
        return memoTitle;

    }
    public Memo getSpecificMemo(int memoId){
        Memo memo = new Memo();
        String query = "SELECT* FROM memo WHERE _id =" +memoId;
        Cursor cursor = database.rawQuery(query,null);

        if(cursor.moveToFirst()){
            memo.setMemoId(cursor.getInt(0));
            memo.setMemoTitle(cursor.getString(1));
            memo.setMemoInfo(cursor.getString(2));
            memo.setPriority(cursor.getString(3));
            Calendar calendar=Calendar.getInstance();
            calendar.setTimeInMillis(Long.valueOf(cursor.getString(4)));
            memo.setMemoDate(calendar);

            cursor.close();
        }
        return memo;
    }
    public ArrayList<Memo> getMemosByHighPriority() {
        ArrayList<Memo> memos = new ArrayList<Memo>();
        try {
            String query = "SELECT  * FROM memo ORDER BY CASE priority" +
                    "    WHEN 'High' THEN 1\n" +
                    "    WHEN 'Medium' THEN 2\n" +
                    "    WHEN 'Low' THEN 3\n" +
                    "    END ";


            Cursor cursor = database.rawQuery(query, null);
            Memo newMemo;
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                newMemo = new Memo();
                newMemo.setMemoId(cursor.getInt(0));
                newMemo.setMemoTitle(cursor.getString(1));
                newMemo.setMemoInfo(cursor.getString(2));
                newMemo.setPriority(cursor.getString(3));
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(Long.valueOf(cursor.getString(4)));
                newMemo.setMemoDate(calendar);
                memos.add(newMemo);
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch(Exception e){
            memos = new ArrayList<Memo>();
        }
        return memos;

    }
    public ArrayList<Memo> getMemosByMediumPriority() {
        ArrayList<Memo> memos = new ArrayList<Memo>();
        try {
            String query = "SELECT  * FROM memo ORDER BY CASE priority" +
                    "    WHEN 'Medium' THEN 1\n" +
                    "    WHEN 'Low' THEN 2\n" +
                    "    WHEN 'High' THEN 3\n" +
                    "    END ";

            Cursor cursor = database.rawQuery(query, null);
            Memo newMemo;
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                newMemo = new Memo();
                newMemo.setMemoId(cursor.getInt(0));
                newMemo.setMemoTitle(cursor.getString(1));
                newMemo.setMemoInfo(cursor.getString(2));
                newMemo.setPriority(cursor.getString(3));
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(Long.valueOf(cursor.getString(4)));
                newMemo.setMemoDate(calendar);
                memos.add(newMemo);
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch(Exception e){
            memos = new ArrayList<Memo>();
        }
        return memos;

    }
    public ArrayList<Memo> getMemosByLowPriority() {
        ArrayList<Memo> memos = new ArrayList<Memo>();
        try {
            String query = "SELECT  * FROM memo ORDER BY CASE priority " +
                    "    WHEN 'Low' THEN 1\n" +
                    "    WHEN 'Medium' THEN 2\n" +
                    "    WHEN 'High' THEN 3\n" +
                    "    END ";

            Cursor cursor = database.rawQuery(query, null);
            Memo newMemo;
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                newMemo = new Memo();
                newMemo.setMemoId(cursor.getInt(0));
                newMemo.setMemoTitle(cursor.getString(1));
                newMemo.setMemoInfo(cursor.getString(2));
                newMemo.setPriority(cursor.getString(3));
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(Long.valueOf(cursor.getString(4)));
                newMemo.setMemoDate(calendar);
                memos.add(newMemo);
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch(Exception e){
            memos = new ArrayList<Memo>();
        }
        return memos;

    }

}
