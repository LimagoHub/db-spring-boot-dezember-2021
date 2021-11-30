package de.db.webapp;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Demo {

    @Value("${Demo.message}")
    private String message;

    @PostConstruct
    public void postConstruct() {
        System.out.println("#########################" + message + "##########################");
    }
}
