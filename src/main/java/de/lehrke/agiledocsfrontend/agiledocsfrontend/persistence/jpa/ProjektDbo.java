package de.lehrke.agiledocsfrontend.agiledocsfrontend.persistence.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProjektDbo {

    @Id
    private String id;

    private String name;
    private String link;
}
