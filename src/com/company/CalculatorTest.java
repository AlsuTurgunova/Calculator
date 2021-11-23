package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    Calculator cases;
    @Test
    void test1() throws Exception {
        cases = new Calculator("56.0+345.75/100+57");
        cases.parsingAnExpression();
        assertEquals("116.45750000000001", cases.toString());
    }

    @Test
    void test2() throws Exception {
        cases = new Calculator("83.1+(34-58)*64/(36+10)");
        cases.parsingAnExpression();
        assertEquals("49.70869565217391", cases.toString());
    }

    @Test
    void test3() throws Exception {
        cases = new Calculator("90-45.8+(30-(20-(10-(-10-5))))");
        cases.parsingAnExpression();
        assertEquals("79.2", cases.toString());
    }

    @Test
    void test4() throws Exception {
        cases = new Calculator("457.62+(462.462/2-19)*(456-90)");
        cases.parsingAnExpression();
        assertEquals("78134.166", cases.toString());
    }

    @Test
    void test5() throws Exception {
        try {
            cases = new Calculator("100/0");
            cases.parsingAnExpression();
        } catch (Exception exception) {
            assertEquals("Division by zero!", exception.getMessage());
        }
    }

    @Test
    void test6() throws Exception {
        try {
            cases = new Calculator("748+56*(467+23");
            cases.parsingAnExpression();
        } catch (Exception exception) {
            assertEquals("Missing parenthesis", exception.getMessage());
        }
    }

    @Test
    void test7() throws Exception {
        try {
            cases = new Calculator("6735-(457+)");
            cases.parsingAnExpression();
        } catch (Exception exception) {
            assertEquals("The location of the operators does not match the calculator", exception.getMessage());
        }
    }

    @Test
    void test8() throws Exception {
        try {
            cases = new Calculator("6735-457+6)");
            cases.parsingAnExpression();
        } catch (Exception exception) {
            assertEquals("Absence of a paired bracket", exception.getMessage());
        }
    }

    @Test
    void test9() throws Exception {
        try {
            cases = new Calculator("46+89(36-67)");
            cases.parsingAnExpression();
        } catch (Exception exception) {
            assertEquals("The location of the operators does not match the calculator", exception.getMessage());
        }
    }

    @Test
    void test10() throws Exception {
        try {
            cases = new Calculator("89+90*/89");
            cases.parsingAnExpression();
        } catch (Exception exception) {
            assertEquals("The location of the operators does not match the calculator", exception.getMessage());
        }
    }

    @Test
    void test11() throws Exception {
        try {
            cases = new Calculator("728+467+-90.24");
            cases.parsingAnExpression();
        } catch (Exception exception) {
            assertEquals("The location of the operators does not match the calculator", exception.getMessage());
        }
    }

    @Test
    void test12() throws Exception {
        try {
            cases = new Calculator("89/567.27.35");
            cases.parsingAnExpression();
        } catch (Exception exception) {
            assertEquals("An extra dot!", exception.getMessage());
        }
    }

    @Test
    void test13() throws Exception {
        try {
            cases = new Calculator("123+4*sin(98)");
            cases.parsingAnExpression();
        } catch (Exception exception) {
            assertEquals("Invalid character!", exception.getMessage());
        }
    }
}