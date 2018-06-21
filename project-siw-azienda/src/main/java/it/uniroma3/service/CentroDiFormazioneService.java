package it.uniroma3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.model.CentroDiFormazione;
import it.uniroma3.model.Responsabile;
import it.uniroma3.repository.CentroDiFormazioneRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CentroDiFormazioneService {
@Autowired
private CentroDiFormazioneRepository repository;

public CentroDiFormazione save(CentroDiFormazione centro) {
	return this.repository.save(centro);
}

public List<CentroDiFormazione> findAll() {
	return (List<CentroDiFormazione>) this.repository.findAll();
}

public CentroDiFormazione findByNome(String nome) {
	Optional<CentroDiFormazione> centro = this.repository.findByNome(nome);
	if (centro.isPresent()) 
		return centro.get();
	else
		return null;
}
public CentroDiFormazione findById(Long id) {
	Optional<CentroDiFormazione> centro = this.repository.findById(id);
	if (centro.isPresent()) 
		return centro.get();
	else
		return null;
}
public boolean alreadyExists(CentroDiFormazione centro) {
	List<CentroDiFormazione> centri = this.repository.findByNomeAndIndirizzoAndTelefonoAndEmailAndCapienza(centro.getNome(),centro.getIndirizzo(),centro.getTelefono(),centro.getEmail(),centro.getCapienza());
	if (centri.size() > 0)
		return true;
	else 
		return false;
}

public CentroDiFormazione findByResponsabile(Responsabile responsabile) {
	return this.repository.findByResponsabile(responsabile);
	
}	

}
