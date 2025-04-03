package de.lehrke.agiledocsfrontend.agiledocsfrontend.persistence.jpa;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.Fachfunktion;
import jakarta.persistence.Converter;
import jakarta.persistence.AttributeConverter;
import lombok.SneakyThrows;

@Converter(autoApply = true)
public class FachfunktionConverter implements AttributeConverter<Fachfunktion, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(Fachfunktion fachfunktion) {

        return this.objectMapper.writeValueAsString(fachfunktion);
    }

    @SneakyThrows
    @Override
    public Fachfunktion convertToEntityAttribute(String string) {
        return this.objectMapper.readValue(string, Fachfunktion.class);
    }
}
