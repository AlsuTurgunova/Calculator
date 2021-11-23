package com.company;

import java.util.Stack;

/**
 * Calculator for calculating a mathematical expression.
 * Basic mathematical operations such as addition, subtraction, multiplication, division and parentheses are used.
 * It is also able to work with decimal numbers supplied at the input.
 *
 * @author Alsu
 */
public class Calculator {

    /**
     * The introduced mathematical expression.
     */
    public String mathExpression;

    /**
     * Stack with numbers
     */
    private Stack<Double> numbers;

    /**
     * Stack with operations
     */
    private Stack<Character> operations;

    /**
     * The index of an element in a mathematical expression.
     */
    private int index;

    /**
     * The answer of the mathematical expression
     */
    public double result;

    /**
     * The possibility of a unary minus.
     */
    private boolean unaryMinus;

    /**
     * Checking the order of operations and numbers.
     */
    private boolean checkOfNumber;


    /**
     * Constructor of the class. Declares initial values to class properties.
     * @param exp A mathematical expression that needs to be calculated.
     */
    public Calculator(String exp) {
        mathExpression = exp.replaceAll(" ", "");
        index = 0;
        unaryMinus = true;
        numbers = new Stack<>();
        operations = new Stack<>();
        checkOfNumber = false;
    }

    /**
     * Method for determining the current row element.
     * @throws Exception Throws an error if any element of a mathematical expression does not fall within
     * the scope of the calculator definition.
     */
    public void parsingAnExpression() throws Exception {
        while (index != mathExpression.length()) {
            if (mathExpression.charAt(index) >= '0' && mathExpression.charAt(index) <= '9' || mathExpression.charAt(index) == '-' && unaryMinus) {
                isNumber();
            }
            else if (mathExpression.charAt(index) == '(') {
                isOpenBracket();
            }
            else if (mathExpression.charAt(index) == ')') {
                isCloseBracket();
            }
            else if (mathExpression.charAt(index) == '+' || mathExpression.charAt(index) == '-' && !unaryMinus) {
                isPlusOrMinus();
            }
            else if (mathExpression.charAt(index) == '*' || mathExpression.charAt(index) == '/') {
                isMultOrDiv();
            }
            else {
                throw new Exception("Invalid character!");
            }
        }
        while(!operations.isEmpty()) {
            isComputing();
        }
        result = numbers.pop();
    }

    /**
     * This method is called when a digit is detected in a number.
     * Then it is extracted from the expression and inserted into the stack with numbers.
     * @throws Exception If more than one point is detected when checking a number, the corresponding error is displayed.
     */
    private void isNumber() throws Exception {
        int token = 1;
        if (mathExpression.charAt(index) == '-') {
            index++;
            token = -1;
        }

        String num = "";
        int countOfDot = 0;
        while (index != mathExpression.length() && (mathExpression.charAt(index) >= '0' && mathExpression.charAt(index) <= '9' || mathExpression.charAt(index) == '.')) {
            if (mathExpression.charAt(index) == '.') {
                countOfDot++;
            }
            num += mathExpression.charAt(index);
            index++;
        }
        if (countOfDot > 1) {
            throw new Exception("An extra dot!");
        }
        double result1 = 0;
        String[] splitNumber = num.split("\\.");
        if (splitNumber.length == 2) {
            result1 = Integer.parseInt(splitNumber[0]) + Integer.parseInt(splitNumber[1]) / Math.pow(10, splitNumber[1].length());
        }
        else if (splitNumber.length == 1) {
            result1 = Integer.parseInt(splitNumber[0]);
        }

        numbers.push(result1 * token);

        unaryMinus = false;
        checkOfNumber = true;
    }


