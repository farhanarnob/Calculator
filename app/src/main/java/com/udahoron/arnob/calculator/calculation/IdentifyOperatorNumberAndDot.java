package com.udahoron.arnob.calculator.calculation;

/**
 * Created by farhanarnob on 06-Oct-16.
 */

class IdentifyOperatorNumberAndDot {
    boolean isOperator(String operator) {
        return operator.equals("+") || operator.equals("-") || operator.equals("*") || operator.equals("/");
    }

    boolean hasOperator(String value) {
        for (int i = 0; i < value.length(); i++) {
            if (isOperator(value.substring(i, i + 1))) {
                return true;
            }
        }
        return false;
    }

    boolean isNumber(String number) {
        return number.charAt(0) >= '0' && number.charAt(0) <= '9';
    }

    boolean isDot(String dot) {
        return dot.charAt(0) == '.';
    }
}
