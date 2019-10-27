package com.example.mymemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MemoList extends AppCompatActivity {
    ArrayList<Memo> memos;
    boolean isDeleting = false;
    MemoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_list);
        initAddMemoButton();
        initListButton();
        initSettingsButton();
        initItemClick();
        initDeleteButton();

        MemoDataSource ds = new MemoDataSource(this);
        String sortBy = getSharedPreferences("MyMemoPreferences",
                Context.MODE_PRIVATE).getString("sortorder","memoDate" );
        String sortPriority =getSharedPreferences("MyMemoPreferences",
                Context.MODE_PRIVATE).getString("sortfield", "priority");
        if(sortBy.equalsIgnoreCase("memodate")) {
            try {
                ds.open();
                memos = ds.getMemos(sortBy);

                ds.close();
                ListView listView = (ListView) findViewById(R.id.lvMemo);
                adapter = new MemoAdapter(this, memos);
                listView.setAdapter(adapter);
            } catch (Exception e) {
                Toast.makeText(this, "Error retrieving memos", Toast.LENGTH_LONG).show();
            }
        }
        else{
            if(sortPriority.equalsIgnoreCase("high")){


                try {
                    ds.open();
                    memos = ds.getMemosByHighPriority();

                    ds.close();
                    ListView listView = (ListView) findViewById(R.id.lvMemo);
                    adapter = new MemoAdapter(this, memos);
                    listView.setAdapter(adapter);
                } catch (Exception e) {
                    Toast.makeText(this, "Error retrieving memos", Toast.LENGTH_LONG).show();
                }

            }
            else if(sortPriority.equalsIgnoreCase("medium")){
                try {
                    ds.open();
                    memos = ds.getMemosByMediumPriority();

                    ds.close();
                    ListView listView = (ListView) findViewById(R.id.lvMemo);
                    adapter = new MemoAdapter(this, memos);
                    listView.setAdapter(adapter);
                } catch (Exception e) {
                    Toast.makeText(this, "Error retrieving memos", Toast.LENGTH_LONG).show();
                }
            }
            else{
                try {
                    ds.open();
                    memos = ds.getMemosByLowPriority();

                    ds.close();
                    ListView listView = (ListView) findViewById(R.id.lvMemo);
                    adapter = new MemoAdapter(this, memos);
                    listView.setAdapter(adapter);
                } catch (Exception e) {
                    Toast.makeText(this, "Error retrieving memos", Toast.LENGTH_LONG).show();
                }
            }
        }

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
    private void initItemClick(){
        ListView listView = (ListView)findViewById(R.id.lvMemo);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                Memo selectedMemo = memos.get(position);
                if (isDeleting) {
                    adapter.showDelete(position, itemClicked, MemoList.this, selectedMemo);
                } else {
                    Intent intent = new Intent(MemoList.this, MainActivity.class);
                    intent.putExtra("memoid", selectedMemo.getMemoId());
                    intent.putExtra("memotitle", selectedMemo.getMemoTitle());
                    intent.putExtra("memoinfo", selectedMemo.getMemoInfo());
                    intent.putExtra("memopriority", selectedMemo.getPriority());
                    intent.putExtra("memodate", selectedMemo.getMemoDate());
                    startActivity(intent);
                }
            }


        });
    }
    public void initDeleteButton() {
        final Button deleteButton = (Button) findViewById(R.id.buttonDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (isDeleting) {
                    deleteButton.setText("Delete");
                    isDeleting = false;
                    adapter.notifyDataSetChanged();
                } else {
                    deleteButton.setText("Done Deleting");
                    isDeleting = true;
                }
            }
        });
    }

}
