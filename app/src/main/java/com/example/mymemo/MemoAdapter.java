package com.example.mymemo;

import android.content.Context;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class MemoAdapter extends ArrayAdapter<Memo> {

    private ArrayList<Memo> items;
    private Context adapterContext;

    public MemoAdapter(Context context, ArrayList<Memo> items){
        super(context,R.layout.list_item,items);
        adapterContext = context;
        this.items= items;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        int red = ContextCompat.getColor(getContext(), R.color.system_red);
        int blue = ContextCompat.getColor(getContext(), R.color.system_blue);

        try {
            Memo memo = items.get(position);

            if (v == null) {
                LayoutInflater vi = (LayoutInflater) adapterContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.list_item, null);
            }
            TextView memoTitle = (TextView) v.findViewById(R.id.textMemoTitle);
            TextView memoPriority = (TextView) v.findViewById(R.id.textMemoPriority);
            TextView memoDate = (TextView) v.findViewById(R.id.textListDate);
            Button b = (Button) v.findViewById(R.id.buttonDeleteMemo);
            memoTitle.setText(memo.getMemoTitle());
            memoPriority.setText(memo.getPriority());
            memoDate.setText(DateFormat.format("MM/dd/yyyy",
                    memo.getMemoDate().getTimeInMillis()).toString());
            b.setVisibility(View.INVISIBLE);
            if(memo.getPriority().equalsIgnoreCase("Low")) {
                memoPriority.setTextColor(Color.BLACK);
                memoPriority.setText(memo.getPriority());
            }
            if(memo.getPriority().equalsIgnoreCase("Medium")) {
                memoPriority.setTextColor(Color.BLUE);
                memoPriority.setText(memo.getPriority());
            }
            if(memo.getPriority().equalsIgnoreCase("High")) {
                memoPriority.setTextColor(Color.RED);
                memoPriority.setText(memo.getPriority());
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return v;
    }
    public void showDelete(final int position, final View convertView,
                           final Context context, final Memo memo){
        View v = convertView;
        final Button b = (Button)v.findViewById(R.id.buttonDeleteMemo);
        if(b.getVisibility()==View.INVISIBLE){
            b.setVisibility(View.VISIBLE);
            b.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    hideDelete(position, convertView, context);
                    items.remove(memo);
                    deleteOption(memo.getMemoId(), context);

                }
            });


        }
        else{
            hideDelete(position, convertView, context);
        }
    }
    private void deleteOption(int memoToDelete, Context context){
        MemoDataSource db = new MemoDataSource(context);
        try{
            db.open();
            db.deleteMemo(memoToDelete);
            db.close();
        }
        catch(Exception e){
            Toast.makeText(adapterContext, "Delete memo Failed", Toast.LENGTH_LONG).show();
        }
        this.notifyDataSetChanged();
    }
    public void hideDelete(int position, View convertView, Context context){
        View v = convertView;
        final Button b = (Button)v.findViewById(R.id.buttonDeleteMemo);
        b.setVisibility(View.INVISIBLE);
        b.setOnClickListener(null);

    }

}
