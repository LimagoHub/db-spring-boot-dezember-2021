package de.db.webapp.repositories.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity // Pflicht
@Table(name ="tbl_personen")

@NamedQuery(name = "PersonEntity.holeAllePersonenMitNachname", query = "select p from PersonEntity p where p.nachname like :nachname")
public class PersonEntity {

    @Id // Pflicht
    @Column(nullable = false, length = 36)
    private String id;

    @Column(nullable = false, length = 30)
    private String vorname;

    @Column(nullable = false, length = 30)
    private String nachname;



}
