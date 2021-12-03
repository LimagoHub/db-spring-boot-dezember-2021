package de.db.webapp.application.events;

import de.db.webapp.controllers.dtos.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PersonInsertedEvent extends BaseEvent{

    private PersonDTO dto; // Payload
}
