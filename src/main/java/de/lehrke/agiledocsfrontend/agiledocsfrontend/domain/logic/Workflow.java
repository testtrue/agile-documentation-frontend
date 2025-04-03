package de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.logic;

import com.leakyabstractions.result.api.Result;
import com.leakyabstractions.result.core.Results;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.Fachfunktion;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.FachfunktionId;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class Workflow {

    private final FachfunktionRepository fachfunktionRepository;

    public Result<Fachfunktion, String> neueFachfunktion(String projectId) {
        final FachfunktionId latestId = this.findLatestId(projectId);
        return Results.success(new Fachfunktion().withId(latestId));
    }

    private FachfunktionId findLatestId(String projectId) {
        int highestNumer = this.fachfunktionRepository.findLastIdByProjectId(projectId);

        return FachfunktionId.of(projectId, String.valueOf(highestNumer));
    }

    public Result<Fachfunktion, String> speicherNeueFachfunktion(Fachfunktion fachfunktion) {
        if (fachfunktion == null) {
            return Results.failure("Keine Fachfunktion angegeben");
        }

        if (fachfunktion.getId() == null) {
            return Results.failure("Fachfunktion hat keine ID erhalten");
        }

        if (fachfunktion.getName() == null) {
            return Results.failure("Fachfunktion ohne Name kann nicht gespeichert werden");
        }

        if (fachfunktion.getAkzeptanzkriterien() == null || fachfunktion.getAkzeptanzkriterien().isEmpty() ) {
            return Results.failure("Ohne Akzeptanzkriterien kann nicht gespeichert");
        }

        if (fachfunktion.getBeschreibung() == null || fachfunktion.getBeschreibung().isEmpty() ) {
            return Results.failure("Ohne Beschreibung kann nicht gespeichert");
        }

        this.fachfunktionRepository.save(fachfunktion);

        return Results.success(fachfunktion);
    }

    public Result<Fachfunktion, String> updateFachfunktion(FachfunktionUpdateCommand fachfunktionUpdateCommand) {


    }
}
