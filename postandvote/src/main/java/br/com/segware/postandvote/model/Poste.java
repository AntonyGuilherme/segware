package br.com.segware.postandvote.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="postes")
public class Poste {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// poste em si
	private String texto;
	
	// os "upvotes"
	private Long aprovacoes;
	
	
	
	public Poste() {}



	public Poste(String texto, Long aprovacoes) {
		this.texto = texto;
		this.aprovacoes = aprovacoes;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getTexto() {
		return texto;
	}



	public Long getAprovacoes() {
		return aprovacoes;
	}



	public void setTexto(String texto) {
		this.texto = texto;
	}



	public void setAprovacoes(Long aprovacoes) {
		this.aprovacoes = aprovacoes;
	}
	
	
	
	
	
	
}
