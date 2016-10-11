package com.udahoron.arnob.calculator;

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
                if (displayValue.length() != 0)
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
                if (displayValue.length() != 0 && !identifyOperatorNumberAndDot.atLastHasOperator(displayValue) && !identifyOperatorNumberAndDot.isSameOperator(displayValue, getString(R.string.plus))) {
                    screenShow(R.string.plus);
                    btnDot.setOnClickListener(this);
                } else if (displayValue.length() != 0 && identifyOperatorNumberAndDot.atLastHasOperator(displayValue)) {
                    displayValue = buttonFunctionCheck.backspaceButtonWork(displayValue);
                    screenShow(R.string.plus);
                    btnDot.setOnClickListener(this);
                }
                deleteNumberOneNumberTwoLastOperator();
                break;
            case R.id.minus:
                if (displayValue.length() != 0 && !identifyOperatorNumberAndDot.atLastHasOperator(displayValue) && !identifyOperatorNumberAndDot.isSameOperator(displayValue, getString(R.string.minus))) {
                    screenShow(R.string.minus);
                    btnDot.setOnClickListener(this);
                } else if (displayValue.length() != 0 && identifyOperatorNumberAndDot.atLastHasOperator(displayValue)) {
                    displayValue = buttonFunctionCheck.backspaceButtonWork(displayValue);
                    screenShow(R.string.minus);
                    btnDot.setOnClickListener(this);
                }
                deleteNumberOneNumberTwoLastOperator();
                break;
            case R.id.multi:
                if ((displayValue.length() != 0) && !identifyOperatorNumberAndDot.atLastHasOperator(displayValue) && !identifyOperatorNumberAndDot.isSameOperator(displayValue, getString(R.string.multi))) {
                    screenShow(R.string.multi);
                    btnDot.setOnClickListener(this);
                }
                else if (displayValue.length() != 0 && identifyOperatorNumberAndDot.atLastHasOperator(displayValue)) {
                    displayValue = buttonFunctionCheck.backspaceButtonWork(displayValue);
                    screenShow(R.string.multi);
                    btnDot.setOnClickListener(this);
                }
                deleteNumberOneNumberTwoLastOperator();
                break;
            case R.id.divide:
                if (displayValue.length() != 0 && !identifyOperatorNumberAndDot.atLastHasOperator(displayValue) && !identifyOperatorNumberAndDot.isSameOperator(displayValue, getString(R.string.divide))) {
                    screenShow(R.string.divide);
                    btnDot.setOnClickListener(this);
                } else if (displayValue.length() != 0 && identifyOperatorNumberAndDot.atLastHasOperator(displayValue)) {
                    displayValue = buttonFunctionCheck.backspaceButtonWork(displayValue);
                    screenShow(R.string.divide);
                    btnDot.setOnClickListener(this);
                }
                deleteNumberOneNumberTwoLastOperator();
                break;
            case R.id.backspace:
                Toast.makeText(MainActivity.this, "backspace", Toast.LENGTH_SHORT).show();
                deleteNumberOneNumberTwoLastOperator();
                if (displayValue.equals("Infinity")) {
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
                break;
            case R.id.round_bracket_open:
                Toast.makeText(MainActivity.this, "round_bracket_open", Toast.LENGTH_SHORT).show();
                break;
            case R.id.round_bracket_close:
                Toast.makeText(MainActivity.this, "round_bracket_close", Toast.LENGTH_SHORT).show();
                break;
            case R.id.equal:
                Toast.makeText(MainActivity.this, "equal", Toast.LENGTH_SHORT).show();
                if (displayValue.equals("Infinity")) {
                    deleteNumberOneNumberTwoLastOperator();
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
                if (!buttonFunctionCheck.permissionDotEnable(displayValue)) {
                    btnDot.setOnClickListener(null);
                }
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

}
