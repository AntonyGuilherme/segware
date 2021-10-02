package br.com.segware.postandvote.controller;


import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.segware.postandvote.controller.dto.PosteDTO;
import br.com.segware.postandvote.controller.form.PosteForm;
import br.com.segware.postandvote.model.Poste;

import br.com.segware.postandvote.repository.PosteRepository;
import br.com.segware.postandvote.repository.UsuarioRepository;

@RestController
@RequestMapping("/poste")
public class PostController {
	
	
	@Autowired
	private PosteRepository posteRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	@GetMapping()
	public Page<PosteDTO> pegarPostes(@PageableDefault(
			sort = "numeroDeAprovacoes", direction = Direction.ASC ,
			page = 0, size = 3
			) Pageable paginacao ) {
		
		Page<Poste> postes = posteRepository.findAll(paginacao);
		Page<PosteDTO> postesDTO = PosteDTO.converterParaPosteDTO(postes);
		return postesDTO;
	}
	
	
	@PostMapping()
	@Transactional
	public ResponseEntity<?> salvarPoste(
			@RequestBody @Valid PosteForm posteForm,
			Authentication autenticacao
			){
		
		String nomeDeUsuario = autenticacao.getName();
		Poste novoPoste = posteForm.converterParaPoste(usuarioRepository,nomeDeUsuario);
		this.posteRepository.save(novoPoste);
		PosteDTO conteudoDeRetornoDoPoste = new PosteDTO(novoPoste);		
		return ResponseEntity.ok(conteudoDeRetornoDoPoste);
		
		
	}
	
	
	@PatchMapping("{id}/aprovar")
	public ResponseEntity<PosteDTO> pegarAprovacoesPoste(
			@PathVariable("id") Long id,
			Authentication autenticacao
			){
		
		String nomeDeUsuario = autenticacao.getName();
		
		/// um usuario nao pode aprovar seu proprio poste
		Optional<Poste> possivelPoste = this.posteRepository
				.findByIdAndUsuarioNomeDeUsuarioNot(id,nomeDeUsuario);
		
		
		if(possivelPoste.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		this.posteRepository.incrementarNumeroDeAprovacoes(id);
		
		Poste poste = possivelPoste.get();
		poste.incrementarNumeroDeAprovacoes();
		
		PosteDTO posteParaRetorno = new PosteDTO(possivelPoste.get());
		
		return ResponseEntity.ok(posteParaRetorno);
		
	}
	
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> deletarPoste(
			@PathVariable("id") Long id,
			Authentication autenticacao
			){
		
		String nomeDeUsuario = autenticacao.getName();
		Optional<Poste> possivelPoste = this.posteRepository.findById(id);
		
		if(possivelPoste.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		String nomeDoUsuarioDoPoste = possivelPoste.get().getUsuario().getNomeDeUsuario();
		
		if(!nomeDeUsuario.equals(nomeDoUsuarioDoPoste)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		
		
		Long idPoste = possivelPoste.get().getId();
		
		this.posteRepository.deleteById(idPoste);
		
		return ResponseEntity.noContent().build();
		
	}
	
	
	@PatchMapping("{id}")
	@Transactional
	public ResponseEntity<?> atualizarPoste(
			@PathVariable("id") Long id,
			@RequestBody @Valid PosteForm posteForm,
			Authentication autenticacao
			){
		
		String nomeDeUsuario = autenticacao.getName();
		
		Optional<Poste> possivelPoste = this.posteRepository.findById(id);
		
		
		if(possivelPoste.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
			
		Poste poste = possivelPoste.get();
		
		if(!poste.getUsuario().getNomeDeUsuario().equals(nomeDeUsuario)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		poste.setTexto(posteForm.getTexto());
		
		return ResponseEntity.noContent().build();
		
		
	}
	
	
	@GetMapping("/usuario")
	public Page<PosteDTO> buscarPostesDoUsuario(@PageableDefault(
			sort = "numeroDeAprovacoes", direction = Direction.ASC ,
			page = 0, size = 3
			) Pageable paginacao,
			Authentication autenticacao
			) {
		
		String nomeDeUsuario = autenticacao.getName();
		
		Page<Poste> postes = posteRepository.findByUsuarioNomeDeUsuario(nomeDeUsuario,paginacao);
		Page<PosteDTO> postesDTO = PosteDTO.converterParaPosteDTO(postes);
		return postesDTO;
	}
	
	
	
	
	

}
