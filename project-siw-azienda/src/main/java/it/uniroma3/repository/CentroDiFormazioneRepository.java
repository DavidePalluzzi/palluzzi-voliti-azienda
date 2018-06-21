package it.uniroma3.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.model.CentroDiFormazione;
import it.uniroma3.model.Responsabile;

public interface CentroDiFormazioneRepository extends CrudRepository <CentroDiFormazione, Long>{
	
Optional<CentroDiFormazione> findByNome(String nome);
List<CentroDiFormazione> findByNomeAndIndirizzoAndTelefonoAndEmailAndCapienza(String nome, String indirizzo,String telefono,String email,int capienza);
CentroDiFormazione findByResponsabile(Responsabile responsabile);

}
