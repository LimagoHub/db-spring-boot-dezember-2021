package de.db.webapp.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlRootElement
public class PersonDTO {

    @NotNull
    @Size(min = 36, max = 36)
    private String id;
    @NotNull
    @Size(min = 2, max = 30)
    private String vorname;
    @NotNull
    @Size(min = 2, max = 30)
    private String nachname;
    @Size(min = 5, max = 6)
    private String plz;
    @Size(min = 2, max = 30)
    private String ort;
    @Size(min = 2, max = 30)
    private String strasse;

}
