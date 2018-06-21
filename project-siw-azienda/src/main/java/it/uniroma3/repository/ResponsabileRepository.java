package it.uniroma3.repository;



import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.model.Responsabile;



public interface ResponsabileRepository extends CrudRepository <Responsabile,Long>{

	public List<Responsabile> findByNomeAndCognome(String nome, String cognome);

	public Responsabile findByUsername(String username);

	
	
	
}
