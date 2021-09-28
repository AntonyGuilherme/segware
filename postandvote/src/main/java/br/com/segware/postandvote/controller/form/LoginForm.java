package br.com.segware.postandvote.controller.form;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginForm {

	private String nomeDeUsuario;
	private String senha;

	
	
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

}
