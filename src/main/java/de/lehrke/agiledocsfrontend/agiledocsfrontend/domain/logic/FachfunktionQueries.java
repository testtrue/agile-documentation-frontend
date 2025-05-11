package de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.logic;

import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.Projekt;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@Data
public class FachfunktionQueries {

    private final FachfunktionRepository fachfunktionRepository;
    private final ProjektRepository projektRepository;

    public List<String> getTags() {
        return this.fachfunktionRepository.findFachfunktionen().stream().flatMap(fachfunktion -> fachfunktion.getTags().stream()).collect(Collectors.toSet()).stream().toList();
    }

    public List<Projekt> getProjects() {
        return this.projektRepository.findAllProjekte();
    }
}
