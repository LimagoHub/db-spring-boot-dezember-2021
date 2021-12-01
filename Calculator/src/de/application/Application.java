package de.application;

import de.client.CalcClient;
import de.common.LoggerProxy;
import de.math.Calculator;
import de.math.CalculatorImpl;
import de.math.CalculatorLogger;
import de.math.CalculatorSecure;

import java.time.Duration;
import java.time.Instant;

public class Application {

    public static void main(String[] args) {

        Instant start = Instant.now();
        Calculator calculator = new CalculatorImpl(); // 1000
        //calculator = new CalculatorLogger(calculator);

        calculator = (Calculator) LoggerProxy.newInstance(calculator);

        calculator = new CalculatorSecure(calculator);
        CalcClient client = new CalcClient(calculator);
        client.go();
        Instant ende = Instant.now();

        Duration duration = Duration.between(start,ende);

        System.out.println("Duration = " + duration.toMillis());
    }
}
