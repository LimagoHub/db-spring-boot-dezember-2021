package de.db.webapp.controllers;

import de.db.webapp.application.PersonenAdapter;
import de.db.webapp.application.events.PersonDeletedEvent;
import de.db.webapp.application.events.PersonInsertedEvent;
import de.db.webapp.controllers.dtos.PersonDTO;
import de.db.webapp.controllers.mappers.PersonDTOMapper;
import de.db.webapp.services.PersonenService;
import de.db.webapp.services.PersonenServiceException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/v1/personen")
@AllArgsConstructor
public class PersonenCommandController {

   private PersonenAdapter adapter;


    @ApiResponse(responseCode = "200", description = "Person wurde geloescht")
    @ApiResponse(responseCode = "404", description = "Person wurde nicht gefunden")
    @ApiResponse(responseCode = "400", description = "Bad Request" )
    @ApiResponse(responseCode = "500", description = "Interner Serverfehler")
    @DeleteMapping(path="/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id)  throws PersonenServiceException{
        PersonDeletedEvent event = PersonDeletedEvent.builder().id(id).build();
        adapter.handle(event);
        return ResponseEntity.ok().build();
     }



    @Operation(summary = "Ändern oder Speichern einer Person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person wurde geändert"),
            @ApiResponse(responseCode = "201", description = "Person wurde erstellt"),
            @ApiResponse(responseCode = "400", description = "Bad Request" ),
            @ApiResponse(responseCode = "500", description = "Interner Serverfehler")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Details des zu speichernden Person",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = PersonDTO.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "Ein minimales Beispiel nur mit Pflichtfeldern",
                                    value = "{\n" +
                                            "  \"id\": \"cc0b3c5e-8d33-4e49-ad58-cb8a79117dd2\",\n" +
                                            "  \"vorname\": \"John\",\n" +
                                            "  \"nachname\": \"Doe\"\n" +
                                            "}",
                                    summary = "Minimal request"),
                            @ExampleObject(
                                    name = "Ein Beispiel mit allen Feldern",
                                    value = "{\n" +
                                            "  \"id\": \"cc0b3c5e-8d33-4e49-ad58-cb8a79117dd2\",\n" +
                                            "  \"vorname\": \"John\",\n" +
                                            "  \"nachname\": \"Doe\",\n" +
                                            "  \"plz\": \"60000\",\n" +
                                            "  \"ort\": \"Frankfurt\",\n" +
                                            "  \"strasse\": \"Hessenring\"\n" +
                                            "}",
                                    summary = "Full request") }))

    @PutMapping(path="", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveOrUpdateIdempotent(@Valid @RequestBody PersonDTO dto)  throws PersonenServiceException{

            PersonInsertedEvent event = PersonInsertedEvent.builder().dto(dto).build();
            adapter.handle(event);
            return ResponseEntity.ok().build();


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
