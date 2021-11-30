package de.db.webapp.controllers;

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
public class PersonenController {

    private final PersonenService service;
    private final PersonDTOMapper mapper;


    @Operation(summary = "Suchen Sie eine Person über ihre ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person wurde gefunden", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PersonDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Falsche ID gesendet", content = @Content),
            @ApiResponse(responseCode = "404", description = "Person wurde nicht gefunden", content = @Content) ,
            @ApiResponse(responseCode = "500", description = "Interner Serverfehler", content = @Content)
        })

    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonDTO> findPersonByID(@Parameter(description = "id der gesuchten Person")  @PathVariable String id) throws PersonenServiceException{
        return ResponseEntity.of(service.findePersonNachId(id).map(mapper::convert));
    }



    @Operation(summary = "Suche alle Personen")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personenliste wurde erstellt", content = { @Content(mediaType = "application/json") }),
           // @ApiResponse(responseCode = "400", description = "Falsche ID gesendet", content = @Content),
           // @ApiResponse(responseCode = "404", description = "Person wurde nicht gefunden", content = @Content) ,
            @ApiResponse(responseCode = "500", description = "Interner Serverfehler", content = @Content)
    })
    @GetMapping(path="", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<PersonDTO>> findAll(
            @Parameter(description = "Vorname der Person")
            @RequestParam(required = false, defaultValue = "") String vorname,
            @Parameter(description = "Nachname der Person")
            @RequestParam(required = false, defaultValue = "") String nachname
    )  throws PersonenServiceException {


        return ResponseEntity.ok(mapper.convert(service.findeAlle()));
    }

    @ApiResponse(responseCode = "200", description = "Person wurde geloescht")
    @ApiResponse(responseCode = "404", description = "Person wurde nicht gefunden")
    @ApiResponse(responseCode = "400", description = "Bad Request" )
    @ApiResponse(responseCode = "500", description = "Interner Serverfehler")
    @DeleteMapping(path="/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id)  throws PersonenServiceException{
        if(service.loeschen(id))
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.notFound().build();
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

        if(service.speichernOderAendern(mapper.convert(dto)))
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.status(HttpStatus.CREATED).build();
    }
//NOSONAR    @PostMapping(path="", consumes = MediaType.APPLICATION_JSON_VALUE)
//NOSONAR    public ResponseEntity<Void> saveOrUpdateNichtIdempotent(@RequestBody PersonDTO dto, UriComponentsBuilder builder) {
//NOSONAR        dto.setId(UUID.randomUUID().toString());
//NOSONAR        System.out.println(dto + " wurde gespeichert");
//NOSONAR
//NOSONAR        UriComponents uriComponents = builder.path("/v1/personen/{id}").buildAndExpand(dto.getId());
//NOSONAR
//NOSONAR        if(dto.getId().startsWith("1"))
//NOSONAR            return ResponseEntity.created(uriComponents.toUri()).build();
//NOSONAR        else
//NOSONAR            return ResponseEntity.ok().header("location",uriComponents.toUri().toString()).build();
//NOSONAR    }

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
