package de.db.webapp.services;

import de.db.webapp.services.models.Person;

import java.util.Optional;

public interface PersonenService {

    boolean speichernOderAendern(Person person) throws PersonenServiceException;
    boolean loeschen(Person person) throws PersonenServiceException;
    boolean loeschen(String id) throws PersonenServiceException;
    Optional<Person> findePersonNachId(String id)throws PersonenServiceException;
    Iterable<Person> findeAlle() throws PersonenServiceException;
}
