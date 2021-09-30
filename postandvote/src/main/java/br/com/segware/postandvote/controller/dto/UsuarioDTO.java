package br.com.segware.postandvote.controller.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import br.com.segware.postandvote.model.Usuario;

public class UsuarioDTO {

	private String nomeDeUsuario;
	private List<PerfilDTO> perfis = new ArrayList<>();
	
	public UsuarioDTO(Usuario usuario) {
		this.nomeDeUsuario = usuario.getNomeDeUsuario();
		this.perfis = PerfilDTO.converterPerfis(usuario.getPerfis());
	}
	
	
	public String getNomeDeUsuario() {
		return this.nomeDeUsuario;
	}
	

	public List<PerfilDTO> getPerfis() {
		return perfis;
	}


	public static Page<UsuarioDTO> converterUsuarios(Page<Usuario> paginacaoUsuarios) {
		return paginacaoUsuarios.map(UsuarioDTO::new);
	}
	
	
	
}
