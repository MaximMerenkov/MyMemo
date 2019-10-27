package com.example.mymemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements com.example.mymemo.DatePickerDialog.SaveDateListener {

    private Memo currentMemo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initListButton();
        initAddMemoButton();
        initSettingsButton();
        initChangeButton();
        initTextChangedEvents();
        initToggleButton();
        initSaveButton();
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            initMemo(extras.getInt("memoid"));
        }
        else{
            currentMemo = new Memo();
        }
        setForEditing(false);


    }

    private void initListButton() {
        ImageButton ibList = (ImageButton) findViewById(R.id.imageButtonList);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MemoList.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void initAddMemoButton() {
        ImageButton ibAddMemo = (ImageButton) findViewById(R.id.imageButtonAdd);
        ibAddMemo.setEnabled(false);
    }

    private void initSettingsButton() {
        ImageButton ibSettings = (ImageButton) findViewById(R.id.imageButtonSettings);
        ibSettings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MemoSettings.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    public void didFinishDatePickerDialog(Calendar selectedTime) {
        TextView memoDay = (TextView) findViewById(R.id.textMemoDate);
        memoDay.setText(DateFormat.format("MM/dd/yyyy", selectedTime.getTimeInMillis()).toString());
        currentMemo.setMemoDate(selectedTime);
    }
    private void initChangeButton(){
        Button btChange = (Button)findViewById(R.id.buttonChangeDate);
        btChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager m = getSupportFragmentManager();
                DatePickerDialog datePickerDialog = new DatePickerDialog();
                datePickerDialog.show(m,"DatePick");



            }
        });
    }
    private void initTextChangedEvents(){
        final EditText etMemoTitle = (EditText) findViewById(R.id.editMemoTitle);
        etMemoTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                currentMemo.setMemoTitle(etMemoTitle.getText().toString());

            }
        });
        final EditText etMemoInfo = (EditText) findViewById(R.id.memoMessage);
        etMemoInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                currentMemo.setMemoInfo(etMemoInfo.getText().toString());


            }
        });




      final RadioGroup rgPriority = (RadioGroup)findViewById(R.id.radioGroup);
        rgPriority.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                RadioButton rbHigh =(RadioButton)findViewById(R.id.high);
                RadioButton rbMedium =(RadioButton)findViewById(R.id.medium);
                RadioButton rbLow =(RadioButton)findViewById(R.id.low);
                if(rbHigh.isChecked()){
                    currentMemo.setPriority(rbHigh.getText().toString());
                    Toast.makeText(MainActivity.this, "High checked", Toast.LENGTH_SHORT).show();
                }
                else if(rbMedium.isChecked()){
                    currentMemo.setPriority(rbMedium.getText().toString());
                }
                else{
                    currentMemo.setPriority(rbLow.getText().toString());
                }


            }
        });




    }

    private void initToggleButton(){
        final ToggleButton editToggle = (ToggleButton) findViewById(R.id.toggleButtonEdit);
        editToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                setForEditing(editToggle.isChecked());
            }
        });

    }
    private void setForEditing(boolean enabled){
        EditText etTitle = (EditText)findViewById(R.id.editMemoTitle);
        EditText etMemoInfo = (EditText)findViewById(R.id.memoMessage);
        Button btSave = (Button)findViewById(R.id.buttonSave);
        Button btChange = (Button)findViewById(R.id.buttonChangeDate);

        etTitle.setEnabled(enabled);
        etMemoInfo.setEnabled(enabled);
        btSave.setEnabled(enabled);
        btChange.setEnabled(enabled);

        if(enabled){
            etMemoInfo.requestFocus();
        }
    }
    private void initSaveButton(){
        Button saveButton = (Button)findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean wasSuccessful = false;
                MemoDataSource ds = new MemoDataSource(MainActivity.this);
                try{
                    ds.open();
                    if(currentMemo.getMemoId()== -1){
                        wasSuccessful = ds.insertMemo(currentMemo);
                        if(wasSuccessful){
                            int newId = ds.getLastMemoId();
                            currentMemo.setMemoId(newId);
                        }

                    }
                    else {
                        wasSuccessful = ds.updateMemo(currentMemo);
                    }
                }
                catch(Exception e){


                    wasSuccessful = false;

                }
                if(wasSuccessful){
                    ToggleButton editToggle = (ToggleButton)findViewById(R.id.toggleButtonEdit);
                    editToggle.toggle();
                    setForEditing(false);
                }


            }
        });
    }
    private void initMemo(int id){
        MemoDataSource ds = new MemoDataSource(MainActivity.this);
        try{
            ds.open();
            currentMemo = ds.getSpecificMemo(id);
            ds.close();
        }
        catch (Exception e){
            Toast.makeText(this, "Failed loading memo",Toast.LENGTH_LONG).show();
        }
        EditText etTitle = (EditText)findViewById(R.id.editMemoTitle);
        EditText etMemoInfo = (EditText)findViewById(R.id.memoMessage);
        TextView memoDate = (TextView)findViewById(R.id.textMemoDate);
        RadioButton rbHigh =(RadioButton)findViewById(R.id.high);
        RadioButton rbMedium =(RadioButton)findViewById(R.id.medium);
        RadioButton rbLow =(RadioButton)findViewById(R.id.low);
        if(currentMemo.getPriority().equalsIgnoreCase("high")){
            rbHigh.setChecked(true);
        }
        else if(currentMemo.getPriority().equalsIgnoreCase("medium")){
            rbMedium.setChecked(true);
        }
        else{
            rbLow.setChecked(true);
        }

        etTitle.setText(currentMemo.getMemoTitle());
        etMemoInfo.setText(currentMemo.getMemoInfo());
        memoDate.setText(DateFormat.format("MM/dd/yyyy",
                currentMemo.getMemoDate().getTimeInMillis()).toString());
    }



}
