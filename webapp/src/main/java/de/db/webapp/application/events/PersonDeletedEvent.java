package de.db.webapp.application.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class PersonDeletedEvent extends BaseEvent{

    private String id; // Payload
}
