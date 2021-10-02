package br.com.segware.postandvote.repository;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.segware.postandvote.model.Poste;

public interface PosteRepository extends JpaRepository<Poste, Long>  {
	
	
	@Modifying
	@Query("UPDATE Poste p set p.numeroDeAprovacoes = (p.numeroDeAprovacoes + 1) WHERE p.id = :id ")
	@Transactional
	public void incrementarNumeroDeAprovacoes( @Param("id") Long id );
	
	public Optional<Poste> findByIdAndUsuarioNomeDeUsuarioNot(Long id,String nomeDeUsuario);
	
	public Page<Poste> findByUsuarioNomeDeUsuario(String nomeDeUsuario, Pageable paginacao);
	
}
