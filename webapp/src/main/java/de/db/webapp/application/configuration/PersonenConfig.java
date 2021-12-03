package de.db.webapp.application.configuration;


import de.db.webapp.repositories.PersonenRepository;
import de.db.webapp.services.PersonenService;
import de.db.webapp.services.impl.PersonenServiceImpl;
import de.db.webapp.services.mappers.PersonMapper;
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

   @Bean
   public PersonenService createPersonenService(PersonenRepository repo, PersonMapper mapper, @Qualifier("antipathen") List<String> antipathen) {
        return new PersonenServiceImpl(repo,mapper,antipathen);
  }
}
