package de.db.webapp.controllers;

import de.db.webapp.controllers.dtos.PersonDTO;
import de.db.webapp.controllers.mappers.PersonDTOMapper;
import de.db.webapp.services.PersonenService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/personen")
@AllArgsConstructor
public class PersonenController {

    private final PersonenService service;
    private final PersonDTOMapper mapper;

    @ApiResponse(responseCode = "200", description = "Person wurde gefunden")
    @ApiResponse(responseCode = "404", description = "Person wurde nicht gefunden")
    @ApiResponse(responseCode = "400", description = "Bad Request" )
    @ApiResponse(responseCode = "500", description = "Interner Serverfehler")

    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonDTO> findPersonByID(@PathVariable String id) throws Exception{
        Optional<PersonDTO>  optional;

        if(id.startsWith("1"))
            optional = Optional.of( PersonDTO.builder().id(id).vorname("John").nachname("Doe").build());
        else
            optional =Optional.empty();

        return ResponseEntity.of(optional);
    }


    @ApiResponse(responseCode = "200", description = "Personliste wurde erstellt")

    @ApiResponse(responseCode = "400", description = "Bad Request" )
    @ApiResponse(responseCode = "500", description = "Interner Serverfehler")
    @GetMapping(path="", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<PersonDTO>> findAll(@RequestParam(required = false, defaultValue = "") String vorname, @RequestParam(required = false, defaultValue = "") String nachname) {
        System.out.println(String.format("Vorname ist %s und Nachname = %s", vorname, nachname));

        List<PersonDTO> liste = List.of(PersonDTO.builder().id("1").vorname("John").nachname("Doe").build(),PersonDTO.builder().id("2").vorname("John").nachname("Rambo").build(),PersonDTO.builder().id("3").vorname("John").nachname("Wayne").build());
        return ResponseEntity.ok(liste);
    }

    @ApiResponse(responseCode = "200", description = "Person wurde geloescht")
    @ApiResponse(responseCode = "404", description = "Person wurde nicht gefunden")
    @ApiResponse(responseCode = "400", description = "Bad Request" )
    @ApiResponse(responseCode = "500", description = "Interner Serverfehler")
    @DeleteMapping(path="/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if(id.startsWith("1"))
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.notFound().build();
    }


    @ApiResponse(responseCode = "200", description = "Person wurde geändert")
    @ApiResponse(responseCode = "201", description = "Person wurde erstellt")
    @ApiResponse(responseCode = "400", description = "Bad Request" )
    @ApiResponse(responseCode = "500", description = "Interner Serverfehler")

    @PutMapping(path="", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveOrUpdateIdempotent(@Valid @RequestBody PersonDTO dto) {
        System.out.println(dto + " wurde gespeichert");
        if(dto.getId().startsWith("1"))
            return ResponseEntity.status(HttpStatus.CREATED).build();
        else
            return ResponseEntity.ok().build();
    }
//    @PostMapping(path="", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Void> saveOrUpdateNichtIdempotent(@RequestBody PersonDTO dto, UriComponentsBuilder builder) {
//        dto.setId(UUID.randomUUID().toString());
//        System.out.println(dto + " wurde gespeichert");
//
//        UriComponents uriComponents = builder.path("/v1/personen/{id}").buildAndExpand(dto.getId());
//
//        if(dto.getId().startsWith("1"))
//            return ResponseEntity.created(uriComponents.toUri()).build();
//        else
//            return ResponseEntity.ok().header("location",uriComponents.toUri().toString()).build();
//    }

    @PostMapping(path="/demo/to-upper", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonDTO> toUpper(@RequestBody PersonDTO dto) {
        dto.setVorname(dto.getVorname().toUpperCase());
        dto.setNachname(dto.getNachname().toUpperCase());
        return ResponseEntity.ok(dto);
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
