package de.db.webapp.controllers;

import de.db.webapp.controllers.dtos.PersonDTO;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/personen")
public class PersonenController {

    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonDTO> findPersonByID(@PathVariable String id) {
        Optional<PersonDTO>  optional;

        if(id.startsWith("1"))
            optional = Optional.of( PersonDTO.builder().id(id).vorname("John").nachname("Doe").build());
        else
            optional =Optional.empty();

        return ResponseEntity.of(optional);
    }

    @GetMapping(path="", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<PersonDTO>> findAll(@RequestParam(required = false, defaultValue = "") String vorname, @RequestParam(required = false, defaultValue = "") String nachname) {
        System.out.println(String.format("Vorname ist %s und Nachname = %s", vorname, nachname));

        List<PersonDTO> liste = List.of(PersonDTO.builder().id("1").vorname("John").nachname("Doe").build(),PersonDTO.builder().id("2").vorname("John").nachname("Rambo").build(),PersonDTO.builder().id("3").vorname("John").nachname("Wayne").build());
        return ResponseEntity.ok(liste);
    }
}


/*

    Verb            safe            idempotent
    GET             ja              ja
    DELETE          nein            ja
    PUT             nein            ja   (insert or update)
    POST            nein            nein (insert or update)
    PATCH           nein            nein

    POST*           ja              ja (Ersatz-Get wenn Daten im Body übertragen werden müssen)

 */
