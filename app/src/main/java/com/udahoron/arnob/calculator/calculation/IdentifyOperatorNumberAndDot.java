package com.udahoron.arnob.calculator.calculation;

/**
 * Created by farhanarnob on 06-Oct-16.
 */

public class IdentifyOperatorNumberAndDot {
    public boolean isOperator(String operator) {
        return operator.equals("+") || operator.equals("-") || operator.equals("*") || operator.equals("/");
    }

    public boolean hasOperator(String value) {
        for (int i = 0; i < value.length(); i++) {
            if (isOperator(value.substring(i, i + 1))) {
                return true;
            }
        }
        return false;
    }

    public boolean isNumber(String number) {
        return number.charAt(0) >= '0' && number.charAt(0) <= '9';
    }

    public boolean isDot(String dot) {
        return dot.charAt(0) == '.';
    }

    //checking that at last has already a operator or not
    public boolean atLastHasOperator(String displayValue) {
        return displayValue.charAt(displayValue.length() - 1) == '+' ||
                displayValue.charAt(displayValue.length() - 1) == '-' ||
                displayValue.charAt(displayValue.length() - 1) == '*' ||
                displayValue.charAt(displayValue.length() - 1) == '/';
    }

    //inputted operator is already same or not
    public boolean isSameOperator(String displayValue, String operator) {
        return displayValue.charAt(displayValue.length() - 1) == operator.charAt(0);
    }
}
