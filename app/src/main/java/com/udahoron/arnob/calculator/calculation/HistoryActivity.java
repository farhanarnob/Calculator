package com.udahoron.arnob.calculator.calculation;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.udahoron.arnob.calculator.R;
import com.udahoron.arnob.calculator.calculation.DB.database;

import java.util.ArrayList;

/**
 * Created by ${farhanarnob} on ${06-Oct-16}.
 */

public class HistoryActivity extends AppCompatActivity{
    private com.udahoron.arnob.calculator.calculation.DB.database database;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);
        database = new database(this);
        Cursor res = database.getAllData();

        final ArrayList<String> todoItems = new ArrayList<>();
        ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,todoItems);
        if(res !=null){
            while(res.moveToNext()){
                String history = res.getString(0);
                todoItems.add(history);
            }
            res.close();
        }
        ListView listView= (ListView) findViewById(R.id.list_items);
        listView.setAdapter(itemAdapter);

    }
}
