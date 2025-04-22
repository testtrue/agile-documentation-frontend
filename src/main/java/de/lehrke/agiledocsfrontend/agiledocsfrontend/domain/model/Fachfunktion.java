package de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.List;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
public class Fachfunktion {
    private FachfunktionId id;
    private Projekt projekt;
    private String name;
    private String kurzbeschreibung;
    private List<Akzeptanzkriterium> akzeptanzkriterien;
    private List<String> tags;

}
