package de.application.demo;

public class ToLower implements  Transformer{
    @Override
    public String transform(String message) {
        return message.toLowerCase();
    }
}
