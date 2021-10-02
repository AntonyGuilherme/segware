package br.com.segware.postandvote.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Table(name="usuarios")
public class Usuario implements UserDetails {
	
	private static final long serialVersionUID = 5713148853138748061L;

	@Id
	private String nomeDeUsuario;
	
	private String senha;
	
	@ManyToMany(fetch= FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinTable(
			name="usuarios_perfis",
			joinColumns = { @JoinColumn(name="id_usuario") },
			inverseJoinColumns = { @JoinColumn(name="id_perfil") }
			)
	private List<Perfil> perfis = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
	private List<Poste> postes = new ArrayList<Poste>();
	
	public Usuario() {}
	
	public Usuario(String nomeDeUsuario, String senha) {
		this.nomeDeUsuario = nomeDeUsuario;
		this.senha = senha;
	}
	
	public Usuario(String nomeDeUsuario, String senha, Perfil perfil) {
		this.nomeDeUsuario = nomeDeUsuario;
		this.senha = senha;
		this.perfis.add(perfil);
	}

	public String getNomeDeUsuario() {
		return nomeDeUsuario;
	}
	public String getSenha() {
		return senha;
	}
	
	public void setNomeDeUsuario(String nomeDeUsuario) {
		this.nomeDeUsuario = nomeDeUsuario;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public List<Perfil> getPerfis() {
		return Collections.unmodifiableList(this.perfis);
	}
	

	
	@Override
	public Collection<Perfil> getAuthorities() {
		return this.perfis;
	}

	@Override
	public String getPassword() {
		
		return this.getSenha();
	}

	@Override
	public String getUsername() {
		
		return this.getNomeDeUsuario();
	}

	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nomeDeUsuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(nomeDeUsuario, other.nomeDeUsuario);
	}

	

	
	
	
	
	
	
	
}
