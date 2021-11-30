package de.db.webapp.controllers.mappers;

import de.db.webapp.controllers.dtos.PersonDTO;
import de.db.webapp.services.models.Person;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface PersonDTOMapper {

    PersonDTO convert(Person person);
    Person convert(PersonDTO personDTO);
    Iterable<PersonDTO> convert(Iterable<Person> personen);
}
