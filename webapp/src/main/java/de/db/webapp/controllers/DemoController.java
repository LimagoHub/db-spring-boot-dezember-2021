package de.db.webapp.controllers;

import de.db.webapp.controllers.dtos.PersonDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import java.util.UUID;

@RestController
@RequestMapping("/demo") // Virtueller Ordner
//@RequestScope
//@SessionScope // nix good
public class DemoController {



    @GetMapping(path="/gruss")
    public String gruss() {
       return "Hallo Rest";
    }

    @GetMapping(path="/uuid")
    public String uuid() {
        return UUID.randomUUID().toString();
    }

    @GetMapping(path="/person", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<PersonDTO> person() {

        return ResponseEntity.notFound().build();
    }
}
