package br.com.segware.postandvote.controller.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.segware.postandvote.model.Perfil;
import br.com.segware.postandvote.model.Usuario;
import br.com.segware.postandvote.repository.PerfileRepository;

public class UsuarioForm {

	@NotBlank @NotNull @Length(min = 4 , max = 32)
	private String nomeDeUsuario;
	
	@NotBlank @NotNull @Length(min = 4 , max = 32)
	private String senha;
	
	private Long idPerfil;

	
	
	public Long getIdPerfil() {
		return idPerfil;
	}



	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
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

	public UsernamePasswordAuthenticationToken converter() {
		return new UsernamePasswordAuthenticationToken(this.nomeDeUsuario, this.senha);
	}
	
	
	public Usuario converterParaUsuario(PerfileRepository perfileRepository) {
		
		Perfil perfil = perfileRepository.findById(idPerfil).get();
		String senhaCriptografada = this.encriptarSenha(senha);
		
		return new Usuario(this.nomeDeUsuario,senhaCriptografada,perfil);
	}
	
	private String encriptarSenha(String senha) {
		return new BCryptPasswordEncoder().encode(senha);
	}
	

}
