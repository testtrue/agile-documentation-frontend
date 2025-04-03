package de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.router.Route;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.logic.FachfunktionRepository;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.Fachfunktion;

@Route("")
public class FachfunktionListView extends Main {

    Grid<Fachfunktion> fachfunktionGrid;

    public FachfunktionListView(FachfunktionRepository repository) {
        fachfunktionGrid = new Grid<>(Fachfunktion.class, false);
        fachfunktionGrid.addColumn(Fachfunktion::getId).setHeader("Id");
        fachfunktionGrid.addColumn(Fachfunktion::getName).setHeader("Name");
        fachfunktionGrid.addColumn(Fachfunktion::getTags).setHeader("Tags");
        fachfunktionGrid.setItems(repository.findFachfunktionen());
        this.add(fachfunktionGrid);
    }
}
