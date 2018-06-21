package it.uniroma3.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.model.Attività;
import it.uniroma3.model.CentroDiFormazione;
import it.uniroma3.repository.AttivitàRepository;

@Service
public class AttivitàService {

@Autowired
private AttivitàRepository repository; 
	
	public Attività save(Attività attivita) {
		return this.repository.save(attivita);
	}
	/*public List<Attività> findByAllievi( String allievo){
		return this.repository.findByAllievi(allievo);
	}
*/
	public List<Attività> findByCentro(CentroDiFormazione centro) {
		return this.repository.findByCentro(centro);
	}

	public List<Attività> findAll(){
		return (List<Attività>) this.repository.findAll();
		}
	
	public Attività findById(Long id) {
		Optional<Attività> attivita = this.repository.findById(id);
		if (attivita.isPresent()) 
			return attivita.get();
		else
			return null;
	}

	public boolean alreadyExists(Attività attivita) {
		List<Attività> lista = this.repository.findByNomeAndDataAndCentro(attivita.getNome(),attivita.getData(),attivita.getCentro());
		if (lista.size() > 0)
			return true;
		else 
			return false;
	}	
}