    /**
     * If the current symbol is plus or minus, this method is called. It performs
     * operations on the left depending on their priority with the current sign and puts
     * the sign on the stack with operations.
     * @throws Exception Throws an error if there are several operations in a row.
     */
    private void isPlusOrMinus() throws Exception {
        if (!checkOfNumber) {
            throw new Exception("The location of the operators does not match the calculator");
        }
        boolean exitTheLoop = true;
        while (!operations.isEmpty() && exitTheLoop) {
            if (operations.peek() == '+' || operations.peek() == '-' || operations.peek() == '*' || operations.peek() == '/') {
                isComputing();
            }
            else {
                exitTheLoop = false;
            }
        }
        operations.push(mathExpression.charAt(index++));
        checkOfNumber = false;
    }

    /**
     * If the current symbol is multiplication or division, this method is called. It performs
     * operations on the left depending on their priority with the current sign and puts
     * the sign on the stack with operations.
     * @throws Exception Throws an error if there are several operations in a row.
     */
    private void isMultOrDiv() throws Exception {
        if (!checkOfNumber) {
            throw new Exception("The location of the operators does not match the calculator");
        }
        boolean exitTheLoop = true;
        while (!operations.isEmpty() && exitTheLoop) {
            if (operations.peek() == '*' || operations.peek() == '/') {
                isComputing();
            }
            else {
                exitTheLoop = false;
            }
        }
        operations.push(mathExpression.charAt(index++));
        checkOfNumber = false;
    }

    /**
     * If the current character is an opening parenthesis, then it will put it on the stack with operations.
     * @throws Exception If there is no sign before the bracket (i.e. there is a number or a closing bracket, then an error is thrown.
     */
    private void isOpenBracket() throws Exception {
        if (checkOfNumber && index != 0) {
            throw new Exception("The location of the operators does not match the calculator");
        }
        operations.push(mathExpression.charAt(index));
        index++;
        unaryMinus = true;
        checkOfNumber = true;
    }

    /**
     * If the current character is a closing parenthesis, then this method will go to the opening one
     * and perform operations between them. If there was a minus sign in front of the opening bracket,
     * it replaces it with a plus and makes the result inside the brackets negative.
     * @throws Exception An error is thrown if: 1) There is no number before the closing parenthesis; 2) There is no opening bracket for the current closing one.
     */
    private void isCloseBracket() throws Exception {
        if (!checkOfNumber) {
            throw new Exception("The location of the operators does not match the calculator");
        }
        if (!operations.contains('(')) {
            throw new Exception("Absence of a paired bracket");
        }
        while(operations.peek() != '(') {
            isComputing();
        }
        operations.pop();
        if (!operations.isEmpty()) {
            if (operations.peek() == '-') {
                operations.pop();
                operations.push('+');
                numbers.push(numbers.pop() * (-1));
            }
        }
        index++;
        unaryMinus = true;
        checkOfNumber = true;
    }

    /**
     * Method for basic calculation of operations. Calculates one operation at a time.
     * @throws Exception Throws an error if the denominator is zero or if there is an extra opening parenthesis.
     */
    private void isComputing() throws Exception {
        double num1 = numbers.pop();
        double num2 = numbers.pop();
        switch(operations.peek()) {
            case '+': {
                numbers.push(num1+num2);
                break;
            }
            case '-': {
                numbers.push(num2-num1);
                break;
            }
            case '*': {
                numbers.push(num1*num2);
                break;
            }
            case '/': {
                if (num1 == 0) {
                    throw new Exception("Division by zero!");
                }
                numbers.push(num2/num1);
                break;
            }
            default: throw new Exception("Missing parenthesis");
        }
        operations.pop();
    }


    /**
     * Redefined method
     * @return Outputs the response as a string.
     */
    @Override
    public String toString() {
        return String.valueOf(result);
    }

    /**
     * Calculates the hash code of this object.
     * @return Hash code in integer.
     */
    @Override
    public int hashCode() {
        return (int) (5 * Math.round(result) + mathExpression.length());
    }

    /**
     * A method for comparing two objects. Is overridden.
     * @param obj The object to be compared with the current object.
     * @return True if they are equal, otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        Calculator that = (Calculator) obj;
        return Double.compare(that.result, result) == 0 && mathExpression.equals(that.mathExpression);
    }

}
