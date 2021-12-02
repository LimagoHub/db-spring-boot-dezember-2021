package de.db.webapp.repositories;

import de.db.webapp.repositories.entity.PersonEntity;
import de.db.webapp.repositories.entity.TinyPerson;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PersonenRepository extends CrudRepository<PersonEntity, String>, PersonenCustomRepository {

    Iterable<PersonEntity> findByVorname(String vorname);

    @Query("update PersonEntity p set p.vorname=:vorname where p.id=:id") // Patch (nicht idempotent)
    void changeVornameById(String id, String vorname);

    Iterable<PersonEntity> holeAllePersonenMitNachname(String nachname);

    @Query("Select new de.db.webapp.repositories.entity.TinyPerson(p.id, p.nachname) from PersonEntity p")
    Iterable<TinyPerson> findTinyPerson();
}
