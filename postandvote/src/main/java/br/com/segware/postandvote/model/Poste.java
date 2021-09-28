package br.com.segware.postandvote.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "postes")
public class Poste {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// poste em si
	private String texto;

	// os "upvotes"
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "poste")
	private List<Aprovacao> aprovacoes;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	public Poste() {
	}

	public Poste(String texto, Usuario usuario) {
		this.texto = texto;
		this.usuario = usuario;
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

	public void setTexto(String texto) {
		this.texto = texto;
	}

	
	public List<Aprovacao> getAprovacoes() {
		return aprovacoes;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Poste other = (Poste) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
	

}
