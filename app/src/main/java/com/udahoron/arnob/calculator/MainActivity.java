package com.udahoron.arnob.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.udahoron.arnob.calculator.buttonChecker.ButtonFunctionCheck;
import com.udahoron.arnob.calculator.calculation.CalculationUtilities;
import com.udahoron.arnob.calculator.calculation.IdentifyOperatorNumberAndDot;


public class MainActivity extends AppCompatActivity implements Button.OnClickListener {
    CalculationUtilities calculationUtilities = new CalculationUtilities();
    private IdentifyOperatorNumberAndDot identifyOperatorNumberAndDot;
    private ButtonFunctionCheck buttonFunctionCheck = new ButtonFunctionCheck();
    private TextView screen;
    private TextView subScreen;
    private String displayValue = "";
    private String subDisplayValue = "";
    private Button btnNine, btnEight, btnSeven, btnSix, btnFive, btnFour, btnThree, btnTwo, btnOne, btnZero,
            btnPlus, btnMinus, btnMulti, btnDivide, btnEqual, btnBackspace,btnDot,btnRoundBracketOpen,
            btnRoundBracketClose, btnPlusOrMinus;
    private boolean roundBracketFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                screenShow(R.string.nine);
                deleteNumberOneNumberTwoLastOperator();
                break;
            case R.id.eight:
                screenShow(R.string.eight);
                deleteNumberOneNumberTwoLastOperator();
                break;
            case R.id.seven:
                screenShow(R.string.seven);
                deleteNumberOneNumberTwoLastOperator();
                break;
            case R.id.six:
                screenShow(R.string.six);
                deleteNumberOneNumberTwoLastOperator();
                break;
            case R.id.five:
                screenShow(R.string.five);
                deleteNumberOneNumberTwoLastOperator();
                break;
            case R.id.four:
                screenShow(R.string.four);
                deleteNumberOneNumberTwoLastOperator();
                break;
            case R.id.three:
                screenShow(R.string.three);
                deleteNumberOneNumberTwoLastOperator();
                break;
            case R.id.two:
                screenShow(R.string.two);
                deleteNumberOneNumberTwoLastOperator();
                break;
            case R.id.one:
                screenShow(R.string.one);
                deleteNumberOneNumberTwoLastOperator();
                break;
            case R.id.zero:
                if (displayValue.length() > 0 && !displayValue.equals("0"))
                    screenShow(R.string.zero);
                deleteNumberOneNumberTwoLastOperator();
                break;
            case R.id.dot:
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
                displayValue = buttonFunctionCheck.regardingPlusMinusBtn(displayValue);
                if (displayValue.length() > 0) {
                    screen.setText(displayValue);
                }
                deleteNumberOneNumberTwoLastOperator();
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
    }

    //displaying current status
    private void screenShow(int ins) {
        if (displayValue.equals("Infinity")) {
            displayValue = "";
        }
        if (identifyOperatorNumberAndDot.isOperator(getString(ins))) {
            if (displayValue.charAt(displayValue.length() - 1) == '.') {
                displayValue = displayValue + "0";
            }
            if (roundBracketFlag && !buttonFunctionCheck.minusAsNumberSymbol(displayValue)) {
                roundBracketFlag = false;
                displayValue = displayValue + ")";
                Log.d("LOG", displayValue);

            }
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
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        displayValue = savedInstanceState.getString("SAVE_DISPLAY_VALUE");
        if (!displayValue.equals("")) {
            screen.setText(displayValue);
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void deleteNumberOneNumberTwoLastOperator() {
        calculationUtilities.setNumberOne("");
        calculationUtilities.setNumberTwo("");
        calculationUtilities.setLatestOperator("");
    }

    private void operatorButtonWork(int id) {
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

    private void backspaceButtonFunction() {
        if (displayValue.length() > 0) {
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

    private void equalButtonFunction() {
        roundBracketFlag = false;
        if (displayValue.equals("Infinity") || displayValue.equals("NaN")) {
            deleteNumberOneNumberTwoLastOperator();
            btnDot.setOnClickListener(MainActivity.this);
            subDisplayValue = displayValue;
            displayValue = "";
            subScreen.setText("0");
        }
        if (displayValue.equals("0")) {
            displayValue = "";
            deleteNumberOneNumberTwoLastOperator();
            btnDot.setOnClickListener(MainActivity.this);
            subDisplayValue = displayValue;
            subScreen.setText("0");
        }
        if (displayValue.length() > 0) {
            if (identifyOperatorNumberAndDot.hasOperator(displayValue.substring(displayValue.length() - 1))) {
                subDisplayValue = displayValue.substring(0, displayValue.length() - 1) + calculationUtilities.getLatestOperator() + calculationUtilities.getNumberTwo();
            } else {
                subDisplayValue = displayValue + calculationUtilities.getLatestOperator() + calculationUtilities.getNumberTwo();
            }
            subScreen.setText(subDisplayValue);
        }
        if (displayValue.equals("0")) {
            displayValue = "";
            deleteNumberOneNumberTwoLastOperator();
            subDisplayValue = displayValue;
            subScreen.setText("0");
        }
        if (displayValue.equals("Infinity") || displayValue.equals("Nan")) {
            deleteNumberOneNumberTwoLastOperator();
            subDisplayValue = displayValue;
            subScreen.setText("0");
        }

        displayValue = calculationUtilities.calculate(displayValue);
        if (displayValue.length() > 0) {
            screen.setText(displayValue);
        }
        if (!buttonFunctionCheck.permissionDotEnable(displayValue)) {
            btnDot.setOnClickListener(null);
        }
    }

    private void roundBracketOpen() {
        if (!roundBracketFlag && identifyOperatorNumberAndDot.isOperator(displayValue.substring(displayValue.length() - 1))) {
            displayValue = displayValue + "(";
            screen.setText(displayValue);
            roundBracketFlag = true;
        }
    }

    private void roundBracketClose() {
        if (roundBracketFlag && displayValue.charAt(displayValue.length() - 1) != '(') {
            displayValue = displayValue + ")";
            screen.setText(displayValue);
            roundBracketFlag = false;
        }
    }
}
