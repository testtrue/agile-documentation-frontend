package de.lehrke.agiledocsfrontend.agiledocsfrontend.view;

import com.leakyabstractions.result.api.Result;
//import com.vaadin.flow.component.Component;
//import com.vaadin.flow.component.Focusable;
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.button.ButtonVariant;
//import com.vaadin.flow.component.combobox.ComboBox;
//import com.vaadin.flow.component.formlayout.FormLayout;
//import com.vaadin.flow.component.grid.Grid;
//import com.vaadin.flow.component.grid.editor.Editor;
//import com.vaadin.flow.component.html.Div;
//import com.vaadin.flow.component.html.Span;
//import com.vaadin.flow.component.icon.Icon;
//import com.vaadin.flow.component.icon.VaadinIcon;
//import com.vaadin.flow.component.notification.Notification;
//import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.component.textfield.TextArea;
//import com.vaadin.flow.component.textfield.TextField;
//import com.vaadin.flow.data.binder.Binder;
//import com.vaadin.flow.data.renderer.ComponentRenderer;
//import com.vaadin.flow.router.*;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.logic.*;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.Akzeptanzkriterium;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.Fachfunktion;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.FachfunktionId;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
//@Route("edit")
public class FachfunktionEditorView {

    private static final String ID_PLACEHOLDER = "--plus--";
    private final FachfunktionRepository repository;
    private final FachfunktionQueries queries;
    private final Workflow workflow;
    private Fachfunktion fachfunktion;
    private FachfunktionUpdateCommand fachfunktionUpdateCommand;
    //private final Binder<Fachfunktion> binder;
    private boolean isNew = true;
    //private final TextField id;
//    private final Grid<AkzeptanzkriteriumUpdateCommand> akzeptanzkriteriumGrid;
//    private Editor<AkzeptanzkriteriumUpdateCommand> akzeptanzkriteriumEditor;
//    private final HorizontalLayout badges;

