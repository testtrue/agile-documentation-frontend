package de.lehrke.agiledocsfrontend.agiledocsfrontend.view;

import com.leakyabstractions.result.api.Result;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.logic.*;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.Akzeptanzkriterium;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.Fachfunktion;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.FachfunktionId;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Route("edit")
public class FachfunktionEditorView extends VerticalLayout implements HasUrlParameter<String> {

    private static final String ID_PLACEHOLDER = "--plus--";
    private final FachfunktionRepository repository;
    private final Workflow workflow;
    private Fachfunktion fachfunktion;
    private FachfunktionUpdateCommand fachfunktionUpdateCommand;
    Binder<Fachfunktion> binder;
    private boolean isNew = true;
    private TextField id;
    private Grid<AkzeptanzkriteriumUpdateCommand> akzeptanzkriteriumGrid;
    private Editor<AkzeptanzkriteriumUpdateCommand> akzeptanzkriteriumEditor;

    public FachfunktionEditorView(FachfunktionRepository repository, Workflow workflow) {
        this.repository = repository;
        this.workflow = workflow;

        Button back = new Button("Zurück", new Icon(VaadinIcon.ARROW_LEFT));
        back.addClickListener(e -> {
            back.getUI().ifPresent(
                    ui -> ui.navigate(FachfunktionListView.class)
            );
        });

        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
                // Use one column by default
                new FormLayout.ResponsiveStep("0", 1));
        this.id = new TextField("ID");
        this.id.setReadOnly(true);
        TextField name = new TextField("Name");
        TextArea beschreibung = new TextArea("Beschreibung");
        beschreibung.setMinRows(4);
        this.akzeptanzkriteriumGrid = new Grid<>(AkzeptanzkriteriumUpdateCommand.class, false);
        Grid.Column<AkzeptanzkriteriumUpdateCommand> idCol = this.akzeptanzkriteriumGrid.addColumn(new ComponentRenderer<>(
                Div::new,
                (d, f) -> {
                    if (ID_PLACEHOLDER.equals(f.id())) {
                        Button neuesAkzeptanzkriterium = new Button(new Icon(VaadinIcon.PLUS));
                        neuesAkzeptanzkriterium.addClickListener((e) -> {
                            AkzeptanzkriteriumUpdateCommand a = new AkzeptanzkriteriumUpdateCommand("", "", Aktion.ADD);
                            this.fachfunktionUpdateCommand.akzeptanzkriterien().add(a);
                            this.akzeptanzkriteriumGrid.setItems(this.fachfunktionUpdateCommand.akzeptanzkriterien());
                            if (this.akzeptanzkriteriumEditor != null) {
                                this.akzeptanzkriteriumEditor.closeEditor();
                            }
                            this.akzeptanzkriteriumEditor = this.akzeptanzkriteriumGrid.getEditor();
                            this.akzeptanzkriteriumEditor.editItem(a);
                        });
                        d.add(neuesAkzeptanzkriterium);
                    } else {
                        d.add(f.id());
                    }
                }

        )).setHeader("ID").setAutoWidth(true);
        Grid.Column<AkzeptanzkriteriumUpdateCommand> beschreibungCol = this.akzeptanzkriteriumGrid.addColumn(AkzeptanzkriteriumUpdateCommand::beschreibung).setHeader("Beschreibung");

        binder = new Binder<>(Fachfunktion.class);
        binder.addValueChangeListener(e -> this.fachfunktionUpdateCommand = this.fachfunktionUpdateCommand.withAktion(Aktion.UPDATE));
        binder.forField(name).bind("name");
        binder.forField(beschreibung).bind("beschreibung");


        Binder<AkzeptanzkriteriumUpdateCommand> akzeptanzkriteriumBinder = new Binder<>(AkzeptanzkriteriumUpdateCommand.class);
        akzeptanzkriteriumBinder.addValueChangeListener(e -> this.fachfunktionUpdateCommand = this.fachfunktionUpdateCommand.withAktion(Aktion.UPDATE));
        this.akzeptanzkriteriumGrid.getEditor().setBinder(akzeptanzkriteriumBinder);
        TextField akzeptanzkriteriumId = new TextField();
        akzeptanzkriteriumBinder.forField(akzeptanzkriteriumId).bind(AkzeptanzkriteriumUpdateCommand::getId, AkzeptanzkriteriumUpdateCommand::setId);
        idCol.setEditorComponent(akzeptanzkriteriumId);

        TextField akzeptanzkriteriumBeschreibung = new TextField();
        akzeptanzkriteriumBinder.forField(akzeptanzkriteriumBeschreibung).bind(AkzeptanzkriteriumUpdateCommand::getBeschreibung, AkzeptanzkriteriumUpdateCommand::setBeschreibung);
        beschreibungCol.setEditorComponent(akzeptanzkriteriumBeschreibung);

