package com.udahoron.arnob.calculator.buttonChecker;

import com.udahoron.arnob.calculator.calculation.IdentifyOperatorNumberAndDot;

/**
 * Created by farhanarnob on 06-Oct-16.
 */

public class ButtonFunctionCheck {
    private IdentifyOperatorNumberAndDot identifyOperatorNumberAndDot = new IdentifyOperatorNumberAndDot();

    public String regardingPlusMinusBtn(String displayValue, boolean roundBracketFlag) {
        if (displayValue.length() != 0) {
            if (!identifyOperatorNumberAndDot.hasOperator(displayValue.substring(1))) {
                if (!displayValue.substring(0, 1).equals("-")) {
                    displayValue = "-" + displayValue;
                    return displayValue;
                } else if (displayValue.substring(0, 1).equals("-")) {
                    displayValue = displayValue.substring(1);
                    return displayValue;

                }
            } else if (displayValue.charAt(displayValue.length() - 1) == '-') {
                displayValue = displayValue.substring(0, displayValue.length() - 1);
            } else if (displayValue.charAt(displayValue.length() - 1) == '(') {
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
            }
        }
        return displayValue;
    }

    //removing last value or operator
    public String backspaceButtonWork(String displayValue) {
        if (displayValue.length() > 0) {
            displayValue = displayValue.substring(0, displayValue.length() - 1);
            return displayValue;

        }
        return displayValue;

    }

    public boolean permissionDotEnable(String displayValue) {
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

    public boolean minusAsNumberSymbol(String displayValue) {
        for (int i = (displayValue.length() - 1); i >= 0; i--) {
            if (identifyOperatorNumberAndDot.isBracketOpen(displayValue.substring(i, i + 1))) {
                String subString = displayValue.substring(i);
                if (subString.length() == 1) {
                    return true;
                }
            }
        }
        return false;
    }

}
