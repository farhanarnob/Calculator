package com.udahoron.arnob.calculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.udahoron.arnob.calculator.buttonChecker.ButtonFunctionCheck;
import com.udahoron.arnob.calculator.calculation.CalculationUtilities;
import com.udahoron.arnob.calculator.calculation.IdentifyOperatorNumberAndDot;


public class MainActivity extends AppCompatActivity implements Button.OnClickListener {
    private static final String MEMORY = "memory";
    CalculationUtilities calculationUtilities = new CalculationUtilities();
    private IdentifyOperatorNumberAndDot identifyOperatorNumberAndDot;
    private ButtonFunctionCheck buttonFunctionCheck = new ButtonFunctionCheck();
    private TextView screen;
    private TextView subScreen;
    private String displayValue = "";
    private String subDisplayValue = "";
    private Button btnNine, btnEight, btnSeven, btnSix, btnFive, btnFour, btnThree, btnTwo, btnOne, btnZero,
            btnPlus, btnMinus, btnMulti, btnDivide, btnEqual, btnBackspace,btnDot,btnRoundBracketOpen,
            btnRoundBracketClose, btnPlusOrMinus, btnMC, btnMR, btnMPlus, btnMMinus;
    private boolean roundBracketFlag = false, equalButtonClick = false;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = MainActivity.this.getSharedPreferences("memoryInfo", Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        setContentView(R.layout.activity_main);
        screen = (TextView) findViewById(R.id.displayID);
        subScreen = (TextView) findViewById(R.id.subDisplay);
        screen.setMovementMethod(new ScrollingMovementMethod());
        screen.setText(R.string.zero);
        buttonInit();
        btnBackspace.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                displayValue = "";
                subScreen.setText(R.string.zero);
                screen.setText(R.string.zero);
                deleteNumberOneNumberTwoLastOperator();
                if (buttonFunctionCheck.permissionDotEnable(displayValue)) {
                    btnDot.setOnClickListener(MainActivity.this);
                }
                roundBracketFlag = false;
                equalButtonClick = false;
                return true;
            }
        });

        identifyOperatorNumberAndDot = new IdentifyOperatorNumberAndDot();
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
                if (displayValue.equals("Nan") || displayValue.equals("Infinity")) {
                    displayValue = "0";
                }
                if (displayValue.length() == 0) {
                    displayValue = "0";
                    screenShow(R.string.dot);
                    btnDot.setOnClickListener(null);
                } else {
                    displayValue = displayValue + ".";
                    screen.setText(displayValue);
                    btnDot.setOnClickListener(null);
                }

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
                displayValue = buttonFunctionCheck.regardingPlusOrMinusBtn(displayValue, roundBracketFlag);
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
                equalButtonFunction();
                mPlusMinus(R.string.plus);
                break;

            case R.id.M_MINUS:
                equalButtonFunction();
                mPlusMinus(R.string.minus);
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
        if (displayValue.equals("Infinity") || displayValue.equals("NaN")) {
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
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        displayValue = savedInstanceState.getString("SAVE_DISPLAY_VALUE");
        subDisplayValue = savedInstanceState.getString("SAVE_SUB_DISPLAY_VALUE");
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

    private void operatorButtonWork(int id) {
        if (displayValue.equals("Infinity") || displayValue.equals("NaN")) {
            displayValue = "";
            screen.setText("0");
        }
        if (!displayValue.equals("") && !displayValue.equals("-")) {
            if (identifyOperatorNumberAndDot.isOperator(getString(id))) {
                if (displayValue.charAt(displayValue.length() - 1) == '.') {
                    displayValue = displayValue + "0";
                }
                if (roundBracketFlag && !buttonFunctionCheck.minusAsNumberSymbol(displayValue)) {
                    roundBracketFlag = false;
                    if (identifyOperatorNumberAndDot.isMinus(displayValue.substring(displayValue.length() - 1))) {
                        displayValue = displayValue + "0)";
                    } else {
                        displayValue = displayValue + ")";
                    }
                }
            }
            if (displayValue.charAt(displayValue.length() - 1) != '(') {
                if (displayValue.length() != 0 && !identifyOperatorNumberAndDot.atLastHasOperator(displayValue)
                        && !identifyOperatorNumberAndDot.isSameOperator(displayValue, getString(id))
                        ) {
                    screenShow(id);
                    btnDot.setOnClickListener(MainActivity.this);
                } else if (displayValue.length() != 0 && identifyOperatorNumberAndDot.atLastHasOperator(displayValue)) {
                    displayValue = buttonFunctionCheck.backspaceButtonWork(displayValue);
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
                        displayValue = buttonFunctionCheck.backspaceButtonWork(displayValue);
                        screenShow(id);
                        btnDot.setOnClickListener(MainActivity.this);
                    }
                    deleteNumberOneNumberTwoLastOperator();
                }
            }
        }

    }

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
            if (displayValue.equals("Infinity") || displayValue.equals("NaN")) {
                displayValue = "";
            } else {
                displayValue = buttonFunctionCheck.backspaceButtonWork(displayValue);
            }
            if (buttonFunctionCheck.permissionDotEnable(displayValue)) {
                btnDot.setOnClickListener(MainActivity.this);
            }
            if (displayValue.length() != 0) {
                screen.setText(displayValue);
            } else {
                screen.setText("0");
            }
        }
    }

    private void numberButton(int id) {
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
            screenShow(id);
        }
        deleteNumberOneNumberTwoLastOperator();
    }
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

        displayValue = calculationUtilities.calculate(displayValue);

        if (displayValue.length() > 0) {
            screen.setText(displayValue);
        }
        infinityNaNDetection();
        if (!buttonFunctionCheck.permissionDotEnable(displayValue)) {
            btnDot.setOnClickListener(null);
        }

    }

    private void roundBracketOpen() {
        if (!displayValue.equals("")) {
            if (!roundBracketFlag && identifyOperatorNumberAndDot.isOperator(displayValue.substring(displayValue.length() - 1))) {
                displayValue = displayValue + "(";
                screen.setText(displayValue);
                roundBracketFlag = true;
            }
            if (!roundBracketFlag && identifyOperatorNumberAndDot.isNumber(displayValue.substring(displayValue.length() - 1))) {
                displayValue = displayValue + "*(";
                screen.setText(displayValue);
                roundBracketFlag = true;
            }
        }

    }

    private void roundBracketClose() {
        if (roundBracketFlag && !identifyOperatorNumberAndDot.isBracketOpen(displayValue.substring(displayValue.length() - 1))) {
            if (identifyOperatorNumberAndDot.isMinus(displayValue.substring(displayValue.length() - 1))) {
                displayValue = displayValue + "0)";
            } else {
                displayValue = displayValue + ")";
            }
            screen.setText(displayValue);
            roundBracketFlag = false;
        }
    }

    private void mcClear() {
        editor.clear();
        editor.commit();
        Toast.makeText(getApplicationContext(), "memory is 0", Toast.LENGTH_SHORT).show();
    }

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
            Toast.makeText(getApplicationContext(), displayValue + "", Toast.LENGTH_SHORT).show();
        }
    }

    private void mPlusMinus(int plusOrMinus) {
        String temp = sharedPref.getString(MEMORY, "");
        if (!displayValue.equals("") && !displayValue.equals("Infinity") && !displayValue.equals("NaN")) {
            if (displayValue.equals("NaN") || displayValue.equals("Infinity")) {
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
            temp = calculationUtilities.extraZeroRemoving(temp);
            editor.putString(MEMORY, temp + "");
            editor.commit();
            Toast.makeText(getApplicationContext(), "memory : " + temp, Toast.LENGTH_SHORT).show();
        }
        if (!displayValue.equals("Infinity") || !displayValue.equals("NaN")) {
            Toast.makeText(getApplicationContext(), "Can't save Infinity or NaN ", Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "memory is " + temp, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "memory is " + temp, Toast.LENGTH_SHORT).show();
        }
    }

    private void infinityNaNDetection() {
        if (displayValue.equals("Infinity") || displayValue.equals("NaN")) {
            deleteNumberOneNumberTwoLastOperator();
            btnDot.setOnClickListener(MainActivity.this);
        }
    }

}
