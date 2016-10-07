package com.udahoron.arnob.calculator.calculation;

import android.util.Log;

/**
 * Created by ${farhanarnob} on 06-Oct-16.
 */

public class CalculationUtilities {
    private String numberOne = "";
    private String numberTwo = "";
    private String latestOperator = "";
    private IdentifyOperatorNumberAndDot identifyOperator = new IdentifyOperatorNumberAndDot();
    private boolean flag;
    public String calculate(String displayValue) {
        Log.d("CalculationUtilities", numberOne + "**" + displayValue);
        if (displayValue.charAt(0) == '-' && !identifyOperator.hasOperator(displayValue.substring(1))) {
            if (numberTwo.equals("")) {
                return displayValue;
            }
        }
        if (displayValue.length() > 0 && identifyOperator.isOperator(displayValue.substring(displayValue.length() - 1))) {
            displayValue = displayValue.substring(0, displayValue.length() - 1);
        }
        if (identifyOperator.hasOperator(displayValue.substring(1))) {
            numberOne = "";
            numberTwo = "";
            latestOperator = "";
        } else {
            numberOne = displayValue;
            if (!numberTwo.equals("")) {
                calculation();
            }
            return numberOne;
        }
        return startToCalculate(displayValue);

    }

    private String startToCalculate(String displayValue) {
        if (displayValue.charAt(0) == '-') {
            flag = true;
            displayValue = displayValue.substring(1);
        }
        for (int i = 0; i < displayValue.length(); i++) {
            String substring = displayValue.substring(i, i + 1);
            if (identifyOperator.isNumber(substring) || identifyOperator.isDot(substring)) {
                numberTwo += substring;
            } else if (identifyOperator.isOperator(substring)) {
                if (latestOperator.equals("") && (i + 1) != displayValue.length()) {
                    latestOperator = substring;
                    if (flag) {
                        numberOne = "-" + numberTwo;
                        flag = false;
                    } else {
                        numberOne = numberTwo;
                    }
                    numberTwo = "";
                } else if (!latestOperator.equals("") && (!numberOne.equals("") || !numberOne.equals("-")) && !numberTwo.equals("")) {
                    calculation();
                    if (identifyOperator.hasOperator(displayValue.substring(i))) {
                        numberTwo = "";
                        latestOperator = substring;
                    }
                }
            }
        }

        calculation();
        return numberOne;
    }

    private void calculation() {
        switch (latestOperator) {
            case "+":
                Double value = (Double.parseDouble(numberOne) + Double.parseDouble(numberTwo));
                numberOne = rounding(value);
                break;
            case "-":
                value = (Double.parseDouble(numberOne) - Double.parseDouble(numberTwo));
                numberOne = rounding(value);
                break;
            case "*":
                value = (Double.parseDouble(numberOne) * Double.parseDouble(numberTwo));
                numberOne = rounding(value);
                break;
            case "/":
                value = (Double.parseDouble(numberOne) / Double.parseDouble(numberTwo));
                numberOne = rounding(value);
                break;
        }
    }

    private String rounding(Double value) {
        if (value % 1 != 0) {
            return String.format("%.2f", value);
        } else {
            return String.valueOf(Math.round(value));
        }

    }
}
