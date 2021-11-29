package de.application.demo;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("upper")
public class ToUpper implements Transformer{
    @Override
    public String transform(String message) {
        return message.toUpperCase();
    }
}
