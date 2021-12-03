package de.db.webapp.application.impl;

import de.db.webapp.application.HandlerException;
import de.db.webapp.application.PersonenAdapter;
import de.db.webapp.application.events.PersonDeletedEvent;
import de.db.webapp.application.events.PersonInsertedEvent;
import de.db.webapp.application.events.PersonUpdatedEvent;
import de.db.webapp.controllers.mappers.PersonDTOMapper;
import de.db.webapp.services.PersonenService;
import de.db.webapp.services.PersonenServiceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class PersonenAdapterImpl implements PersonenAdapter {

    private final PersonenService service;
    private final PersonDTOMapper mapper;

    @Override
    public void handle(PersonInsertedEvent event) {
        try {
            service.speichernOderAendern(mapper.convert(event.getDto()));
            // Success Event
        } catch (PersonenServiceException e) {
            // Failure Event
            throw new HandlerException(e);
        }
    }

    @Override
    public void handle(PersonUpdatedEvent event) {
        try {
            service.speichernOderAendern(mapper.convert(event.getDto()));
            // Success Event
        } catch (PersonenServiceException e) {
            // Failure Event
            throw new HandlerException(e);
        }
    }

    @Override
    public void handle(PersonDeletedEvent event) {
        try {
            service.loeschen(event.getId());
            // Success Event
        } catch (PersonenServiceException e) {
            // Failure Event
            throw new HandlerException(e);
        }
    }

}
