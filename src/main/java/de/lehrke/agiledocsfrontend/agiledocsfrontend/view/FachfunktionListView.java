package de.lehrke.agiledocsfrontend.agiledocsfrontend.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.router.Route;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.logic.FachfunktionRepository;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.Fachfunktion;

@Route("")
public class FachfunktionListView extends Main {

    Grid<Fachfunktion> fachfunktionGrid;

    public FachfunktionListView(FachfunktionRepository repository) {
        fachfunktionGrid = new Grid<>(Fachfunktion.class, false);
        fachfunktionGrid.addColumn(f -> f.getId().id()).setHeader("Id").setSortable(true);
        fachfunktionGrid.addColumn(Fachfunktion::getName).setHeader("Name");
        fachfunktionGrid.addColumn(createStatusComponentRenderer()).setHeader("Tags");
        fachfunktionGrid.setSizeFull();
        fachfunktionGrid.setAllRowsVisible(true);

        GridListDataView<Fachfunktion> gridListDataView = fachfunktionGrid.setItems(repository.findFachfunktionen());
        TextField searchField = new TextField();
        searchField.setWidth("50%");
        searchField.setPlaceholder("Search");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> gridListDataView.refreshAll());


        gridListDataView.addFilter(fachfunktion -> {
            String searchTerm = searchField.getValue().trim();

            if (searchTerm.isEmpty())
                return true;

            boolean matchesId = matchesTerm(fachfunktion.getId().id(),
                    searchTerm);
            boolean matchesName = matchesTerm(fachfunktion.getName(), searchTerm);
            boolean matchesBeschreibung = matchesTerm(fachfunktion.getBeschreibung(), searchTerm);
            boolean matchesTag = fachfunktion.getTags().stream().anyMatch(t -> matchesTerm(t, searchTerm));

            return matchesId || matchesName || matchesBeschreibung || matchesTag;
        });

        HorizontalLayout verticalLayout = new HorizontalLayout();

        verticalLayout.add(searchField);

        this.add(verticalLayout);
        this.add(fachfunktionGrid);
    }

    private boolean matchesTerm(String value, String searchTerm) {
        return value.toLowerCase().contains(searchTerm.toLowerCase());
    }

    private static final SerializableBiConsumer<Div, Fachfunktion> statusComponentUpdater = (
            span, fachfunktion) -> {
        fachfunktion.getTags().forEach(t -> {Span s = new Span(t);
            s.getStyle().set("background-color", "lightgray");
            s.getStyle().set("margin", "1rem");
            span.add(s);
        });
    };

    private static ComponentRenderer<Div, Fachfunktion> createStatusComponentRenderer() {
        return new ComponentRenderer<>(Div::new, statusComponentUpdater);
    }
}
