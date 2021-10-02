package br.com.segware.postandvote.config.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.segware.postandvote.model.Usuario;
import br.com.segware.postandvote.repository.UsuarioRepository;

@Service
public class AutenticacaoService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String nomeDeUsuario) throws UsernameNotFoundException {
		
		Optional<Usuario> usuarioOptional = this.usuarioRepository.findById(nomeDeUsuario);
		
		if(usuarioOptional.isEmpty()) {
			throw new UsernameNotFoundException("Usuário não encontrado!");
		}
		
		return usuarioOptional.get();
	}
	
	
}
