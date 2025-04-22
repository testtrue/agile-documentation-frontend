package de.lehrke.agiledocsfrontend.agiledocsfrontend.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.router.Route;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.logic.FachfunktionRepository;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.fsimport.FileImport;
import org.vaadin.filesystemdataprovider.FilesystemData;
import org.vaadin.filesystemdataprovider.FilesystemDataProvider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Route("files")
public class FileView extends VerticalLayout {

    private final FileImport fileImport;
    private final FachfunktionRepository repository;

    public FileView(FileImport fileImport, FachfunktionRepository repository) {
        this.fileImport = fileImport;
        this.repository = repository;

        File rootFile = new File("/");
        FilesystemData root = new FilesystemData(rootFile, false);
        FilesystemDataProvider fileSystem = new FilesystemDataProvider(root);
        final TreeGrid<File> tree = new TreeGrid<>();
        tree.setItemSelectableProvider(f -> f.isDirectory() || f.getName().toLowerCase().endsWith(".json") || f.getName().toLowerCase().endsWith(".yaml"));
        tree.setDataProvider(fileSystem);
        tree.addHierarchyColumn(File::getName).setHeader("Name").setAutoWidth(true).setResizable(true);
        tree.addColumn(file -> file.isDirectory() ? "Directory" : "File").setHeader("Type");
        tree.addComponentColumn(file -> {
                if (!file.isDirectory() && !file.getName().toLowerCase().endsWith(".json") && !file.getName().toLowerCase().endsWith(".yaml")) {
                    return new Div();
                }
                return new Button("Import", new Icon(VaadinIcon.PLUS), buttonClickEvent -> {

                        List<String> filesToImport = new ArrayList<>();
                        if (file.isDirectory()) {
                            File[] files = file.listFiles(f -> f.getName().endsWith(".json") || f.getName().endsWith(".yaml") );
                            if (files == null) {
                                return;
                            }
                            Arrays.stream(files).forEach(f -> filesToImport.add(f.getAbsolutePath()));
                        } else {
                            filesToImport.add(file.getAbsolutePath());
                        }
                        filesToImport.forEach(
                                f -> {
                                    try {
                                        this.repository.saveFachfunktion(this.fileImport.importFachfunktion(f));
                                        Notification.show(f + " wurde importiert");
                                    } catch (IOException e) {
                                        Notification.show(e.getMessage());
                                    }
                                });
                });});
        tree.addComponentColumn(file -> {
            if (!file.isDirectory()) {
                return new Div();
            }
            return new Button("Export", new Icon(VaadinIcon.FILE), buttonClickEvent -> this.repository.findFachfunktionen().forEach(f -> {
                try {
                    this.fileImport.exportFachfunktion(f,file + "/" + f.getId().id() + ".yaml");
                    Notification.show(f.getId().id() + " wurde exportiert");
                } catch (IOException e) {
                    Notification.show(e.getMessage());
                }
            }));
        });

        this.add(tree);

    }
}
