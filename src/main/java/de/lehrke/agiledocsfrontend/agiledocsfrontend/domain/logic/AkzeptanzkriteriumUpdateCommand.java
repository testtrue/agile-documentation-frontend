package de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.logic;

import lombok.*;

@With
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
public class AkzeptanzkriteriumUpdateCommand {
    private String id;
    private String beschreibung;
    private Aktion aktion;

    public String id() {
        return id;
    }
    public String beschreibung() {
        return beschreibung;
    }

    public Aktion aktion() {
        return aktion;
    }
}
