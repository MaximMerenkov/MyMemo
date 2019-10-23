package com.example.mymemo;

import java.util.Calendar;

public class Memo {
    private int memoId;
    private String memoTitle;
    private String memoInfo;
    private String priority;
    private Calendar memoDate;

    public Memo(){
        memoId = -1;
        memoDate = Calendar.getInstance();

    }

    public Calendar getMemoDate() {
        return memoDate;
    }
    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setMemoDate(Calendar memoDate) {
        this.memoDate = memoDate;
    }



    public String getMemoInfo() {
        return memoInfo;
    }

    public void setMemoInfo(String memoInfo) {
        this.memoInfo = memoInfo;
    }

    public int getMemoId() {
        return memoId;
    }

    public void setMemoId(int memoId) {
        this.memoId = memoId;
    }
    public String getMemoTitle() {
        return memoTitle;
    }

    public void setMemoTitle(String memoTitle) {
        this.memoTitle = memoTitle;
    }








}
