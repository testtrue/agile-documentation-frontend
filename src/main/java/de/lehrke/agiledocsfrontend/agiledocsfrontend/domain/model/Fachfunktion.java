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
    private String name;
    private String beschreibung;
    private List<Akzeptanzkriterium> akzeptanzkriterien;
    private List<String> tags;

}
