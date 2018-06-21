package it.uniroma3.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.model.Allievo;

public interface AllievoRepository extends CrudRepository <Allievo,Long>{
	//public List<Attività> findByAllievo ( String Allievo);
	public List<Allievo> findByNomeAndCognomeAndTelefonoAndEmailAndDataAndLuogoDiNascita( String nome, String cognome, String telefono, String email, Date date,
			String luogoDiNascita);
			
	}
