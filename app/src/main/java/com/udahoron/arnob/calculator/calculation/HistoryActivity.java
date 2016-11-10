package com.udahoron.arnob.calculator.calculation;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.udahoron.arnob.calculator.MainActivity;
import com.udahoron.arnob.calculator.R;
import com.udahoron.arnob.calculator.calculation.DB.database;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by ${farhanarnob} on ${06-Oct-16}.
 */

public class HistoryActivity extends AppCompatActivity{
    Toolbar toolbar;
    private com.udahoron.arnob.calculator.calculation.DB.database database;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        database = new database(this);
        Cursor res = database.getAllData();

        final ArrayList<String> todoItems = new ArrayList<>();
        ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,todoItems);
        if(res !=null){
            while(res.moveToNext()){
                String history = res.getString(0);
                todoItems.add(history);
            }
            Collections.reverse(todoItems);
            res.close();
        }
        final ListView listView= (ListView) findViewById(R.id.list_items);
        listView.setAdapter(itemAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = listView.getItemAtPosition(position).toString();
                Intent intentWithHistory = new Intent( HistoryActivity.this,MainActivity.class);
                intentWithHistory.putExtra("HISTORY_VALUE",selectedItem);
                startActivityForResult(intentWithHistory,101);
            }
        });

    }
}
