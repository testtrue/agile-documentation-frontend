package de.lehrke.agiledocsfrontend.agiledocsfrontend.persistence.jpa;

import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.logic.ProjektRepository;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.Projekt;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Collectors;

public interface H2ProjRepository extends ProjektRepository, JpaRepository<ProjektDbo, String> {

    @Override
    default List<Projekt> findAllProjekte(){
        return this.findAll(Sort.by("name"))
                .stream()
                .map(p -> new Projekt().withId(p.getId()).withName(p.getName()).withLink(p.getLink()))
                .collect(Collectors.toList());
    }
}
