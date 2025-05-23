package de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.logic;

import com.leakyabstractions.result.api.Result;
import com.leakyabstractions.result.core.Results;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.Akzeptanzkriterium;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.Fachfunktion;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.FachfunktionId;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.Projekt;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Consumer;

@Data
@Service
public class Workflow {

    private final FachfunktionRepository fachfunktionRepository;

    public Result<Fachfunktion, String> neueFachfunktion(String projectId) {
        final FachfunktionId latestId = this.findLatestId(new Projekt().withId(projectId));
        return Results.success(new Fachfunktion().withId(latestId).withAkzeptanzkriterien(new ArrayList<>()).withTags(new ArrayList<>()));
    }

    public Result<Fachfunktion, String> neueFachfunktion(Projekt projectId) {
        final FachfunktionId latestId = this.findLatestId(projectId);
        return Results.success(new Fachfunktion().withId(latestId).withAkzeptanzkriterien(new ArrayList<>()));
    }

    private FachfunktionId findLatestId(Projekt projectId) {
        int highestNumer = this.fachfunktionRepository.findLastIdByProjectId(projectId.getId());

        return FachfunktionId.of(projectId.getId(), String.valueOf(highestNumer + 1));
    }

    public Result<Fachfunktion, String> speicherNeueFachfunktion(Fachfunktion fachfunktion) {
        if (fachfunktion == null) {
            return Results.failure("Keine Fachfunktion angegeben");
        }

        if (!StringUtils.hasText(fachfunktion.getId().id())) {
            return Results.failure("Fachfunktion hat keine ID erhalten");
        }

        if (!StringUtils.hasText(fachfunktion.getName())) {
            return Results.failure("Fachfunktion ohne Name kann nicht gespeichert werden");
        }

        if (fachfunktion.getAkzeptanzkriterien() == null || fachfunktion.getAkzeptanzkriterien().isEmpty() ) {
            return Results.failure("Ohne Akzeptanzkriterien kann nicht gespeichert werden");
        }

        if (fachfunktion.getKurzbeschreibung() == null || fachfunktion.getKurzbeschreibung().isEmpty() ) {
            return Results.failure("Ohne Beschreibung kann nicht gespeichert werden");
        }

        this.fachfunktionRepository.saveFachfunktion(fachfunktion);

        return Results.success(fachfunktion);
    }

    public Result<Fachfunktion, String> updateFachfunktion(FachfunktionUpdateCommand fachfunktionUpdateCommand) {

        Optional<Fachfunktion> optionalFachfunktion = this.fachfunktionRepository.findFachfunktionById(fachfunktionUpdateCommand.id());
        if (optionalFachfunktion.isEmpty()) {
            return Results.failure("Keine Fachfunktion angegeben");
        }
        Fachfunktion fachfunktion = optionalFachfunktion.get();

        if (fachfunktionUpdateCommand.aktion() == Aktion.DELETE) {
            this.fachfunktionRepository.deleteFachfunktionById(fachfunktionUpdateCommand.id());
        }

        if (fachfunktionUpdateCommand.aktion() == Aktion.NOTHING || fachfunktionUpdateCommand.aktion() == Aktion.DELETE) {
            return Results.success(fachfunktion);
        }

        fachfunktionUpdateCommand.tags().forEach(
                processTagUpdates(fachfunktion)
        );

        fachfunktionUpdateCommand.akzeptanzkriterien().forEach(
                processAkzeptanzkriterienUpdates(fachfunktion)
        );

        if (!fachfunktion.getKurzbeschreibung().equals(fachfunktionUpdateCommand.beschreibung())) {
            fachfunktion.setKurzbeschreibung(fachfunktionUpdateCommand.beschreibung());
        }

        if (!fachfunktion.getName().equals(fachfunktionUpdateCommand.name())) {
            fachfunktion.setName(fachfunktionUpdateCommand.name());
        }

        this.fachfunktionRepository.saveFachfunktion(fachfunktion);
        return Results.success(fachfunktion);
    }

    private static Consumer<TagUpdateCommand> processTagUpdates(Fachfunktion fachfunktion) {
        return tagUpdateCommand -> {
            if (tagUpdateCommand.aktion() == Aktion.DELETE) {
                fachfunktion.getTags().remove(tagUpdateCommand.tag());
            }

            if (tagUpdateCommand.aktion() == Aktion.ADD) {
                fachfunktion.getTags().add(tagUpdateCommand.tag());
            }
        };
    }

    private static Consumer<AkzeptanzkriteriumUpdateCommand> processAkzeptanzkriterienUpdates(Fachfunktion fachfunktion) {
        return akzeptanzkriterienCommand -> {
            if (akzeptanzkriterienCommand.aktion() == Aktion.DELETE) {
                fachfunktion.setAkzeptanzkriterien(fachfunktion.getAkzeptanzkriterien().stream().filter(a -> !a.getId().equals(akzeptanzkriterienCommand.id())).toList());
            }
            if (akzeptanzkriterienCommand.aktion() == Aktion.UPDATE) {
                Optional<Akzeptanzkriterium> akzeptanzkriterium = fachfunktion.getAkzeptanzkriterien().stream().filter(a -> a.getId().equals(akzeptanzkriterienCommand.id())).findFirst();
                akzeptanzkriterium.ifPresent(
                        a -> a.setBeschreibung(akzeptanzkriterienCommand.kurzbeschreibung())
                );
            }
            if (akzeptanzkriterienCommand.aktion() == Aktion.ADD) {
                fachfunktion.getAkzeptanzkriterien().add(new Akzeptanzkriterium().withId(akzeptanzkriterienCommand.id()).withBeschreibung(akzeptanzkriterienCommand.kurzbeschreibung()));
            }
        };
    }
}
