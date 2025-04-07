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
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.logic.FachfunktionRepository;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.logic.FachfunktionUpdateCommand;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.logic.Workflow;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.Akzeptanzkriterium;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.Fachfunktion;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.FachfunktionId;

import java.util.ArrayList;

@Route("edit")
public class FachfunktionEditorView extends VerticalLayout implements HasUrlParameter<String> {

    private static final String ID_PLACEHOLDER = "--plus--";
    private final FachfunktionRepository repository;
    private final Workflow workflow;
    private Fachfunktion fachfunktion;
    Binder<Fachfunktion> binder;
    private boolean isNew = true;
    private TextField id;
    private Grid<Akzeptanzkriterium> akzeptanzkriteriumGrid;

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
        this.akzeptanzkriteriumGrid = new Grid<>(Akzeptanzkriterium.class, false);
        Grid.Column<Akzeptanzkriterium> idCol = this.akzeptanzkriteriumGrid.addColumn(new ComponentRenderer<>(
                Div::new,
                (d, f) -> {
                    if (ID_PLACEHOLDER.equals(f.getId())) {
                        Button neuesAkzeptanzkriterium = new Button(new Icon(VaadinIcon.PLUS));
                        neuesAkzeptanzkriterium.addClickListener((e) -> {
                            this.fachfunktion.getAkzeptanzkriterien().add(new Akzeptanzkriterium());
                            this.akzeptanzkriteriumGrid.setItems(calculateGridList(this.fachfunktion));
                        });
                        d.add(neuesAkzeptanzkriterium);
                    } else {
                        d.add(f.getId());
                    }
                }

        )).setHeader("ID").setAutoWidth(true);
        Grid.Column<Akzeptanzkriterium> beschreibungCol = this.akzeptanzkriteriumGrid.addColumn(Akzeptanzkriterium::getBeschreibung).setHeader("Beschreibung");

        binder = new Binder<>(Fachfunktion.class);
        binder.forField(name).bind("name");
        binder.forField(beschreibung).bind("beschreibung");


        Binder<Akzeptanzkriterium> akzeptanzkriteriumBinder = new Binder<>(Akzeptanzkriterium.class);
        Editor<Akzeptanzkriterium> akzeptanzkriteriumEditor = this.akzeptanzkriteriumGrid.getEditor();
        akzeptanzkriteriumEditor.setBinder(akzeptanzkriteriumBinder);
        akzeptanzkriteriumEditor.setBuffered(true);
        TextField akzeptanzkriteriumId = new TextField();
        akzeptanzkriteriumBinder.forField(akzeptanzkriteriumId).bind("id");
        idCol.setEditorComponent(akzeptanzkriteriumId);
        akzeptanzkriteriumEditor.addCloseListener(e -> {
            this.fachfunktion.getAkzeptanzkriterien().add(e.getItem());
        });
        TextField akzeptanzkriteriumBeschreibung = new TextField();
        akzeptanzkriteriumBinder.forField(akzeptanzkriteriumBeschreibung).bind("beschreibung");
        beschreibungCol.setEditorComponent(akzeptanzkriteriumBeschreibung);

        akzeptanzkriteriumGrid.addItemDoubleClickListener(e -> {
            akzeptanzkriteriumEditor.editItem(e.getItem());
            Component editorComponent = e.getColumn().getEditorComponent();
            if (editorComponent instanceof Focusable<?>) {
                ((Focusable) editorComponent).focus();
            }
        });

        Button save = new Button("Speichern", new Icon(VaadinIcon.FILE));
        save.addClickListener(e -> {
           if (this.isNew) {
               Result<Fachfunktion, String> r = this.workflow.speicherNeueFachfunktion(this.fachfunktion);
               if (r.hasFailure() && r.getFailure().isPresent()) {
                   Notification notification = Notification
                           .show(r.getFailure().get(), 10000, Notification.Position.TOP_END);
               }
           } else {
               //FachfunktionUpdateCommand updateCommand = new FachfunktionUpdateCommand();
               //this.workflow.updateFachfunktion();
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
        this.id.setValue(this.fachfunktion.getId().id());
        this.binder.setBean(this.fachfunktion);

        this.akzeptanzkriteriumGrid.setItems(calculateGridList(this.fachfunktion));
    }

    private static ArrayList<Akzeptanzkriterium> calculateGridList(Fachfunktion fachfunktion) {
        ArrayList<Akzeptanzkriterium> akzeptanzkriteriums = new ArrayList<>();
        akzeptanzkriteriums.add(new Akzeptanzkriterium().withBeschreibung("Neues Akzeptanzkriterium").withId(ID_PLACEHOLDER));
        akzeptanzkriteriums.addAll(fachfunktion.getAkzeptanzkriterien());
        return akzeptanzkriteriums;
    }
}
