package de.application.client;


import de.application.math.Calculator;

public class CalcClient {
    private final Calculator calculator;

    public CalcClient(final Calculator calculator) {
        this.calculator = calculator;
    }

    public void go() {
        System.out.println(calculator.add(3,4));
    }
}
