package de.lehrke.agiledocsfrontend.agiledocsfrontend;

import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.Fachfunktion;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.FachfunktionId;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.persistence.jpa.H2Repository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class AgiledocsfrontendApplication {

	@Autowired
	private H2Repository fachfunktionRepository;

	@PostConstruct
	public void init() {
		this.fachfunktionRepository.saveFachfunktion(new Fachfunktion().withId(new FachfunktionId("FF_PROJ_00001")).withName("Test").withBeschreibung("Dies ist ein Test").withTags(List.of("Test")));
		this.fachfunktionRepository.saveFachfunktion(new Fachfunktion().withId(new FachfunktionId("FF_PROX_00002")).withName("Test2").withBeschreibung("Dies ist ein Test2").withTags(List.of("Test2", "Test3")));
	}

	public static void main(String[] args) {
		SpringApplication.run(AgiledocsfrontendApplication.class, args);
	}

}
