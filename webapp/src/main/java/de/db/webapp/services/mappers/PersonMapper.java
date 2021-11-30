package de.db.webapp.services.mappers;

import de.db.webapp.repositories.entity.PersonEntity;
import de.db.webapp.services.models.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonEntity convert(Person person);
    Person convert(PersonEntity person);
    Iterable<Person> convert(Iterable<PersonEntity> entities);
}
