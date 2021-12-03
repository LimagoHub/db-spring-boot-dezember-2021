package de.math;


@RunAs(role=ADMIN)
public class CalculatorImpl implements Calculator {



    @Override
    public double add(double a, double b) {
        return a + b;
    }


    @Override
    public double sub(double a, double b) {
        return this.add( a, -b);
    }
}
