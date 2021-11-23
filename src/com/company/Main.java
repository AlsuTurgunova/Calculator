package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to the Mathematical Expressions calculator!\n" +
                "You can enter a mathematical expression in which there can be:\n" +
                "1. Decimal numbers (entered through a dot);\n" +
                "2. Plus (+);\n" +
                "3. Minus(-);\n" +
                "4. Multiplication(*);\n" +
                "5. Division(/);\n" +
                "6. Parentheses.\n");
        String example = sc.nextLine();
        Calculator pars;
            while (example != "") {
                pars = new Calculator(example);
                try {
                    pars.parsingAnExpression();
                    System.out.println(pars.toString());
                }
                catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                example = sc.nextLine();
            }
    }
}
