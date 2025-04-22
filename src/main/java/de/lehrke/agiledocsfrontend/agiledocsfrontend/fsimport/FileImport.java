package de.lehrke.agiledocsfrontend.agiledocsfrontend.fsimport;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.Fachfunktion;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.FachfunktionId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
public class FileImport {

    public Fachfunktion importFachfunktion(String filename) throws IOException {
        ObjectMapper objectMapper;
        if (filename.endsWith(".json")) {
            objectMapper = new ObjectMapper();
        } else {
            objectMapper = new ObjectMapper(new YAMLFactory());
        }

        String[] segments = filename.split("/");
        return objectMapper.readValue(new File(filename), Fachfunktion.class).withId(new FachfunktionId(segments[segments.length - 1].replaceAll(".(json|yaml)", "")));
    }

    public void exportFachfunktion(Fachfunktion fachfunktion, String filename) throws IOException {
        ObjectMapper objectMapper;
        if (filename.endsWith(".json")) {
            objectMapper = new ObjectMapper();
        } else {
            objectMapper = new ObjectMapper(new YAMLFactory());
        }

        objectMapper.writeValue(new File(filename), fachfunktion);
    }
}
