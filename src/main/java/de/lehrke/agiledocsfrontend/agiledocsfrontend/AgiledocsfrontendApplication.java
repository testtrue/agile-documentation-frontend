package de.lehrke.agiledocsfrontend.agiledocsfrontend;

import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.logic.FachfunktionRepository;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.domain.model.FachfunktionId;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.persistence.jpa.FachfunktionDbo;
import de.lehrke.agiledocsfrontend.agiledocsfrontend.persistence.jpa.H2Repository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AgiledocsfrontendApplication {

	@Autowired
	private H2Repository fachfunktionRepository;

	@PostConstruct
	public void init() {
		this.fachfunktionRepository.save(new FachfunktionDbo().withId(new FachfunktionId("FF_PROJ_00001")).withName("Test").withBeschreibung("Dies ist ein Test"));
	}

	public static void main(String[] args) {
		SpringApplication.run(AgiledocsfrontendApplication.class, args);
	}

}
