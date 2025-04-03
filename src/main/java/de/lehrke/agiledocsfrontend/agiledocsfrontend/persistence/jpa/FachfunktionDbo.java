package de.lehrke.agiledocsfrontend.agiledocsfrontend.persistence.jpa;

import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.Fachfunktion;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.FachfunktionId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class FachfunktionDbo extends Fachfunktion {

    @Id
    public String getPrimaryKey() {
        return super.getId().id();
    }

    public void setPrimaryKey(String primaryKey) {
        super.setId(new FachfunktionId(primaryKey));
    }


}
