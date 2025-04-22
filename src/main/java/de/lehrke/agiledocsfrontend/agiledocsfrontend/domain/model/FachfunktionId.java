package de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model;

import org.apache.commons.lang3.StringUtils;

public record FachfunktionId(String id) {
    private static final String ID_PREFIX = "FF-";
    private static final String ID_VALIDATION_REGEX = "^" + ID_PREFIX + "\\w{2,6}-\\d{4,5}$";

    public FachfunktionId {
        if (!id.matches(ID_VALIDATION_REGEX)) {
            throw new IllegalArgumentException("Fachfunktion id must contain alphanumeric characters");
        }
    }

    public static FachfunktionId of(String projectId, String id) {
        return new FachfunktionId(ID_PREFIX + projectId + "-" + StringUtils.leftPad(String.valueOf(id), 5, "0"));
    }
}
