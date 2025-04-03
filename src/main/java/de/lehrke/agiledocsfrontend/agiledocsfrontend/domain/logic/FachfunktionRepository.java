package de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.logic;

import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.Fachfunktion;

public interface FachfunktionRepository {

    int findLastIdByProjectId(String projectId);
    Fachfunktion save(Fachfunktion fachfunktion);
}
