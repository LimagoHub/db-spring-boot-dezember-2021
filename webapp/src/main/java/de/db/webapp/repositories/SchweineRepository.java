package de.db.webapp.repositories;

import de.db.webapp.repositories.entity.SchweinEntity;
import org.springframework.data.repository.CrudRepository;

public interface SchweineRepository extends CrudRepository<SchweinEntity, String> {
}
