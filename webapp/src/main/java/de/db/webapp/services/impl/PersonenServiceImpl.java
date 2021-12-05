package de.db.webapp.services.impl;

import de.db.webapp.repositories.PersonenRepository;
import de.db.webapp.services.PersonenService;
import de.db.webapp.services.PersonenServiceException;
import de.db.webapp.services.mappers.PersonMapper;
import de.db.webapp.services.models.Person;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class PersonenServiceImpl implements PersonenService {

    private final PersonenRepository repo;
    private final PersonMapper mapper;
    private final List<String> antipathen;

    public PersonenServiceImpl(PersonenRepository repo, PersonMapper mapper,List<String> antipathen) {
        this.repo = repo;
        this.mapper = mapper;
        this.antipathen = antipathen;
    }

    /*
            person null -> PSE
            vorname null -> PSE
            vorname < 2 zeichen -> PSE
            nachname null -> PSE
            nachname < 2 zeichen -> PSE

            vorname attila -> PSE

            unexpected Exception in underlying service -> PSE

            Happy Day -> person to Repo
         */



    @Override
    public boolean speichernOderAendern(Person person) throws PersonenServiceException {
        try {
            if(person == null) throw new PersonenServiceException("Person darf nicht null sein");
            if(person.getVorname() == null || person.getVorname().length() < 2) throw new PersonenServiceException("Vorname zu kurz");

            if(antipathen.contains(person.getVorname())) throw new PersonenServiceException("Antipath");

            boolean result = repo.existsById(person.getId());
            repo.save(mapper.convert(person));

            return result;

            // Event
        } catch (RuntimeException e) {
            // event
            //log.error("Upps {}", e.getMessage(), e);
            throw new PersonenServiceException("Upps", e);
        }
    }

    @Override
    public boolean loeschen(Person person) throws PersonenServiceException {

        return loeschen(person.getId());

    }

    @Override
    public boolean loeschen(String id) throws PersonenServiceException {
        try {

            if( repo.existsById(id)) {
                repo.deleteById(id);
                return true;
            }
           return false;
        } catch (RuntimeException e) {
            //log.error("Upps {}", e.getMessage(), e);
            throw new PersonenServiceException("Upps", e);
        }
    }

    @Override
    public Optional<Person> findePersonNachId(String id) throws PersonenServiceException {
        try {
          return repo.findById(id).map(mapper::convert);
        } catch (RuntimeException e) {
            // log.error("Upps {}", e.getMessage(), e);
            throw new PersonenServiceException("Upps", e);
        }
    }

    @Override
    public Iterable<Person> findeAlle() throws PersonenServiceException {
        try {

//            List<Future<Integer>> liste = new ArrayList<>();
//            for (int i = 0; i < 10; i++) {
//                liste.add(sinnDesLebens());
//            }
//
//            liste.forEach(f->System.out.println(convert(f)));
//
            return mapper.convert(repo.findAll());
        } catch (RuntimeException e) {
            //log.error("Upps {}", e.getMessage(), e);
            throw new PersonenServiceException("Upps", e);
        }
    }

//    private int convert(Future<Integer> f) {
//        try {
//            return f.get();
//        } catch (Exception e) {
//            return 0;
//        }
//    }
//
//    @Async
//    public Future<Integer> sinnDesLebens() {
//        int result;
//        try {
//            Thread.sleep(1000);
//            result = 42;
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            result = 0;
//        }
//        return new AsyncResult<Integer>(result);
//    }
}
