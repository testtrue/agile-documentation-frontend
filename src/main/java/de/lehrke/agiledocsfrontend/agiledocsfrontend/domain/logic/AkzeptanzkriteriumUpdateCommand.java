package de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.logic;

import lombok.*;

@With
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
public class AkzeptanzkriteriumUpdateCommand {
    private String id;
    private String kurzbeschreibung;
    private Aktion aktion;

    public String id() {
        return id;
    }
    public String kurzbeschreibung() {
        return kurzbeschreibung;
    }

    public Aktion aktion() {
        return aktion;
    }
}
