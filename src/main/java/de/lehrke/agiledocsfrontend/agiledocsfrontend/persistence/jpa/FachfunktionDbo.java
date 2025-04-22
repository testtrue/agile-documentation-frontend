package de.lehrke.agiledocsfrontend.agiledocsfrontend.persistence.jpa;

import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.Fachfunktion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FachfunktionDbo {

    @Id
    private String id;

    @Column(length = 320000)
    @Convert(converter = FachfunktionConverter.class)
    private Fachfunktion fachfunktion;


    public static FachfunktionDbo toDbo(Fachfunktion fachfunktion) {
        FachfunktionDbo dbo = new FachfunktionDbo();
        dbo.setId(fachfunktion.getId().id());
        dbo.setFachfunktion(fachfunktion);
        return dbo;
    }

}
