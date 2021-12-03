package de.db.webapp;


import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class FooImpl {

    private final List<String> foos;

    public FooImpl(List<String> foos) {
        this.foos = foos;
    }

    @PostConstruct
    public void bar() {
        System.out.println(foos);
    }
}
