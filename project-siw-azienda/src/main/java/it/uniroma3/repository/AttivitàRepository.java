package it.uniroma3.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.model.Attività;
import it.uniroma3.model.CentroDiFormazione;

public interface AttivitàRepository extends CrudRepository <Attività,Long>{
	//public List<Attività> findByAllievi( String allievo);
	
	public List<Attività> findByCentro(CentroDiFormazione centro);
	
	public List<Attività> findByNomeAndDataAndCentro(String nome,Date data,CentroDiFormazione centro);
	
}
