package com.example.mymemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MemoSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_settings);
        initAddMemoButton();
        initListButton();
        initSettingsButton();
        initSortByClick();
        initSortOrderClick();
        initSettings();
    }

    private void initSettingsButton() {
        ImageButton ibSettings = (ImageButton) findViewById(R.id.imageButtonSettings);
        ibSettings.setEnabled(false);

    }
    private void initAddMemoButton() {
        ImageButton ibAddMemo = (ImageButton) findViewById(R.id.imageButtonAdd);
        ibAddMemo.setOnClickListener((view) -> {

            Intent intent = new Intent(MemoSettings.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        });

    }
    private void initListButton() {
        ImageButton ibList = (ImageButton) findViewById(R.id.imageButtonList);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MemoSettings.this, MemoList.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void initSettings(){
        String sortBy =getSharedPreferences("MyMemoPreferences",
                Context.MODE_PRIVATE).getString("sortfield", "high");
        String sortOrder = getSharedPreferences("MyMemoPreferences",
                Context.MODE_PRIVATE).getString("sortorder","selected" );
        RadioButton rbHigh = (RadioButton)findViewById(R.id.radioHigh);
        RadioButton rbMedium = (RadioButton)findViewById(R.id.radioMedium);
        RadioButton rbLow = (RadioButton)findViewById(R.id.radioLow);
        if(sortBy.equalsIgnoreCase("high")){
            rbHigh.setChecked(true);
        }
        else if(sortBy.equalsIgnoreCase("medium")){
            rbMedium.setChecked(true);
        }
        else{
            rbLow.setChecked(true);
        }
        CheckBox cbDate = (CheckBox)findViewById(R.id.checkBoxDate);


        if(sortOrder.equalsIgnoreCase("selected")){
            cbDate.setChecked(true);
        }
        else{
            cbDate.setChecked(false);
        }




    }
    private void initSortByClick(){
        RadioGroup rgSortBy = (RadioGroup) findViewById(R.id.radioGroupSortBy);
        rgSortBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                RadioButton rbHigh = (RadioButton) findViewById(R.id.radioHigh);
                RadioButton rbMedium= (RadioButton) findViewById(R.id.radioMedium);
                if(rbHigh.isChecked()){
                    getSharedPreferences("MyMemoPreferences",
                            Context.MODE_PRIVATE).edit().putString("sortfield","high").commit();
                }
                else if(rbMedium.isChecked()){
                    getSharedPreferences("MyMemoPreferences",
                            Context.MODE_PRIVATE).edit().putString("sortfield","medium").commit();
                }
                else{
                    getSharedPreferences("MyMemoPreferences",
                            Context.MODE_PRIVATE).edit().putString("sortfield", "low").commit();
                }

            }
        });


    }
    private void initSortOrderClick(){
        RadioGroup rgSortOrder = (RadioGroup)findViewById(R.id.radioGroupSortDate);
        rgSortOrder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                CheckBox cbDate = (CheckBox)findViewById(R.id.checkBoxDate);
                if(cbDate.isChecked()){
                    getSharedPreferences("MyMemoPreferences",
                            Context.MODE_PRIVATE).edit().putString("sortorder", "selected").commit();
                }
                else{
                    getSharedPreferences("MyMemoPreferences",
                            Context.MODE_PRIVATE).edit().putString("sortorder", "date").commit();
                }

            }
        });

    }
    //com
}
