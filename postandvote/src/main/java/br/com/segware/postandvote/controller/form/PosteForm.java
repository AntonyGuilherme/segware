package br.com.segware.postandvote.controller.form;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.segware.postandvote.config.ErroDeFormularioException;
import br.com.segware.postandvote.model.Poste;
import br.com.segware.postandvote.model.Usuario;
import br.com.segware.postandvote.repository.UsuarioRepository;

public class PosteForm {
	
	@NotNull @NotEmpty @Length(max=250, min = 4)
	private String texto;
	
	
	public Poste converterParaPoste(UsuarioRepository usuarioRepository, String nomeDeUsuario) 
			throws ErroDeFormularioException {
		
		Optional<Usuario> optionalUsuario = usuarioRepository.findById(nomeDeUsuario);
		
		if(optionalUsuario.isEmpty()) {
			throw new ErroDeFormularioException("Usuário não encontrado","nomeDeUsuario");
		}
		
		return new Poste(this.texto,optionalUsuario.get());
	}
	
	
	
	public String getTexto() {
		return texto;
	}



	public void setTexto(String texto) {
		this.texto = texto;
	}
	

	

}
