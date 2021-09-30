package br.com.segware.postandvote.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name="perfis")
public class Perfil implements GrantedAuthority {

	private static final long serialVersionUID = 6758966932135486009L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	
	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return this.nome;
	}

	
	public Perfil() {}

	public Perfil(String nome) {
		this.nome = nome;
	}


	public Long getId() {
		return id;
	}


	public String getNome() {
		return nome;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public void setNome(String nome) {
		this.nome = nome;
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
		Perfil other = (Perfil) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
	
}
