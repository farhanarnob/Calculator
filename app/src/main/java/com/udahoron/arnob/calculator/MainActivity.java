package com.udahoron.arnob.calculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.udahoron.arnob.calculator.calculation.CalculationUtilities;
import com.udahoron.arnob.calculator.calculation.DB.database;
import com.udahoron.arnob.calculator.calculation.HistoryActivity;
import com.udahoron.arnob.calculator.calculation.IdentifyOperatorNumberAndDot;


public class MainActivity extends AppCompatActivity implements Button.OnClickListener {
    private Toolbar toolbar;
    private database database;
    private static final String MEMORY = "memory";
    CalculationUtilities calculationUtilities = new CalculationUtilities();
    private IdentifyOperatorNumberAndDot identifyOperatorNumberAndDot;
    private TextView screen;
    private TextView subScreen;
    private String displayValue = "";
    private String subDisplayValue = "";
    private Button btnNine, btnEight, btnSeven, btnSix, btnFive, btnFour, btnThree, btnTwo, btnOne, btnZero,
            btnPlus, btnMinus, btnMulti, btnDivide, btnEqual, btnBackspace,btnDot,btnRoundBracketOpen,
            btnRoundBracketClose, btnPlusOrMinus, btnMC, btnMR, btnMPlus, btnMMinus;
    private boolean roundBracketFlag = false, equalButtonClick = false, btnDotFlag = false;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        database = new database(this);

        sharedPref = MainActivity.this.getSharedPreferences("memoryInfo", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.commit();
        screen = (TextView) findViewById(R.id.displayID);
        subScreen = (TextView) findViewById(R.id.subDisplay);
        screen.setMovementMethod(new ScrollingMovementMethod());
        screen.setText(R.string.zero);
        buttonInit();

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            String history_value= extras.getString("HISTORY_VALUE","");
            if(!history_value.equals("")){
                Toast.makeText(MainActivity.this,"history value",Toast.LENGTH_SHORT).show();
                displayValue = history_value;
                subDisplayValue = "";
                roundBracketFlag = false;
                equalButtonClick = false;
                btnDotFlag = false;
                Toast.makeText(MainActivity.this,"history value",Toast.LENGTH_SHORT).show();
                screen.setText(displayValue);
            }

        }



