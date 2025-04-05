package de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
public class Projekt {

    private String id;
    private String name;
    private String link;
}