        akzeptanzkriteriumGrid.addItemDoubleClickListener(e -> {
            log.info("{}", e.getItem());
            log.info("Double clicked");
            if (this.akzeptanzkriteriumEditor != null) {
                this.akzeptanzkriteriumEditor.closeEditor();
            }
            this.akzeptanzkriteriumEditor = this.akzeptanzkriteriumGrid.getEditor();
            this.akzeptanzkriteriumEditor.editItem(e.getItem());
            Component editorComponent = e.getColumn().getEditorComponent();
            if (editorComponent instanceof Focusable<?>) {
                ((Focusable) editorComponent).focus();
            }
        });

        this.akzeptanzkriteriumGrid.getEditor().addCloseListener(e -> {
            log.info("{}", e.getItem());
            log.info("Close editor");
            e.getSource().save();
            //e.getSource().closeEditor();
        });

        Button save = new Button("Speichern", new Icon(VaadinIcon.FILE));
        save.addClickListener(e -> {
            Result<Fachfunktion, String> r = null;
           if (this.isNew) {
               r = this.workflow.speicherNeueFachfunktion(this.fachfunktion
                       .withAkzeptanzkriterien(this.fachfunktionUpdateCommand.akzeptanzkriterien().stream().filter(a -> !ID_PLACEHOLDER.equals(a.getId())).map(a -> new Akzeptanzkriterium(a.id(), a.beschreibung())).toList())
                       .withTags(this.fachfunktionUpdateCommand.tags().stream().map(TagUpdateCommand::tag).toList())
               );
           } else {
               r = this.workflow.updateFachfunktion(this.fachfunktionUpdateCommand
                       .withBeschreibung(this.fachfunktion.getBeschreibung())
                       .withName(this.fachfunktion.getName()));
           }

            if (r.hasFailure() && r.getFailure().isPresent()) {
                Notification
                        .show(r.getFailure().get(), 10000, Notification.Position.TOP_END);
            } else {
                Notification
                        .show("Erfolgreich gespeichert", 10000, Notification.Position.TOP_END);
                e.getSource().getUI().ifPresent(
                        ui -> ui.navigate(FachfunktionEditorView.class, this.fachfunktion.getId().id())
                );
            }

        });
        Button cancel = new Button("Abbrechen", new Icon(VaadinIcon.CLOSE));
        Button back2 = new Button("Zurück", new Icon(VaadinIcon.ARROW_LEFT));
        back2.addClickListener(e -> {
            back2.getUI().ifPresent(
                    ui -> ui.navigate(FachfunktionListView.class)
            );
        });
        HorizontalLayout buttonGroup = new HorizontalLayout(save, cancel, back2);
        formLayout.add(this.id, name, beschreibung, this.akzeptanzkriteriumGrid, buttonGroup);
        this.add(back, formLayout);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String s) {
        if (s == null) {
            this.fachfunktion = workflow.neueFachfunktion("PROJ").getSuccess().get();
        } else {
            this.fachfunktion = repository.findFachfunktionById(new FachfunktionId(s)).get();
        }
        this.isNew = s == null;
        this.id.setValue(this.fachfunktion.getId().id());

        List<AkzeptanzkriteriumUpdateCommand> akzeptanzkriteriumUpdateCommandList = new ArrayList<>();
        akzeptanzkriteriumUpdateCommandList.add(new AkzeptanzkriteriumUpdateCommand(ID_PLACEHOLDER, "Neues Akzeptanzkriterium", Aktion.NOTHING));
        akzeptanzkriteriumUpdateCommandList.addAll(this.fachfunktion.getAkzeptanzkriterien().stream()
                .map(a ->
                        new AkzeptanzkriteriumUpdateCommand(a.getId(), a.getBeschreibung(), Aktion.NOTHING))
                .toList());

        this.fachfunktionUpdateCommand = new FachfunktionUpdateCommand(
                                                    this.fachfunktion.getId(),
                                                    this.fachfunktion.getName(),
                                                    this.fachfunktion.getBeschreibung(),
                                                    akzeptanzkriteriumUpdateCommandList,
                                                    new ArrayList<>(this.fachfunktion.getTags().stream()
                                                            .map(t ->
                                                                    new TagUpdateCommand(t, Aktion.NOTHING))
                                                            .toList()),
                                                    Aktion.NOTHING);

        this.binder.setBean(fachfunktion);
        this.akzeptanzkriteriumGrid.setItems(this.fachfunktionUpdateCommand.akzeptanzkriterien());
    }
}