        btnBackspace.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                displayValue = "";
                subScreen.setText(R.string.zero);
                screen.setText(R.string.zero);
                deleteNumberOneNumberTwoLastOperator();
                if (permissionDotEnable()) {
                    btnDot.setOnClickListener(MainActivity.this);
                }
                roundBracketFlag = false;
                equalButtonClick = false;
                return true;
            }
        });

        identifyOperatorNumberAndDot = new IdentifyOperatorNumberAndDot();
    }



    // adding menu item on toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.history){
            Cursor res = database.getAllData();
            if(res.getCount()==0){
                Toast.makeText(this,"no history",Toast.LENGTH_SHORT).show();
            }else {
                Intent intentNew = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intentNew);
            }
        }
        return true;
    }

    // implementing lister of the button
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nine:
                numberButton(R.string.nine);
                break;
            case R.id.eight:
                numberButton(R.string.eight);
                break;
            case R.id.seven:
                numberButton(R.string.seven);
                break;
            case R.id.six:
                numberButton(R.string.six);
                break;
            case R.id.five:
                numberButton(R.string.five);
                break;
            case R.id.four:
                numberButton(R.string.four);
                break;
            case R.id.three:
                numberButton(R.string.three);
                break;
            case R.id.two:
                numberButton(R.string.two);
                break;
            case R.id.one:
                numberButton(R.string.one);
                break;
            case R.id.zero:

                deleteNumberOneNumberTwoLastOperator();
                numberButton(R.string.zero);
                break;
            case R.id.dot:
                btnDotFunction();
                break;
            case R.id.plus:
                operatorButtonWork(R.string.plus);
                break;

            case R.id.minus:
                operatorButtonWork(R.string.minus);
                break;

            case R.id.multi:
                operatorButtonWork(R.string.multi);
                break;

            case R.id.divide:
                operatorButtonWork(R.string.divide);
                break;

            case R.id.backspace:
                backspaceButtonFunction();
                break;

            case R.id.round_bracket_open:
                roundBracketOpen();
                break;

            case R.id.round_bracket_close:
                roundBracketClose();
                break;

            case R.id.equal:
                equalButtonFunction();
                break;

            case R.id.plus_or_minus:
                regardingPlusOrMinusBtn();
                if (displayValue.equals("")) {
                    screen.setText("0");
                } else if (!displayValue.equals("")) {
                    screen.setText(displayValue);
                }
                deleteNumberOneNumberTwoLastOperator();
                break;
            case R.id.MC:
                mcClear();
                break;

            case R.id.MR:
                mrDisplay();
                break;

            case R.id.M_PLUS:
                if (!equalButtonClick) {
                    equalButtonFunction();
                }
                memoryPlusMinus(R.string.plus);
                break;

            case R.id.M_MINUS:
                if (!equalButtonClick) {
                    equalButtonFunction();
                }
                memoryPlusMinus(R.string.M_MINUS);
                break;
        }
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
        btnDot = (Button) findViewById(R.id.dot);
        btnRoundBracketOpen = (Button) findViewById(R.id.round_bracket_open);
        btnRoundBracketClose = (Button) findViewById(R.id.round_bracket_close);
        btnPlusOrMinus = (Button) findViewById(R.id.plus_or_minus);
        btnMC = (Button) findViewById(R.id.MC);
        btnMR = (Button) findViewById(R.id.MR);
        btnMPlus = (Button) findViewById(R.id.M_PLUS);
        btnMMinus = (Button) findViewById(R.id.M_MINUS);

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
        btnDot.setOnClickListener(this);
        btnRoundBracketOpen.setOnClickListener(this);
        btnRoundBracketClose.setOnClickListener(this);
        btnPlusOrMinus.setOnClickListener(this);
        btnMC.setOnClickListener(this);
        btnMR.setOnClickListener(this);
        btnMPlus.setOnClickListener(this);
        btnMMinus.setOnClickListener(this);
    }

    //displaying current status
    private void screenShow(int ins) {
        if (displayValue.equals("Infinity") || displayValue.equals("NaN") || displayValue.equals("ERROR | Max Limit < 10^14")) {
            displayValue = "";
        }

        displayValue = displayValue + getString(ins);
        if (identifyOperatorNumberAndDot.hasDot(displayValue)) {
            btnDot.setOnClickListener(null);
        }

        screen.setText(displayValue);

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("SAVE_DISPLAY_VALUE",displayValue);
        outState.putString("SAVE_SUB_DISPLAY_VALUE", subDisplayValue);
        outState.putBoolean("SAVE_ROUND_BRACKET_FLAG", roundBracketFlag);
        outState.putBoolean("EQUAL_BUTTON_CLICK", equalButtonClick);
        outState.putBoolean("DOT_BUTTON_HAS_CLICK_LISTENER", btnDot.hasOnClickListeners());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        displayValue = savedInstanceState.getString("SAVE_DISPLAY_VALUE");
        subDisplayValue = savedInstanceState.getString("SAVE_SUB_DISPLAY_VALUE");
        roundBracketFlag = savedInstanceState.getBoolean("SAVE_ROUND_BRACKET_FLAG");
        equalButtonClick = savedInstanceState.getBoolean("EQUAL_BUTTON_CLICK");
        if (!savedInstanceState.getBoolean("DOT_BUTTON_HAS_CLICK_LISTENER")) {
            btnDot.setOnClickListener(null);
        }
        if (!displayValue.equals("")) {
            screen.setText(displayValue);
        }
        if (!subDisplayValue.equals("")) {
            subScreen.setText(subDisplayValue);
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void deleteNumberOneNumberTwoLastOperator() {
        calculationUtilities.setNumberOne("");
        calculationUtilities.setNumberTwo("");
        calculationUtilities.setLatestOperator("");
    }


    // +,-,*,/ button work
    private void operatorButtonWork(int id) {
        if (displayValue.length() < 25) {
            if (displayValue.equals("Infinity") || displayValue.equals("NaN") || displayValue.equals("ERROR | Max Limit < 10^14")) {
                displayValue = "";
                screen.setText("0");
            }
            if (roundBracketFlag) {
                return;
            }
            if (!displayValue.equals("") && !displayValue.equals("-")) {
                equalButtonClick = false;
                if (identifyOperatorNumberAndDot.isOperator(getString(id))) {
                    if (displayValue.charAt(displayValue.length() - 1) == '.') {
                        displayValue = displayValue + "0";
                    }
                }
                if (displayValue.charAt(displayValue.length() - 1) != '(') {
                    if (displayValue.length() != 0 && !identifyOperatorNumberAndDot.atLastHasOperator(displayValue)
                            && !identifyOperatorNumberAndDot.isSameOperator(displayValue, getString(id))
                            ) {
                        screenShow(id);
                        btnDot.setOnClickListener(MainActivity.this);
                    } else if (displayValue.length() != 0 && identifyOperatorNumberAndDot.atLastHasOperator(displayValue)) {
                        backspaceButtonWork();
                        screenShow(id);
                        btnDot.setOnClickListener(MainActivity.this);
                    }
                    deleteNumberOneNumberTwoLastOperator();
                } else if (displayValue.charAt(displayValue.length() - 1) == '(') {
                    if (getString(id).equals("-")) {
                        if (displayValue.length() != 0 && !identifyOperatorNumberAndDot.atLastHasOperator(displayValue)
                                && !identifyOperatorNumberAndDot.isSameOperator(displayValue, getString(id))
                                ) {
                            screenShow(id);
                            btnDot.setOnClickListener(MainActivity.this);
                        } else if (displayValue.length() != 0 && identifyOperatorNumberAndDot.atLastHasOperator(displayValue)) {
                            backspaceButtonWork();
                            screenShow(id);
                            btnDot.setOnClickListener(MainActivity.this);
                        }
                        deleteNumberOneNumberTwoLastOperator();
                    }
                }
            }
        }
    }

    // ⌫ button work
    private void backspaceButtonFunction() {
        if (equalButtonClick) {
            displayValue = "";
            screen.setText("0");
            equalButtonClick = false;
        } else if (displayValue.length() > 0) {
            deleteNumberOneNumberTwoLastOperator();
            if (displayValue.charAt(displayValue.length() - 1) == ')') {
                roundBracketFlag = true;
            } else if (displayValue.charAt(displayValue.length() - 1) == '(') {
                roundBracketFlag = false;
            }
            if (displayValue.equals("Infinity") || displayValue.equals("NaN") || displayValue.equals("ERROR | Max Limit < 10^14")) {
                displayValue = "";
            } else {
                displayValue = backspaceButtonWork();
            }
            if (permissionDotEnable()) {
                btnDot.setOnClickListener(MainActivity.this);
            }
            if (displayValue.length() != 0) {
                screen.setText(displayValue);
            } else {
                screen.setText("0");
            }
        }
    }

    // 0,1,2,3,4,5,6,7,8,9 number button function
    private void numberButton(int id) {
        if (displayValue.length() < 25) {
            if (equalButtonClick && !identifyOperatorNumberAndDot.hasOperator(displayValue)) {
                btnDot.setOnClickListener(MainActivity.this);
                displayValue = "";
                equalButtonClick = false;
            }
            if (getString(id).equals("0")) {
                if (!displayValue.equals("") && !displayValue.equals("0")) {
                    screenShow(R.string.zero);
                }

            } else {
                if (!displayValue.equals("") && displayValue.substring(displayValue.length() - 1).equals(")")) {
                    displayValue = displayValue + "*";
                    btnDot.setOnClickListener(MainActivity.this);
                }
                screenShow(id);
            }
            deleteNumberOneNumberTwoLastOperator();
        }
    }

    //dot button checking
    private void btnDotFunction() {
        if (displayValue.length() < 25) {
            if (equalButtonClick) {
                displayValue = "0";
                equalButtonClick = false;
            } else if (displayValue.equals("Nan") || displayValue.equals("Infinity") || displayValue.equals("ERROR | Max Limit < 10^14")) {
                displayValue = "0";
                equalButtonClick = false;
            }
            if (displayValue.length() == 0) {
                displayValue = "0";
                screenShow(R.string.dot);
                equalButtonClick = false;
                btnDot.setOnClickListener(null);
            } else {
                if (!displayValue.equals("") && displayValue.substring(displayValue.length() - 1).equals(")")) {
                    displayValue = displayValue + "*";
                    btnDot.setOnClickListener(MainActivity.this);
                }
                displayValue = displayValue + ".";
                screen.setText(displayValue);
                equalButtonClick = false;
                btnDot.setOnClickListener(null);
            }
        }
    }

    // = button function
    private void equalButtonFunction() {
        roundBracketFlag = false;
        equalButtonClick = true;
        infinityNaNDetection();
        btnDot.setOnClickListener(MainActivity.this);
        if (displayValue.equals("0")) {
            displayValue = "";
            deleteNumberOneNumberTwoLastOperator();

            subDisplayValue = displayValue;
            subScreen.setText("0");
            screen.setText("0");
        }
        if (displayValue.length() > 0) {
            if (identifyOperatorNumberAndDot.hasOperator(displayValue.substring(displayValue.length() - 1))) {
                subDisplayValue = displayValue.substring(0, displayValue.length() - 1) + calculationUtilities.getLatestOperator() + calculationUtilities.getNumberTwo();
            } else {
                subDisplayValue = displayValue + calculationUtilities.getLatestOperator() + calculationUtilities.getNumberTwo();
            }
            subScreen.setText(subDisplayValue);
        }
        String val = displayValue+calculationUtilities.getLatestOperator()+calculationUtilities.getNumberTwo();

        displayValue = calculationUtilities.calculate(displayValue);
        if(database.insertData(val+"="+displayValue)){
            Toast.makeText(this,"data inserted",Toast.LENGTH_SHORT).show();
        }
        if (displayValue.length() > 0) {
            screen.setText(displayValue);
        }
        infinityNaNDetection();

    }

    // "(" button function
    private void roundBracketOpen() {
        if (!roundBracketFlag && !displayValue.equals("") && displayValue.length() < 25) {
            if (identifyOperatorNumberAndDot.isOperator(displayValue.substring(displayValue.length() - 1))) {
                displayValue = displayValue + "(";
                screen.setText(displayValue);
                roundBracketFlag = true;
            }
            if ((identifyOperatorNumberAndDot.isNumber(displayValue.substring(displayValue.length() - 1))
                    || displayValue.substring(displayValue.length() - 1).equals(")"))) {
                displayValue = displayValue + "*(";
                screen.setText(displayValue);
                btnDot.setOnClickListener(MainActivity.this);
                roundBracketFlag = true;
            } else if (displayValue.substring(displayValue.length() - 1).equals(".")) {
                displayValue = displayValue + "0*(";
                screen.setText(displayValue);
                btnDot.setOnClickListener(MainActivity.this);
                roundBracketFlag = true;
            }
        }

    }

    // ")" button function
    private void roundBracketClose() {
        if (displayValue.length() < 25) {
            if (roundBracketFlag && !identifyOperatorNumberAndDot.isBracketOpen(displayValue.substring(displayValue.length() - 1))
                    && !identifyOperatorNumberAndDot.isDot(displayValue.substring(displayValue.length() - 1))) {

                displayValue = displayValue + ")";
                screen.setText(displayValue);
                roundBracketFlag = false;
            } else if (roundBracketFlag && displayValue.substring(displayValue.length() - 1).equals(".")) {
                displayValue = displayValue + "0)";
                screen.setText(displayValue);
                btnDot.setOnClickListener(MainActivity.this);
                roundBracketFlag = false;
            }
        }

    }

    // plusMinus button
    private void regardingPlusOrMinusBtn() {
        if (displayValue.length() < 25) {
            if (displayValue.length() != 0 && !displayValue.equals("Infinity") && !displayValue.equals("NaN")) {
                if (!identifyOperatorNumberAndDot.hasOperator(displayValue.substring(1))) {
                    if (!displayValue.substring(0, 1).equals("-")) {
                        displayValue = "-" + displayValue;
                    } else if (displayValue.substring(0, 1).equals("-")) {
                        displayValue = displayValue.substring(1);

                    }
                }
//                else if (displayValue.charAt(displayValue.length() - 1) == '-') {
//                    displayValue = displayValue.substring(0, displayValue.length() - 1);
//                }
                else if (displayValue.charAt(displayValue.length() - 1) == '(') {
                    displayValue = displayValue + "-";
                } else if (roundBracketFlag) {
                    int i = 0;
                    for (int x = displayValue.length() - 1; x >= 0; x--) {
                        if (displayValue.charAt(x) == '(') {
                            i = x;
                            break;
                        }

                    }
                    if (i < displayValue.length() && i != 0) {
                        if (identifyOperatorNumberAndDot.isNumber(displayValue.substring(i + 1, i + 2))) {
                            displayValue = displayValue.substring(0, i + 1) + "-" + displayValue.substring(i + 1);
                        } else if (identifyOperatorNumberAndDot.isMinus(displayValue.substring(i + 1, i + 2))) {
                            displayValue = displayValue.substring(0, i + 1) + displayValue.substring(i + 2);
                        }
                    }
                } else if (identifyOperatorNumberAndDot.hasOperator(displayValue)) {
                    int i;
                    for (i = displayValue.length() - 1; i >= 0; i--) {
                        if (identifyOperatorNumberAndDot.isOperator(displayValue.substring(i, i + 1))) {
                            break;
                        }
                    }
                    displayValue = displayValue.substring(0, i + 1) + "(-" + displayValue.substring(i + 1);
                    screen.setText(displayValue);
                    btnDot.setOnClickListener(MainActivity.this);
                    roundBracketFlag = true;
                }

            } else if (displayValue.equals("")) {
                displayValue = "-";
            } else if (displayValue.equals("-")) {
                displayValue = "";
            }

        }
    }

    // MC button function
    private void mcClear() {
        editor.clear();
        editor.commit();
        Toast.makeText(getApplicationContext(), "memory is 0", Toast.LENGTH_SHORT).show();
    }

    // MR button function
    private void mrDisplay() {
        displayValue = "";
        String temp = sharedPref.getString(MEMORY, "");
        temp = calculationUtilities.extraZeroRemoving(temp);
        if (temp.equals("")) {
            screen.setText("0");
            Toast.makeText(getApplicationContext(), "memory is 0", Toast.LENGTH_SHORT).show();
        } else {
            displayValue = temp;
            screen.setText(displayValue);
            Toast.makeText(getApplicationContext(), "memory is " + displayValue, Toast.LENGTH_SHORT).show();
        }
    }


    // M+ and M- button function
    private void memoryPlusMinus(int plusOrMinus) {
        String temp = sharedPref.getString(MEMORY, "");
        if (!displayValue.equals("") && !displayValue.equals("Infinity") && !displayValue.equals("NaN") && !displayValue.equals("ERROR | Max Limit < 10^14")) {
            if (displayValue.equals("NaN") || displayValue.equals("Infinity") || displayValue.equals("ERROR | Max Limit < 10^14")) {
                displayValue = "";
                screen.setText("0");
            }
            if (temp.equals("")) {
                temp = "0";
            }
            if ((getString(plusOrMinus).equals("+"))) {
                temp = (Double.parseDouble(temp) + Double.parseDouble(displayValue)) + "";
            } else {
                temp = (Double.parseDouble(temp) - Double.parseDouble(displayValue)) + "";
            }
            if (!equalButtonClick) {
                temp = calculationUtilities.extraZeroRemoving(temp);
            }
            editor.putString(MEMORY, temp + "");
            editor.commit();
            Toast.makeText(getApplicationContext(), "memory : " + temp, Toast.LENGTH_SHORT).show();
        } else if (displayValue.equals("Infinity") || displayValue.equals("NaN") || displayValue.equals("ERROR | Max Limit < 10^14")) {
            Toast.makeText(getApplicationContext(), "Can't save Infinity or NaN or Error Value", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "memory is " + temp, Toast.LENGTH_SHORT).show();
        }
    }


    // NaN & Infinity button work
    private void infinityNaNDetection() {
        if (displayValue.equals("Infinity") || displayValue.equals("NaN") || displayValue.equals("ERROR | Max Limit < 10^14")) {
            deleteNumberOneNumberTwoLastOperator();
            btnDot.setOnClickListener(MainActivity.this);
        }
    }


    //removing last value or operator
    private String backspaceButtonWork() {

        if (displayValue.length() > 0) {
            displayValue = displayValue.substring(0, displayValue.length() - 1);
            return displayValue;

        }
        return displayValue;

    }

    public boolean permissionDotEnable() {
        for (int i = displayValue.length(); i >= 1; i--) {
            if (identifyOperatorNumberAndDot.isOperator(displayValue.substring(i - 1, i))) {
                return true;
            }
            if (displayValue.charAt(i - 1) == '.') {
                return false;
            }
        }
        return true;
    }


}
