package de.lehrke.agiledocsfrontend.agiledocsfrontend.fsimport;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.Fachfunktion;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.FachfunktionId;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.TestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
public class FileImport {

    public Fachfunktion importFachfunktion(String filename) throws IOException {
        ObjectMapper objectMapper = createObjectMapper(filename);

        String[] segments = filename.split("/");
        return objectMapper.readValue(new File(filename), Fachfunktion.class).withId(new FachfunktionId(segments[segments.length - 1].replaceAll(".(json|yaml)", "")));
    }

    public void exportFachfunktion(Fachfunktion fachfunktion, String filename) throws IOException {
        ObjectMapper objectMapper = createObjectMapper(filename);

        objectMapper.writeValue(new File(filename), fachfunktion);
    }

    public TestResult importTestResult(String filename) throws IOException {
        ObjectMapper objectMapper = createObjectMapper(filename);

        return objectMapper.readValue(new File(filename), TestResult.class);
    }

    private ObjectMapper createObjectMapper(String filename) {
        ObjectMapper objectMapper;
        if (filename.endsWith(".json")) {
            objectMapper = new ObjectMapper();
        } else if (filename.endsWith(".xml")) {
            objectMapper = new XmlMapper();
        }
        else {
            objectMapper = new ObjectMapper(new YAMLFactory());
        }
        return objectMapper;
    }
}
