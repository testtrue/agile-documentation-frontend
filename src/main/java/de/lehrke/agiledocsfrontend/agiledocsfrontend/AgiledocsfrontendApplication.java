package de.lehrke.agiledocsfrontend.agiledocsfrontend;

import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.Akzeptanzkriterium;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.Fachfunktion;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.FachfunktionId;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.persistence.jpa.H2FFRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class AgiledocsfrontendApplication {

	@Autowired
	private H2FFRepository fachfunktionRepository;

	@PostConstruct
	public void init() {
		this.fachfunktionRepository.saveFachfunktion(new Fachfunktion().withId(new FachfunktionId("FF-PROX-00001")).withName("Test").withKurzbeschreibung("Dies ist ein Test").withTags(List.of("Test")).withAkzeptanzkriterien(List.of()));
		this.fachfunktionRepository.saveFachfunktion(new Fachfunktion().withId(new FachfunktionId("FF-PROX-00002")).withName("Test2").withKurzbeschreibung("Dies ist ein Test2").withTags(List.of("Test2", "Test3")).withAkzeptanzkriterien(List.of(new Akzeptanzkriterium().withBeschreibung("Testakzeptanz").withId("01"))));
	}

	public static void main(String[] args) {
		SpringApplication.run(AgiledocsfrontendApplication.class, args);
	}

}
