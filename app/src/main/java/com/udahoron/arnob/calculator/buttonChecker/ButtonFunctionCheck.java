package com.udahoron.arnob.calculator.buttonChecker;

import com.udahoron.arnob.calculator.calculation.IdentifyOperatorNumberAndDot;

/**
 * Created by ${farhanarnob} on ${06-Oct-16}.
 */

public class ButtonFunctionCheck {
    private IdentifyOperatorNumberAndDot identifyOperatorNumberAndDot = new IdentifyOperatorNumberAndDot();

    public String regardingPlusMinusBtn(String displayValue) {
        if (displayValue.length() != 0) {
            if (!identifyOperatorNumberAndDot.hasOperator(displayValue.substring(1))) {
                if (!displayValue.substring(0, 1).equals("-")) {
                    displayValue = "-" + displayValue;
                    return displayValue;
                } else if (displayValue.substring(0, 1).equals("-")) {
                    displayValue = displayValue.substring(1);
                    return displayValue;

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

}
