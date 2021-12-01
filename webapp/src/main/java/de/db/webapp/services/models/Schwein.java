package de.db.webapp.services.models;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Builder
public class Schwein {

    @Setter(AccessLevel.PRIVATE)
    private String id;

    @Setter(AccessLevel.PRIVATE)
    private String name;

    @Setter(AccessLevel.PRIVATE)
    private int gewicht;

    public void fuettere() {
        setGewicht(getGewicht() + 1);
    }
    public void taufe(String name) {
        if(name == null|| "elsa".equalsIgnoreCase(name))  throw new IllegalArgumentException("Unerlaubter Name");
        setName(name);
    }
}
