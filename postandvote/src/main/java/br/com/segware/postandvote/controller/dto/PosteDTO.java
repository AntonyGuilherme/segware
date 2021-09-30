package br.com.segware.postandvote.controller.dto;


import org.springframework.data.domain.Page;

import br.com.segware.postandvote.model.Poste;


public class PosteDTO {
	
	private Long id;
	private String texto;
	private String nomeDeUsuario;
	private Long numeroDeAprovacoes = 0l;
	
	
	public PosteDTO(Poste poste) {
		this.id = poste.getId();
		this.texto = poste.getTexto();
		this.nomeDeUsuario = poste.getUsuario().getNomeDeUsuario();
		this.numeroDeAprovacoes = poste.getNumeroDeAprovacoes();		
	}

	
	
	public String getTexto() {
		return texto;
	}
	public String getNomeDeUsuario() {
		return nomeDeUsuario;
	}
	public Long getNumeroDeAprovacoes() {
		return numeroDeAprovacoes;
	}

	public Long getId() {
		return id;
	}


	@Override
	public String toString() {
		return String.format("PosteDTO [texto=%s, nomeDeUsuario=%s, numeroDeAprovacoes=%s]", texto, nomeDeUsuario,
				numeroDeAprovacoes);
	}
	
	
	public static Page<PosteDTO> converterParaPosteDTO(Page<Poste> postes){
		
		return postes.map(PosteDTO::new);
		
	}
	
	
	
	
	

}
