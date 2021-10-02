package br.com.segware.postandvote.controller;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.segware.postandvote.controller.dto.UsuarioDTO;
import br.com.segware.postandvote.controller.form.UsuarioForm;
import br.com.segware.postandvote.model.Usuario;
import br.com.segware.postandvote.repository.PerfileRepository;
import br.com.segware.postandvote.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PerfileRepository perfilRepository;
	
	
	
	@GetMapping
	public Page<UsuarioDTO> salvarUsuario(@PageableDefault(
			sort = "nomeDeUsuario", direction = Direction.DESC ,
			page = 0, size = 3
			) Pageable paginacao ) {
		
		Page<Usuario> paginacaoUsuarios = this.usuarioRepository.findAll(paginacao);
		
		return UsuarioDTO.converterUsuarios(paginacaoUsuarios);
		
	}
	
	
	
	@PostMapping
	@Transactional
	public ResponseEntity<UsuarioDTO> salvarUsuario(@RequestBody @Valid UsuarioForm usuarioForm) {
		
		Usuario novoUsuario = usuarioForm.converterParaUsuario(perfilRepository);
		this.usuarioRepository.save(novoUsuario);
		UsuarioDTO conteudoDeRetornoDoUsuario = new UsuarioDTO(novoUsuario);
		
		return ResponseEntity.ok(conteudoDeRetornoDoUsuario);
		
	}
	
	
	@DeleteMapping("/{nomeDeUsuario}")
	@Transactional
	public ResponseEntity<?> removerUsuario(@PathVariable String nomeDeUsuario){
		
		Optional<Usuario> optionalTopico = this.usuarioRepository.findById(nomeDeUsuario);
		
		if(optionalTopico.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		this.usuarioRepository.deleteById(nomeDeUsuario);
		
		return ResponseEntity.noContent().build();
		
	}
	
	
	
}
