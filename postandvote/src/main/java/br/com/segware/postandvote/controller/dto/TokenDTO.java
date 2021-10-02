package br.com.segware.postandvote.controller.dto;

public class TokenDTO {

	private String token;
	private String tipo;

	public TokenDTO() {}
	
	public TokenDTO(String token, String tipo) {
		this.token = token;
		this.tipo = tipo;
	}

	public String getToken() {
		return token;
	}

	public String getTipo() {
		return tipo;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	

}
