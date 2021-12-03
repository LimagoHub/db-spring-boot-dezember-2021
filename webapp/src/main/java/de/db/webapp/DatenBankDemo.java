package de.db.webapp;


import de.db.webapp.repositories.PersonenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

//@Component
public class DatenBankDemo {

    @Autowired
    private PersonenRepository repo;

    @PostConstruct
    public void foo() {

        repo.findTinyPerson().forEach(System.out::println);
    }
}
