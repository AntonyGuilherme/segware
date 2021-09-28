package br.com.segware.postandvote.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.segware.postandvote.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByNomeDeUsuario(String nomeDeUsuario);
	
	
}
