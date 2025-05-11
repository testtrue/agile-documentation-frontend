package de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class TestResult {

    private Akzeptanzkriterium akzeptanzkriterium;
    private TestResultStatus status;
}
