package de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.logic;

import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.Projekt;

import java.util.List;

public interface ProjektRepository {
    List<Projekt> findAllProjekte();
}
