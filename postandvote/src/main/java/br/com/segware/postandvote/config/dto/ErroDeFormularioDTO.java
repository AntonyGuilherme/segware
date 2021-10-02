package br.com.segware.postandvote.config.dto;

import br.com.segware.postandvote.config.ErroDeFormularioException;

public class ErroDeFormularioDTO {

	private String campo;
	private String erro;
	
	
	public ErroDeFormularioDTO(String campo, String erro) {
		this.campo = campo;
		this.erro = erro;
	}
	
	public ErroDeFormularioDTO(ErroDeFormularioException excessao) {
		this.campo = excessao.getCampo();
		this.erro = excessao.getMensagem();
	}
	
	
	public String getCampo() {
		return campo;
	}
	public String getErro() {
		return erro;
	}

	
	
}
