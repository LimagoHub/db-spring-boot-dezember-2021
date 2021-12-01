package de.db.webapp.services;

import de.db.webapp.services.models.Person;
import de.db.webapp.services.models.Schwein;

import java.util.Optional;

public interface SchweineService {

    boolean speichernOderAendern(Schwein schwein) throws SchweineServiceException;
    boolean loeschen(String id) throws SchweineServiceException;
    Optional<Schwein> findeSchweinNachId(String id)throws SchweineServiceException;
    Iterable<Schwein> findeAlle() throws SchweineServiceException;
    boolean fuettere(String id) throws SchweineServiceException;
    boolean taufe(String id, String name) throws SchweineServiceException;
}
