package br.com.segware.postandvote.controller.dto;


import java.util.List;
import java.util.stream.Collectors;
import br.com.segware.postandvote.model.Perfil;

public class PerfilDTO {

	private Long id;
	private String nome;
	
	
	public PerfilDTO(Perfil perfil) {
		
		this.id = perfil.getId();
		this.nome = perfil.getNome();
		
	}


	public Long getId() {
		return id;
	}


	public String getNome() {
		return nome;
	}
	
	public static List<PerfilDTO> converterPerfis(List<Perfil> perfis){
		
		return perfis
				.stream()
				.map(PerfilDTO::new)
				.collect(Collectors.toList());
		
	}
	
	
}
