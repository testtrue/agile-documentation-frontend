package de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FachfunktionTest {

    @Test
    void neueFachfunktionId() {
        // GIVEN
        final String id = "1";
        final String projectId = "PROJ";

        // WHEN
        final FachfunktionId fachfunktionId = FachfunktionId.of(projectId, id);
        // THEN
        Assertions.assertEquals("FF-PROJ-00001" , fachfunktionId.id());
        Assertions.assertDoesNotThrow(() -> new FachfunktionId(fachfunktionId.id()));
    }
}