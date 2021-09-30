package br.com.segware.postandvote.config;

public class ErroDeFormularioException extends RuntimeException {

	private static final long serialVersionUID = 5367067223372285146L;

	private String mensagem;
	private String campo;
	
	
	public ErroDeFormularioException(String mensagem, String campo) {
		super(mensagem);
		this.mensagem = mensagem;
		this.campo = campo;
	}
	
	public String getMensagem() {
		return mensagem;
	}
	
	public String getCampo() {
		return campo;
	}
	
	
	
	
}
