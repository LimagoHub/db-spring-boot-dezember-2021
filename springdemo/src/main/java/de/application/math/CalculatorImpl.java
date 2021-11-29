package de.application.math;

public class CalculatorImpl implements Calculator {

    @Override
    public double add(double a, double b) {
        return a + b;
    }
    @Override
    public double sub(double a, double b) {
        return add( a, -b);
    }
}
