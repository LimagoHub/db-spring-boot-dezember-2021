package de.db.webapp.services.mappers;

import de.db.webapp.repositories.entity.SchweinEntity;
import de.db.webapp.services.models.Schwein;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SchweineMapper {

    SchweinEntity convert(Schwein schwein);
    Schwein convert(SchweinEntity entity);
    Iterable<Schwein> convert(Iterable<SchweinEntity> entities);
}
