package de.db.webapp;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Qualifier;
import java.util.List;

@Configuration
public class FooConfig {

    private String stadt;

    @Bean
    @Qualifier("foo")
    public List<String> foos() {
        return List.of(stadt,"land","fluss");
    }
}
