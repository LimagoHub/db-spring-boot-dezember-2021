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
public class PersonenQueryController {

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
            @ApiResponse(responseCode = "500", description = "Interner Serverfehler", content = @Content)
    })
    @GetMapping(path="", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<PersonDTO>> findAll(
//NOSONAR            @Parameter(description = "Vorname der Person")
//NOSONAR            @RequestParam(required = false, defaultValue = "") String vorname,
//NOSONAR            @Parameter(description = "Nachname der Person")
//NOSONAR            @RequestParam(required = false, defaultValue = "") String nachname
    )  throws PersonenServiceException {


        return ResponseEntity.ok(mapper.convert(service.findeAlle()));
    }





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
