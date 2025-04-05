package de.lehrke.agiledocsfrontend.agiledocsfrontend.persistence.jpa;

import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.logic.FachfunktionRepository;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.Fachfunktion;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.FachfunktionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface H2Repository extends JpaRepository<FachfunktionDbo, String>, FachfunktionRepository {
    default int findLastIdByProjectId(String projectId) {
        return this.findByIdContains(projectId).size();
    }

    default List<Fachfunktion> findFachfunktionen() {
        return this.findAll().stream().map(FachfunktionDbo::getFachfunktion).toList();
    }

    default Fachfunktion saveFachfunktion(Fachfunktion fachfunktion) {
        return this.save(FachfunktionDbo.toDbo(fachfunktion)).getFachfunktion();
    }

    default Optional<Fachfunktion> findFachfunktionById(FachfunktionId id) {
        return this.findById(id.id()).map(FachfunktionDbo::getFachfunktion);
    }

    List<FachfunktionDbo> findByIdContains(String projectId);
}
