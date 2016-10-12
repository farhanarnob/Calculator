package com.udahoron.arnob.calculator.calculation;

import android.util.Log;

/**
 * Created by farhanarnob on 06-Oct-16.
 */

public class IdentifyOperatorNumberAndDot {
    public boolean isOperator(String operator) {
        return operator.equals("+") || operator.equals("-") || operator.equals("*") || operator.equals("/");
    }

    public boolean hasOperator(String value) {
        for (int i = value.length() - 1; i >= 0; i--) {
            if (isOperator(value.substring(i, i + 1))) {
                return true;
            }
        }
        return false;
    }

    public boolean isNumber(String number) {
        return number.charAt(0) >= '0' && number.charAt(0) <= '9';
    }

    public boolean hasDot(String dot) {
        for (int i = dot.length() - 1; i >= 0; i--) {
            if (isOperator(dot.substring(i, i + 1))) {
                return false;
            } else if (dot.substring(i, i + 1).equals(".")) {
                return true;
            }
        }
        return false;
    }

    public boolean isBracketOpen(String bracketOpen) {
        return bracketOpen.equals("(");
    }
    public boolean isDot(String dot) {
        return dot.charAt(0) == '.';
    }

    //checking that at last has already a operator or not
    public boolean atLastHasOperator(String displayValue) {
        Log.d("IdentifyOperator+d", displayValue.substring(displayValue.length() - 1));
        return (displayValue.substring(displayValue.length() - 1).equals("+") ||
                displayValue.substring(displayValue.length() - 1).equals("-") ||
                displayValue.substring(displayValue.length() - 1).equals("*") ||
                displayValue.substring(displayValue.length() - 1).equals("/"));
    }

    //inputted operator is already same or not
    public boolean isSameOperator(String displayValue, String operator) {
        return displayValue.charAt(displayValue.length() - 1) == operator.charAt(0);
    }
}
