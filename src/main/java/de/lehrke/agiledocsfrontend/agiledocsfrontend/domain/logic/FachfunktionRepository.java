package de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.logic;

import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.Fachfunktion;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.FachfunktionId;

import java.util.List;
import java.util.Optional;

public interface FachfunktionRepository {

    int findLastIdByProjectId(String projectId);
    Fachfunktion saveFachfunktion(Fachfunktion fachfunktion);

    Optional<Fachfunktion> findFachfunktionById(FachfunktionId id);

    void deleteFachfunktionById(FachfunktionId id);

    List<Fachfunktion> findFachfunktionen();
}
