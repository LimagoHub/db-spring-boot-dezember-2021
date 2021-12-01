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
@Table(name ="tbl_schweine")
public class SchweinEntity {

    @Id // Pflicht
    @Column(nullable = false, length = 36)
    private String id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false)
    private int gewicht;

    //NOSONAR @Version
    //NOSONAR private long version;
}