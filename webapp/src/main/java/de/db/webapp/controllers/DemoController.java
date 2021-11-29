package de.db.webapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo") // Virtueller Ordner
public class DemoController {

    @GetMapping(path="/gruss")
    public String gruss() {
        return "Hallo Rest";
    }
}
