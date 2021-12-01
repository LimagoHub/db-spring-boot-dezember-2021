package de.db.webapp.controllers.mappers;

import de.db.webapp.controllers.dtos.SchweinDTO;
import de.db.webapp.services.models.Schwein;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface SchweineDTOMapper {

    SchweinDTO convert(Schwein schwein);
    Schwein convert(SchweinDTO schweinDTO);
    Iterable<SchweinDTO> convert(Iterable<Schwein> schweine);
}
