package de.lehrke.agiledocsfrontend.agiledocsfrontend.view;

import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.logic.FachfunktionRepository;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.logic.ProjektRepository;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Controller()
public class FachfunktionListView {

    private final FachfunktionRepository repository;
    private final ProjektRepository projektRepository;

//    public FachfunktionListView() {
//        fachfunktionGrid = new Grid<>(Fachfunktion.class, false);
//        fachfunktionGrid.addColumn(f -> f.getId().id()).setHeader("Id").setSortable(true);
//        fachfunktionGrid.addColumn(Fachfunktion::getName).setHeader("Name");
//        fachfunktionGrid.addColumn(createStatusComponentRenderer()).setHeader("Tags");
//        fachfunktionGrid.addColumn(createButtonRenderer());
//        fachfunktionGrid.setSizeFull();
//        fachfunktionGrid.setAllRowsVisible(true);
//
//        GridListDataView<Fachfunktion> gridListDataView = fachfunktionGrid.setItems(repository.findFachfunktionen());
//        TextField searchField = new TextField();
//        searchField.setWidth("50%");
//        searchField.setPlaceholder("Search");
//        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
//        searchField.setValueChangeMode(ValueChangeMode.EAGER);
//        searchField.addValueChangeListener(e -> gridListDataView.refreshAll());
//
//
//        gridListDataView.addFilter(fachfunktion -> {
//            String searchTerm = searchField.getValue().trim();
//
//            if (searchTerm.isEmpty())
//                return true;
//
//            boolean matchesId = matchesTerm(fachfunktion.getId().id(),
//                    searchTerm);
//            boolean matchesName = matchesTerm(fachfunktion.getName(), searchTerm);
//            boolean matchesBeschreibung = matchesTerm(fachfunktion.getKurzbeschreibung(), searchTerm);
//            boolean matchesTag = fachfunktion.getTags().stream().anyMatch(t -> matchesTerm(t, searchTerm));
//
//            return matchesId || matchesName || matchesBeschreibung || matchesTag;
//        });
//
//        HorizontalLayout verticalLayout = new HorizontalLayout();
//
//        Button newFF = new Button("Neu", new Icon(VaadinIcon.PLUS));
//        newFF.addClickListener(e -> newFF.getUI().ifPresent(
//                ui -> ui.navigate(FachfunktionEditorView.class)
//        ));
//        Button importExport = new Button("Import/Export", new Icon(VaadinIcon.FILE));
//        importExport.addClickListener(e -> importExport.getUI().ifPresent(
//                ui -> ui.navigate(FileView.class)
//        ));
//
//        verticalLayout.add(searchField, newFF, importExport);
//
//        this.add(verticalLayout);
//        this.add(fachfunktionGrid);
//    }

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("index");
    }

    @GetMapping("/fachfunktionen")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("fachfunktionen");

        mav.addObject("fachfunktionen", this.repository.findFachfunktionen());

        return mav;
    }

    private boolean matchesTerm(String value, String searchTerm) {
        return value.toLowerCase().contains(searchTerm.toLowerCase());
    }

//    private static final SerializableBiConsumer<Div, Fachfunktion> tagComponentList = (
//            span, fachfunktion) -> {
//        if (fachfunktion.getTags() == null) { return ;}
//
//        fachfunktion.getTags().forEach(t -> {Span s = new Span(t);
//            s.getElement().getThemeList().add("badge pill");
//            span.add(s);
//        });
//    };
//
//    private static ComponentRenderer<Div, Fachfunktion> createStatusComponentRenderer() {
//        return new ComponentRenderer<>(Div::new, tagComponentList);
//    }
//
//    private static final SerializableBiConsumer<Div, Fachfunktion> editCol = (d, f) -> {
//        Button b = new Button("Edit", new Icon(VaadinIcon.EDIT));
//        b.addClickListener(e -> b.getUI().ifPresent(
//                ui -> ui.navigate(FachfunktionEditorView.class, f.getId().id())
//        ));
//        d.add(b);
//    };
//
//    private static ComponentRenderer<Div, Fachfunktion> createButtonRenderer() {
//        return new ComponentRenderer<>(Div::new, editCol);
//    }

}
