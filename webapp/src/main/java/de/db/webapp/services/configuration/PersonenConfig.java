package de.db.webapp.services.configuration;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
//@Profile("test")
public class PersonenConfig {

    @Bean
    @Qualifier("antipathen")
    //@Profile("test")
    public List<String> createAntipathen() {
        return List.of("Attila", "Peter", "Paul", "Mary");
    }

    @Bean
    //@Profile("production")
    @Qualifier("fruits")
    public List<String> fruits() {
        return List.of("Apple", "Cherry", "Strawberry", "Raspberry");
    }

//NOSONAR    @Bean
//NOSONAR   public PersonenService createPersonenService(PersonenRepository repo, PersonMapper mapper, @Qualifier("antipathen") List<String> antipathen) {
//NOSONAR        return new PersonenServiceImpl(repo,mapper,antipathen);
//NOSONAR   }
}
