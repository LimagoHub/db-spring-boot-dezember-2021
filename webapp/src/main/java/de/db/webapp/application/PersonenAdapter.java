package de.db.webapp.application;

import de.db.webapp.application.events.PersonDeletedEvent;
import de.db.webapp.application.events.PersonInsertedEvent;
import de.db.webapp.application.events.PersonUpdatedEvent;

public interface PersonenAdapter {
    void handle(PersonInsertedEvent event);

    void handle(PersonUpdatedEvent event);

    void handle(PersonDeletedEvent event);
}
