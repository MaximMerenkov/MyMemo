package com.example.mymemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MemoList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_list);
        initAddMemoButton();
        initListButton();
        initSettingsButton();
    }

    private void initListButton() {
        ImageButton ibList = (ImageButton) findViewById(R.id.imageButtonList);
        ibList.setEnabled(false);
    }
    private void initSettingsButton() {
        ImageButton ibSettings = (ImageButton) findViewById(R.id.imageButtonSettings);
        ibSettings.setOnClickListener((view) -> {

            Intent intent = new Intent(MemoList.this, MemoSettings.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        });

    }
    private void initAddMemoButton() {
        ImageButton ibAddMemo = (ImageButton) findViewById(R.id.imageButtonAdd);
        ibAddMemo.setOnClickListener((view) -> {

            Intent intent = new Intent(MemoList.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        });

    }

}
