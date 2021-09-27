package br.com.segware.postandvote.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
public class PostController {
	
	
	
	@GetMapping()
	public ResponseEntity<String> pegarPostes() {
		return ResponseEntity.ok("Teste");
	}
	
	
	@PostMapping()
	public ResponseEntity<?> salvarPoste(String texto){
		
		System.out.println(texto);
		
		return ResponseEntity.ok().build();
	}
	
	
	@GetMapping("{id}/aprovacoes")
	public ResponseEntity<?> pegarAprovacoesPoste(@PathVariable("id") Long id){
		
		System.out.println(id);
		
		return ResponseEntity.ok().build();
		
	}
	
	

}
