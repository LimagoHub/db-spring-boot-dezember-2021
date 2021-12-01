package de.db.webapp.controllers;

import de.db.webapp.controllers.dtos.SchweinDTO;
import de.db.webapp.controllers.mappers.SchweineDTOMapper;
import de.db.webapp.services.SchweineServiceException;
import de.db.webapp.services.SchweineService;
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
@RequestMapping("/v1/schweine")
@AllArgsConstructor
public class SchweineController {
    private final SchweineService service;
    private final SchweineDTOMapper mapper;


    @Operation(summary = "Suchen Sie ein Schwein über seine ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Schwein wurde gefunden", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = SchweinDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Falsche ID gesendet", content = @Content),
            @ApiResponse(responseCode = "404", description = "Schwein wurde nicht gefunden", content = @Content) ,
            @ApiResponse(responseCode = "500", description = "Interner Serverfehler", content = @Content)
    })

    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SchweinDTO> findSchweinByID(@Parameter(description = "id des gesuchten Schweins")  @PathVariable String id) throws SchweineServiceException {
        return ResponseEntity.of(service.findeSchweinNachId(id).map(mapper::convert));
    }



    @Operation(summary = "Suche alle Schweine")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Schweineliste wurde erstellt", content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Interner Serverfehler", content = @Content)
    })
    @GetMapping(path="", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<SchweinDTO>> findAll(
//NOSONAR            @Parameter(description = "Vorname der Schwein")
//NOSONAR            @RequestParam(required = false, defaultValue = "") String vorname,
//NOSONAR            @Parameter(description = "Nachname der Schwein")
//NOSONAR            @RequestParam(required = false, defaultValue = "") String nachname
    )  throws SchweineServiceException {


        return ResponseEntity.ok(mapper.convert(service.findeAlle()));
    }

    @ApiResponse(responseCode = "200", description = "Schwein wurde geloescht")
    @ApiResponse(responseCode = "404", description = "Schwein wurde nicht gefunden")
    @ApiResponse(responseCode = "400", description = "Bad Request" )
    @ApiResponse(responseCode = "500", description = "Interner Serverfehler")
    @DeleteMapping(path="/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id)  throws SchweineServiceException{
        if(service.loeschen(id))
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.notFound().build();
    }



    @Operation(summary = "Ändern oder Speichern eines Schweins")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Schwein wurde geändert"),
            @ApiResponse(responseCode = "201", description = "Schwein wurde erstellt"),
            @ApiResponse(responseCode = "400", description = "Bad Request" ),
            @ApiResponse(responseCode = "500", description = "Interner Serverfehler")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Details des zu speichernden Schweins",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = SchweinDTO.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "Ein minimales Beispiel nur mit Pflichtfeldern",
                                    value = "{\n" +
                                            "  \"id\": \"cc0b3c5e-8d33-4e49-ad58-cb8a79117dd2\",\n" +
                                            "  \"name\": \"Miss Piggy\",\n" +
                                            "  \"gewicht\": \"10\"\n" +
                                            "}",
                                    summary = "Minimal request"),
                            }))

    @PutMapping(path="", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveOrUpdateIdempotent(@Valid @RequestBody SchweinDTO dto)  throws SchweineServiceException {

        if(service.speichernOderAendern(mapper.convert(dto)))
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PostMapping(path="/{id}/fuettere")
    public ResponseEntity<Void> feed(@PathVariable String id) throws SchweineServiceException{
        if(service.fuettere(id))
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.notFound().build();

    }

    @PutMapping(path="/{id}/taufe")
    public ResponseEntity<Void> baptize(@PathVariable String id, @RequestParam(required = true) String name) throws SchweineServiceException{
        if(service.taufe(id,name))
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.notFound().build();

    }
}
