package it.uniroma3;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.uniroma3.model.Responsabile;
import it.uniroma3.service.ResponsabileService;

@SpringBootApplication
public class ProjectSiwAziendaApplication {

	@Autowired
	private ResponsabileService responsabileService;
	
	public static void main(String[] args) {
		SpringApplication.run(ProjectSiwAziendaApplication.class, args);
	}
	
	@PostConstruct
	public void init() {
		Responsabile responsabile = new Responsabile();
		responsabile.setNome("admin");
		responsabile.setCognome("admin");
		responsabile.setUsername("admin");
		responsabile.setPassword("admin");
		responsabile.setRole("ADMIN");
		if(responsabileService.findByUsername("admin") == null) {
		responsabileService.save(responsabile);
		}
		
	}
}
