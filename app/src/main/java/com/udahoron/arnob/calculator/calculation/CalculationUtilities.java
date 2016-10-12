package com.udahoron.arnob.calculator.calculation;

import java.util.Stack;

/**
 * Created by farhanarnob on ${06-Oct-16}.
 */

public class CalculationUtilities {
    private String numberOne = "";
    private String numberTwo = "";
    private String latestOperator = "";
    private Stack<String> bracket = new Stack<>();
    private IdentifyOperatorNumberAndDot identifyOperatorNumberAndDot = new IdentifyOperatorNumberAndDot();
    private boolean flag;
    public String calculate(String displayValue) {
        if (displayValue.length() == 0) {
            return "";
        }
        if (displayValue.length() > 0 && displayValue.charAt(0) == '-' && !identifyOperatorNumberAndDot.hasOperator(displayValue.substring(1))) {
            if (numberTwo.equals("")) {
                return displayValue;
            }
        }
        for (; ; ) {
            if (displayValue.length() > 0 && identifyOperatorNumberAndDot.isDot(displayValue.substring(displayValue.length() - 1))) {
                displayValue = displayValue.substring(0, displayValue.length() - 1);
                continue;
            }
            if (displayValue.length() > 0 && identifyOperatorNumberAndDot.isOperator(displayValue.substring(displayValue.length() - 1))) {
                displayValue = displayValue.substring(0, displayValue.length() - 1);
                continue;
            }
            if (displayValue.length() > 0 && identifyOperatorNumberAndDot.isBracketOpen(displayValue.substring(displayValue.length() - 1))) {
                displayValue = displayValue.substring(0, displayValue.length() - 1);
                continue;
            }
            break;
        }

        if (displayValue.length() > 0 && identifyOperatorNumberAndDot.hasOperator(displayValue.substring(1))) {
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
            if (substring.equals("(")) {
                bracket.push(substring);
                continue;
            }
            if (substring.equals(")")) {
                bracket.pop();
                continue;
            }
            if (!bracket.empty()) {
                numberTwo = numberTwo + substring;
                continue;
            }
            if (identifyOperatorNumberAndDot.isNumber(substring) || identifyOperatorNumberAndDot.isDot(substring)) {
                numberTwo += substring;
            } else if (identifyOperatorNumberAndDot.isOperator(substring)) {
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
                    if (identifyOperatorNumberAndDot.hasOperator(displayValue.substring(i))) {
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

    public String getNumberOne() {
        return numberOne;
    }

    public void setNumberOne(String numberOne) {
        this.numberOne = numberOne;
    }

    public String getNumberTwo() {
        return numberTwo;
    }

    public void setNumberTwo(String numberTwo) {
        this.numberTwo = numberTwo;
    }

    public String getLatestOperator() {
        return latestOperator;
    }

    public void setLatestOperator(String latestOperator) {
        this.latestOperator = latestOperator;
    }

}
