package de.application.math;

public class CalculatorSecure implements Calculator{
    private final Calculator calculator;

    public CalculatorSecure(Calculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public double add(double a, double b) {
        System.out.println("Du kommst hier rein");
        return calculator.add(a, b);
    }

    @Override
    public double sub(double a, double b) {
        return calculator.sub(a, b);
    }
}