    public FachfunktionEditorView(FachfunktionRepository repository, Workflow workflow, FachfunktionQueries queries) {
        this.repository = repository;
        this.workflow = workflow;
        this.queries = queries;

//        Button back = new Button("Zurück", new Icon(VaadinIcon.ARROW_LEFT));
//        back.addClickListener(e -> back.getUI().ifPresent(
//                ui -> ui.navigate(FachfunktionListView.class)
//        ));
//
//        FormLayout formLayout = new FormLayout();
//        formLayout.setResponsiveSteps(
//                // Use one column by default
//                new FormLayout.ResponsiveStep("0", 1));
//        this.id = new TextField("ID");
//        this.id.setReadOnly(true);
//        TextField name = new TextField("Name");
//        TextArea beschreibung = new TextArea("Beschreibung");
//        beschreibung.setMinRows(4);
//        this.akzeptanzkriteriumGrid = new Grid<>(AkzeptanzkriteriumUpdateCommand.class, false);
//        Grid.Column<AkzeptanzkriteriumUpdateCommand> idCol = this.akzeptanzkriteriumGrid.addColumn(new ComponentRenderer<>(
//                Div::new,
//                (d, f) -> {
//                    if (ID_PLACEHOLDER.equals(f.id())) {
//                        Button neuesAkzeptanzkriterium = new Button(new Icon(VaadinIcon.PLUS));
//                        neuesAkzeptanzkriterium.addClickListener((e) -> {
//                            AkzeptanzkriteriumUpdateCommand a = new AkzeptanzkriteriumUpdateCommand("", "", Aktion.ADD);
//                            this.fachfunktionUpdateCommand.akzeptanzkriterien().add(a);
//                            this.akzeptanzkriteriumGrid.setItems(this.fachfunktionUpdateCommand.akzeptanzkriterien());
//                            if (this.akzeptanzkriteriumEditor != null) {
//                                this.akzeptanzkriteriumEditor.closeEditor();
//                            }
//                            this.akzeptanzkriteriumEditor = this.akzeptanzkriteriumGrid.getEditor();
//                            this.akzeptanzkriteriumEditor.editItem(a);
//                        });
//                        d.add(neuesAkzeptanzkriterium);
//                    } else {
//                        d.add(f.id());
//                    }
//                }
//
//        )).setHeader("ID").setAutoWidth(true);
//        Grid.Column<AkzeptanzkriteriumUpdateCommand> beschreibungCol = this.akzeptanzkriteriumGrid.addColumn(AkzeptanzkriteriumUpdateCommand::kurzbeschreibung).setHeader("Beschreibung");
//
//        binder = new Binder<>(Fachfunktion.class);
//        binder.addValueChangeListener(e -> this.fachfunktionUpdateCommand = this.fachfunktionUpdateCommand.withAktion(Aktion.UPDATE));
//        binder.forField(name).bind("name");
//        binder.forField(beschreibung).bind("kurzbeschreibung");
//
//
//        Binder<AkzeptanzkriteriumUpdateCommand> akzeptanzkriteriumBinder = new Binder<>(AkzeptanzkriteriumUpdateCommand.class);
//        akzeptanzkriteriumBinder.addValueChangeListener(e -> this.fachfunktionUpdateCommand = this.fachfunktionUpdateCommand.withAktion(Aktion.UPDATE));
//        this.akzeptanzkriteriumGrid.getEditor().setBinder(akzeptanzkriteriumBinder);
//        TextField akzeptanzkriteriumId = new TextField();
//        akzeptanzkriteriumBinder.forField(akzeptanzkriteriumId).bind(AkzeptanzkriteriumUpdateCommand::getId, AkzeptanzkriteriumUpdateCommand::setId);
//        idCol.setEditorComponent(akzeptanzkriteriumId);
//
//        TextField akzeptanzkriteriumBeschreibung = new TextField();
//        akzeptanzkriteriumBinder.forField(akzeptanzkriteriumBeschreibung).bind(AkzeptanzkriteriumUpdateCommand::getKurzbeschreibung, AkzeptanzkriteriumUpdateCommand::setKurzbeschreibung);
//        beschreibungCol.setEditorComponent(akzeptanzkriteriumBeschreibung);
//
//        akzeptanzkriteriumGrid.addItemDoubleClickListener(e -> {
//            log.info("{}", e.getItem());
//            log.info("Double clicked");
//            if (this.akzeptanzkriteriumEditor != null) {
//                this.akzeptanzkriteriumEditor.closeEditor();
//            }
//            this.akzeptanzkriteriumEditor = this.akzeptanzkriteriumGrid.getEditor();
//            this.akzeptanzkriteriumEditor.editItem(e.getItem());
//            Component editorComponent = e.getColumn().getEditorComponent();
//            if (editorComponent instanceof Focusable<?>) {
//                ((Focusable<?>) editorComponent).focus();
//            }
//        });
//
//        this.akzeptanzkriteriumGrid.getEditor().addCloseListener(e -> {
//            log.info("{}", e.getItem());
//            log.info("Close editor");
//            e.getSource().save();
//            //e.getSource().closeEditor();
//        });
//
//        Button save = new Button("Speichern", new Icon(VaadinIcon.FILE));
//        save.addClickListener(e -> {
//            Result<Fachfunktion, String> r;
//           if (this.isNew) {
//               r = this.workflow.speicherNeueFachfunktion(this.fachfunktion
//                       .withAkzeptanzkriterien(this.fachfunktionUpdateCommand.akzeptanzkriterien().stream().filter(a -> !ID_PLACEHOLDER.equals(a.getId())).map(a -> new Akzeptanzkriterium(a.id(), a.kurzbeschreibung())).toList())
//                       .withTags(this.fachfunktionUpdateCommand.tags().stream().map(TagUpdateCommand::tag).toList())
//               );
//           } else {
//               r = this.workflow.updateFachfunktion(this.fachfunktionUpdateCommand
//                       .withBeschreibung(this.fachfunktion.getKurzbeschreibung())
//                       .withName(this.fachfunktion.getName()));
//           }
//
//            if (r.hasFailure() && r.getFailure().isPresent()) {
//                Notification
//                        .show(r.getFailure().get(), 10000, Notification.Position.TOP_END);
//            } else {
//                Notification
//                        .show("Erfolgreich gespeichert", 10000, Notification.Position.TOP_END);
//                e.getSource().getUI().ifPresent(
//                        ui -> ui.navigate(FachfunktionEditorView.class, this.fachfunktion.getId().id())
//                );
//            }
//
//        });
//        Button cancel = new Button("Abbrechen", new Icon(VaadinIcon.CLOSE));
//        cancel.addClickListener(e -> this.setParameter(null, this.fachfunktion.getId().id()));
//        Button back2 = new Button("Zurück", new Icon(VaadinIcon.ARROW_LEFT));
//        back2.addClickListener(e -> back2.getUI().ifPresent(
//                ui -> ui.navigate(FachfunktionListView.class)
//        ));
//
//        this.badges = new HorizontalLayout();
//        badges.getStyle().set("flex-wrap", "wrap");
//        ComboBox<String> comboBox = new ComboBox<>("Tags");
//        comboBox.setAllowCustomValue(true);
//        comboBox.addCustomValueSetListener(e -> {
//            String customValue = e.getDetail();
//            List<String> items = new ArrayList<>(this.queries.getTags());
//            items.add(customValue);
//            comboBox.setItems(items);
//            comboBox.setValue(customValue);
//            this.fachfunktionUpdateCommand.tags().add(new TagUpdateCommand(customValue, Aktion.ADD));
//            this.fachfunktionUpdateCommand = this.fachfunktionUpdateCommand.withAktion(Aktion.UPDATE);
//        });
//        comboBox.setItems(this.queries.getTags());
//        comboBox.addValueChangeListener(e -> {
//            Span filterBadge = createFilterBadge(e.getValue());
//            badges.add(filterBadge);
//        });
//        VerticalLayout vl = new VerticalLayout();
//        vl.add(comboBox, badges);
//
//        HorizontalLayout buttonGroup = new HorizontalLayout(save, cancel, back2);
//        formLayout.add(this.id, name, beschreibung, this.akzeptanzkriteriumGrid, vl ,buttonGroup);
//        this.add(back, formLayout);
    }

//    @Override
//    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String s) {
//        if (s == null) {
//            this.fachfunktion = workflow.neueFachfunktion("PROJ").getSuccess().get();
//        } else {
//            this.fachfunktion = repository.findFachfunktionById(new FachfunktionId(s)).get();
//        }
//        this.isNew = s == null;
//        this.id.setValue(this.fachfunktion.getId().id());
//
//        List<AkzeptanzkriteriumUpdateCommand> akzeptanzkriteriumUpdateCommandList = new ArrayList<>();
//        akzeptanzkriteriumUpdateCommandList.add(new AkzeptanzkriteriumUpdateCommand(ID_PLACEHOLDER, "Neues Akzeptanzkriterium", Aktion.NOTHING));
//        akzeptanzkriteriumUpdateCommandList.addAll(this.fachfunktion.getAkzeptanzkriterien().stream()
//                .map(a ->
//                        new AkzeptanzkriteriumUpdateCommand(a.getId(), a.getBeschreibung(), Aktion.NOTHING))
//                .toList());
//
//        this.fachfunktionUpdateCommand = new FachfunktionUpdateCommand(
//                                                    this.fachfunktion.getId(),
//                                                    this.fachfunktion.getName(),
//                                                    this.fachfunktion.getKurzbeschreibung(),
//                                                    akzeptanzkriteriumUpdateCommandList,
//                                                    new ArrayList<>(this.fachfunktion.getTags().stream()
//                                                            .map(t ->
//                                                                    new TagUpdateCommand(t, Aktion.NOTHING))
//                                                            .toList()),
//                                                    Aktion.NOTHING);
//
//        this.binder.setBean(fachfunktion);
//        this.akzeptanzkriteriumGrid.setItems(this.fachfunktionUpdateCommand.akzeptanzkriterien());
//        this.badges.getChildren().forEach(Component::removeFromParent);
//        this.fachfunktion.getTags().forEach(t -> this.badges.add(this.createFilterBadge(t)));
//    }

//    private Span createFilterBadge(String profession) {
//        Button clearButton = new Button(VaadinIcon.CLOSE_SMALL.create());
//        clearButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST,
//                ButtonVariant.LUMO_TERTIARY_INLINE);
//        clearButton.getStyle().set("margin-inline-start",
//                "var(--lumo-space-xs)");
//        // Accessible button name
//        clearButton.getElement().setAttribute("aria-label",
//                "Clear filter: " + profession);
//        // Tooltip
//        clearButton.getElement().setAttribute("title",
//                "Clear filter: " + profession);
//
//        Span badge = new Span(new Span(profession), clearButton);
//        badge.getElement().getThemeList().add("badge contrast pill");
//
//        // Add handler for removing the badge
//        clearButton.addClickListener(event -> {
//            badge.getElement().removeFromParent();
//            this.fachfunktionUpdateCommand.tags().replaceAll(e -> e.tag().equals(profession) ? new TagUpdateCommand(e.tag(), Aktion.DELETE) : e );
//            this.fachfunktionUpdateCommand = this.fachfunktionUpdateCommand.withAktion(Aktion.UPDATE);
//        });
//
//        return badge;
//    }
}
