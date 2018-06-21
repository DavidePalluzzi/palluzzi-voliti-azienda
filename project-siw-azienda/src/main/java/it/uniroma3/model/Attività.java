package it.uniroma3.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class Attività {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(nullable=false)
	private String nome;
	@Temporal(TemporalType.DATE)
	private Date data;
	@ManyToOne
	private CentroDiFormazione centro;
	
	@ManyToMany(fetch=FetchType.EAGER)
	private List<Allievo> allievi;
	
	public Attività() {
		
	}
	public Attività( String nome, Date data, CentroDiFormazione centro) {

		this.nome = nome;
		this.data = data;
		this.centro = centro;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public void setCentro(CentroDiFormazione centro) {
		this.centro = centro;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public Date getData() {
		return data;
	}

	public CentroDiFormazione getCentro() {
		return centro;
	}

	public List<Allievo> getAllievi() {
		return allievi;
	}

	public void setAllievi(List<Allievo> allievi) {
		this.allievi = allievi;
	}


}
