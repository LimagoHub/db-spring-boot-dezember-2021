package de.application.demo;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Qualifier("lower")
public class ToLower implements  Transformer{
    @Override
    public String transform(String message) {
        return message.toLowerCase();
    }
}
