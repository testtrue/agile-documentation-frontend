package de.lehrke.agiledocsfrontend.agiledocsfrontend.view;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.logic.FachfunktionRepository;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.logic.Workflow;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.Fachfunktion;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.FachfunktionId;

@Route("edit")
public class FachfunktionEditorView extends HorizontalLayout implements HasUrlParameter<String> {

    private final FachfunktionRepository repository;
    private final Workflow workflow;
    private Fachfunktion fachfunktion;

    public FachfunktionEditorView(FachfunktionRepository repository, Workflow workflow) {
        this.repository = repository;
        this.workflow = workflow;
        FormLayout formLayout = new FormLayout();

        this.add(formLayout);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String s) {
        if (s == null) {
            this.fachfunktion = workflow.neueFachfunktion("PROJ").getSuccess().get();
        } else {
            this.fachfunktion = repository.findFachfunktionById(new FachfunktionId(s)).get();
        }
    }
}
