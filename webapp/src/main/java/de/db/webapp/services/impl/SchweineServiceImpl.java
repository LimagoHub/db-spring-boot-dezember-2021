package de.db.webapp.services.impl;

import de.db.webapp.repositories.SchweineRepository;
import de.db.webapp.services.PersonenServiceException;
import de.db.webapp.services.SchweineService;
import de.db.webapp.services.SchweineServiceException;
import de.db.webapp.services.mappers.SchweineMapper;
import de.db.webapp.services.models.Schwein;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class SchweineServiceImpl implements SchweineService {

    private final SchweineRepository repo;
    private final SchweineMapper mapper;
    @Override
    public boolean speichernOderAendern(Schwein schwein) throws SchweineServiceException {
        try {
            if(schwein == null) throw new SchweineServiceException("Schwein darf nicht null sein");
            if(schwein.getName() == null || schwein.getName().equals("Elsa")) throw new SchweineServiceException("Name nicht erlaubt");
            boolean result = repo.existsById(schwein.getId());
            repo.save(mapper.convert(schwein));
            return result;
        } catch (RuntimeException e) {
            log.error("Upps {}", e.getMessage(), e);
            throw new SchweineServiceException("Upps", e);
        }
    }

    @Override
    public boolean loeschen(String id) throws SchweineServiceException {
        try {

            if( repo.existsById(id)) {
                repo.deleteById(id);
                return true;
            }
            return false;
        } catch (RuntimeException e) {
            log.error("Upps {}", e.getMessage(), e);
            throw new SchweineServiceException("Upps", e);
        }
    }

    @Override
    public Optional<Schwein> findeSchweinNachId(String id) throws SchweineServiceException {
        try {
            return repo.findById(id).map(mapper::convert);
        } catch (RuntimeException e) {
            log.error("Upps {}", e.getMessage(), e);
            throw new SchweineServiceException("Upps", e);
        }
    }

    @Override
    public Iterable<Schwein> findeAlle() throws SchweineServiceException {
        try {
            return mapper.convert(repo.findAll());
        } catch (RuntimeException e) {
            log.error("Upps {}", e.getMessage(), e);
            throw new SchweineServiceException("Upps", e);
        }
    }

    @Override
    public boolean fuettere(String id) throws SchweineServiceException {
        Optional<Schwein> optionalSchwein = findeSchweinNachId(id);
        if(optionalSchwein.isPresent()) {
            Schwein piggy = optionalSchwein.get();
            piggy.fuettere();
            return speichernOderAendern(piggy);

        }
        return false;

    }

    @Override
    public boolean taufe(String id, String name) throws SchweineServiceException {
        try {
            Optional<Schwein> optionalSchwein = findeSchweinNachId(id);
            if(optionalSchwein.isPresent()) {
                Schwein piggy = optionalSchwein.get();
                piggy.taufe(name);
                return speichernOderAendern(piggy);

            }
            return false;
        } catch (IllegalArgumentException e) {
            log.error("Upps {}", e.getMessage(), e);
            throw new SchweineServiceException("Upps", e);
        }
    }
}
