package de.application;

import de.client.CalcClient;
import de.math.Calculator;
import de.math.CalculatorImpl;
import de.math.CalculatorLogger;
import de.math.CalculatorSecure;

public class Application {

    public static void main(String[] args) {
        Calculator calculator = new CalculatorImpl();
        calculator = new CalculatorLogger(calculator);
        calculator = new CalculatorSecure(calculator);
        CalcClient client = new CalcClient(calculator);
        client.go();
    }
}
