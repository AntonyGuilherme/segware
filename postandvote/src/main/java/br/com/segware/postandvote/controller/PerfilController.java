package br.com.segware.postandvote.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.segware.postandvote.model.Perfil;
import br.com.segware.postandvote.repository.PerfileRepository;

@RestController()
@RequestMapping("/perfis")
public class PerfilController {

	@Autowired
	private PerfileRepository perfileRepository;
	
	@GetMapping
	public Page<Perfil> buscarTodosOsPerfis(@PageableDefault(
			sort = "id", direction = Direction.DESC ,
			page = 0, size = 3
			) Pageable paginacao) {
		
		return this.perfileRepository.findAll(paginacao);
		
	}
	
	
}
