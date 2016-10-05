package com.udahoron.arnob.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Button.OnClickListener {
    private TextView screen;
    private String displayValue = "";
    private Button btnNine, btnEight, btnSeven, btnSix, btnFive, btnFour, btnThree, btnTwo, btnOne, btnZero,
            btnPlus, btnMinus, btnMulti, btnDivide, btnEqual, btnBackspace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screen = (TextView) findViewById(R.id.displayID);
        screen.setText("0");
        buttonInit();
        btnBackspace.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                displayValue = "";
                screen.setText("0");
                return true;
            }
        });


    }

    // implementing lister of the button
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nine:
                Toast.makeText(MainActivity.this, "nine", Toast.LENGTH_SHORT).show();
                screenShow(R.string.nine);
                break;
            case R.id.eight:
                Toast.makeText(MainActivity.this, "eight", Toast.LENGTH_SHORT).show();
                screenShow(R.string.eight);
                break;
            case R.id.seven:
                Toast.makeText(MainActivity.this, "seven", Toast.LENGTH_SHORT).show();
                screenShow(R.string.seven);
                break;
            case R.id.six:
                Toast.makeText(MainActivity.this, "six", Toast.LENGTH_SHORT).show();
                screenShow(R.string.six);
                break;
            case R.id.five:
                Toast.makeText(MainActivity.this, "five", Toast.LENGTH_SHORT).show();
                screenShow(R.string.five);
                break;
            case R.id.four:
                Toast.makeText(MainActivity.this, "four", Toast.LENGTH_SHORT).show();
                screenShow(R.string.four);
                break;
            case R.id.three:
                Toast.makeText(MainActivity.this, "three", Toast.LENGTH_SHORT).show();
                screenShow(R.string.three);
                break;
            case R.id.two:
                Toast.makeText(MainActivity.this, "two", Toast.LENGTH_SHORT).show();
                screenShow(R.string.two);
                break;
            case R.id.one:
                Toast.makeText(MainActivity.this, "one", Toast.LENGTH_SHORT).show();
                screenShow(R.string.one);
                break;
            case R.id.zero:
                Toast.makeText(MainActivity.this, "zero", Toast.LENGTH_SHORT).show();
                if (displayValue.length() != 0)
                    screenShow(R.string.zero);
                break;
            case R.id.plus:
                Toast.makeText(MainActivity.this, "plus", Toast.LENGTH_SHORT).show();
                if (displayValue.length() != 0 && !atLastHasOperator() && !isSameOperator(R.id.plus)) {
                    screenShow(R.string.plus);
                } else if (displayValue.length() != 0 && atLastHasOperator()) {
                    backspaceButtonWork();
                    screenShow(R.string.plus);
                }
                break;
            case R.id.minus:
                Toast.makeText(MainActivity.this, "minus", Toast.LENGTH_SHORT).show();
                if (displayValue.length() != 0 && !atLastHasOperator() && !isSameOperator(R.id.minus)) {
                    screenShow(R.string.minus);
                } else if (displayValue.length() != 0 && atLastHasOperator()) {
                    backspaceButtonWork();
                    screenShow(R.string.minus);
                }
                break;
            case R.id.multi:
                Toast.makeText(MainActivity.this, "multi", Toast.LENGTH_SHORT).show();
                if (displayValue.length() != 0 && !atLastHasOperator() && !isSameOperator(R.id.multi)) {
                    screenShow(R.string.multi);
                } else if (displayValue.length() != 0 && atLastHasOperator()) {
                    backspaceButtonWork();
                    screenShow(R.string.multi);
                }
                break;
            case R.id.divide:
                Toast.makeText(MainActivity.this, "divide", Toast.LENGTH_SHORT).show();
                if (displayValue.length() != 0 && !atLastHasOperator() && !isSameOperator(R.id.divide)) {
                    screenShow(R.string.divide);
                } else if (displayValue.length() != 0 && atLastHasOperator()) {
                    backspaceButtonWork();
                    screenShow(R.string.divide);
                }
                break;
            case R.id.equal:
                Toast.makeText(MainActivity.this, "equal", Toast.LENGTH_SHORT).show();
                break;
            case R.id.backspace:
                Toast.makeText(MainActivity.this, "backspace", Toast.LENGTH_SHORT).show();
                backspaceButtonWork();
                break;
        }
    }

    //inputted operator is already same or not
    private boolean isSameOperator(int operator) {
        return displayValue.charAt(displayValue.length() - 1) == getString(operator).charAt(0);
    }

    //checking that at last has already a operator or not
    private boolean atLastHasOperator() {
        return displayValue.charAt(displayValue.length() - 1) == '+' ||
                displayValue.charAt(displayValue.length() - 1) == '-' ||
                displayValue.charAt(displayValue.length() - 1) == '*' ||
                displayValue.charAt(displayValue.length() - 1) == '/';
    }


    //Button initialization
    private void buttonInit() {
        btnNine = (Button) findViewById(R.id.nine);
        btnEight = (Button) findViewById(R.id.eight);
        btnSeven = (Button) findViewById(R.id.seven);
        btnSix = (Button) findViewById(R.id.six);
        btnFive = (Button) findViewById(R.id.five);
        btnFour = (Button) findViewById(R.id.four);
        btnThree = (Button) findViewById(R.id.three);
        btnTwo = (Button) findViewById(R.id.two);
        btnOne = (Button) findViewById(R.id.one);
        btnZero = (Button) findViewById(R.id.zero);
        btnPlus = (Button) findViewById(R.id.plus);
        btnMinus = (Button) findViewById(R.id.minus);
        btnMulti = (Button) findViewById(R.id.multi);
        btnDivide = (Button) findViewById(R.id.divide);
        btnEqual = (Button) findViewById(R.id.equal);
        btnBackspace = (Button) findViewById(R.id.backspace);

        btnNine.setOnClickListener(this);
        btnEight.setOnClickListener(this);
        btnSeven.setOnClickListener(this);
        btnSix.setOnClickListener(this);
        btnFive.setOnClickListener(this);
        btnFour.setOnClickListener(this);
        btnThree.setOnClickListener(this);
        btnTwo.setOnClickListener(this);
        btnOne.setOnClickListener(this);
        btnZero.setOnClickListener(this);
        btnPlus.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        btnMulti.setOnClickListener(this);
        btnDivide.setOnClickListener(this);
        btnEqual.setOnClickListener(this);
        btnBackspace.setOnClickListener(this);
    }

    //displaying current status
    private void screenShow(int ins) {
        displayValue = displayValue + getString(ins);
        screen.setText(displayValue);
    }

    //removing last value or operator
    private void backspaceButtonWork() {
        if (displayValue.length() > 0) {
            displayValue = displayValue.substring(0, displayValue.length() - 1);
            screen.setText(displayValue);
        }
        if (displayValue.length() == 0) {
            screen.setText("0");
        }
    }


}
