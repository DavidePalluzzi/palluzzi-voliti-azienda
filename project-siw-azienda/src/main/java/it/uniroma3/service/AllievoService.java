package it.uniroma3.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.model.Allievo;
import it.uniroma3.repository.AllievoRepository;

@Transactional
@Service

public class AllievoService {
@Autowired
private AllievoRepository allievorepository;

public Allievo save(Allievo allievo) {
	return this.allievorepository.save(allievo);
}

public List<Allievo> findAll(){
	return (List<Allievo>)this.allievorepository.findAll();
}
public Allievo findById(Long id) {
	Optional<Allievo> allievo = this.allievorepository.findById(id);
	if (allievo.isPresent()) 
		return allievo.get();
	else
		return null;
}
public boolean alreadyExists(Allievo allievo) {
	List<Allievo> allievi = this.allievorepository.findByNomeAndCognomeAndTelefonoAndEmailAndDataAndLuogoDiNascita(allievo.getNome(),allievo.getCognome(),allievo.getTelefono(),allievo.getEmail(),allievo.getData(),allievo.getLuogoDiNascita());
	if ((allievi.size() > 0))//controllo capienza
		return true;
	else 
		return false;
}
}

