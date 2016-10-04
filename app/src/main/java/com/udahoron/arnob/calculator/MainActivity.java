package com.udahoron.arnob.calculator;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = new Button(this);
        View.OnClickListener onclickListener= new View.OnClickListener() {
            public void onClick(View v) {
                switch(v.getId()) {
                    case R.id.nine:
                        Toast.makeText(MainActivity.this, "nine", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.eight:
                        Toast.makeText(MainActivity.this, "eight", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        button.setOnClickListener(onclickListener);
    }
}
