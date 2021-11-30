package de.db.webapp.repositories;

import de.db.webapp.repositories.entity.PersonEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PersonenRepository extends CrudRepository<PersonEntity, String> {

    Iterable<PersonEntity> findByVorname(String vorname);

    @Query("update PersonEntity p set p.vorname=:vorname where p.id=:id") // Patch (nicht idempotent)
    void changeVornameById(String id, String vorname);

    Iterable<PersonEntity> holeAllePersonenMitNachname(String nachname);
}
