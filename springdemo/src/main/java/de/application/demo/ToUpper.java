package de.application.demo;

public class ToUpper implements Transformer{
    @Override
    public String transform(String message) {
        return message.toUpperCase();
    }
}
