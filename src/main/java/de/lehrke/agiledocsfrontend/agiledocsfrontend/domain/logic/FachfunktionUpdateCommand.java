package de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.logic;

import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.FachfunktionId;

import java.util.List;

public record FachfunktionUpdateCommand(FachfunktionId id, String name, String beschreibung, List<AkzeptanzkriteriumUpdateCommand> akzeptanzkriterien, List<TagUpdateCommand> tags, Aktion aktion) {
}
