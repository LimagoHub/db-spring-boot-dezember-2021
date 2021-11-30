package de.db.webapp;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class Demo {

    @Value("${Demo.message}")
    private String message;

    @PostConstruct
    public void postConstruct() {
       log.info("#########################" + message + "##########################");
    }
}
