package it.uniroma3.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class CentroDiFormazione {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(nullable=false)
	private String nome;
	@Column(nullable=false)
	private String indirizzo;
	private String telefono;
	private String email;
	private int capienza;
	@OneToOne
	private Responsabile responsabile;
	
	//private List<Allievo> allievi;
	@OneToMany(mappedBy="centro", fetch=FetchType.EAGER)
	private List<Attività> attivita;
	public Long getId() {
		return id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getCapienza() {
		return capienza;
	}
	public void setCapienza(int capienza) {
		this.capienza = capienza;
	}
	/*public List<Allievo> getAllievi() {
		return allievi;
	}
	public void setAllievi(List<Allievo> allievi) {
		this.allievi = allievi;
	}*/
	public List<Attività> getAttivita() {
		return attivita;
	}
	public void setAttivita(List<Attività> attività) {
		this.attivita = attività;
	}
	public Responsabile getResponsabile() {
		return responsabile;
	}
	public void setResponsabile(Responsabile responsabile) {
		this.responsabile = responsabile;
	}
	
	
	
	
}
