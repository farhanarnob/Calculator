package com.udahoron.arnob.calculator.calculation;

/**
 * Created by ${farhanarnob} on 06-Oct-16.
 */

public class CalculationUtilities {
    private String numberOne = "";
    private String numberTwo = "";
    private String latestOperator = "";
    private IdentifyOperatorNumberAndDot identifyOperator = new IdentifyOperatorNumberAndDot();

    public String calculate(String displayValue) {
        if (displayValue.length() > 0 && identifyOperator.isOperator(displayValue.substring(displayValue.length() - 1))) {
            displayValue = displayValue.substring(0, displayValue.length() - 1);
        }
        if (identifyOperator.hasOperator(displayValue)) {
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
        for (int i = 0; i < displayValue.length(); i++) {
            String substring = displayValue.substring(i, i + 1);
            if (identifyOperator.isNumber(substring) || identifyOperator.isDot(substring)) {
                numberTwo += substring;
            }
            if (identifyOperator.isOperator(substring)) {
                if (latestOperator.equals("") && (i + 1) != displayValue.length()) {
                    latestOperator = substring;
                    numberOne = numberTwo;
                    numberTwo = "";
                } else if (!latestOperator.equals("") && (i + 1) != displayValue.length()) {
                    calculation();
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
        }
        return String.valueOf(Math.round(value));
    }
}
