package de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model;

import org.apache.commons.lang3.StringUtils;

public record FachfunktionId(String id) {
    private static final String ID_PREFIX = "FF_";
    private static final String ID_VALIDATION_REGEX = "^" + ID_PREFIX + "\\w{2,6}_\\d{5}$";

    public FachfunktionId(String id) {
        if (!id.matches(ID_VALIDATION_REGEX)) {
            throw new IllegalArgumentException("Fachfunktion id must contain alphanumeric characters");
        }
        this.id = id;
    }

    public static FachfunktionId of(String projectId, String id) {
        return new FachfunktionId(ID_PREFIX + projectId + "_" + StringUtils.leftPad(String.valueOf(id), 5, "0"));
    }
}
