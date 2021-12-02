package de.db.webapp.repositories;

import de.db.webapp.repositories.entity.PersonEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class PersonenCustomRepositoryImpl implements PersonenCustomRepository{


    private final EntityManager em;

    @Override
    public PersonEntity getExample() {
        TypedQuery<PersonEntity> myQuery = em.createQuery("SELECT p from PersonEntity p where p.vorname='John'", PersonEntity.class);

        myQuery.setFirstResult(100);
        myQuery.setMaxResults(10);

        return myQuery.getSingleResult();

    }
}
