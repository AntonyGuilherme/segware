package br.com.segware.postandvote.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.segware.postandvote.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${forum.jwt.expiration}")
	private String tempoDeDuracao;
	
	@Value("${forum.jwt.secret}")
	private String segredoParaGeracaoDoToken;
	
	
	public String gerarToken(Authentication autenticacao) {
		
		Usuario usuarioLogado = (Usuario) autenticacao.getPrincipal();
		
		Date dataAtual = new Date();
		
		Long tempoAtualEmMiliSegundos = dataAtual.getTime();
		Long tempoDeDuracaoConvertido = Long.parseLong(tempoDeDuracao);
		
		Date dataExpiracao = new Date(tempoAtualEmMiliSegundos + tempoDeDuracaoConvertido);
		
		return Jwts.builder()
				.setIssuer("API do Fórum da Alura")
				.setSubject(usuarioLogado.getId().toString())
				.setIssuedAt(dataAtual)
				.setExpiration(dataExpiracao)
				.signWith(SignatureAlgorithm.HS256, segredoParaGeracaoDoToken)
				.compact();
		
	}
	
	
	public boolean isTokenValido(String token) {
		try {
			
			Jwts.parser()
			.setSigningKey(this.segredoParaGeracaoDoToken)
			.parseClaimsJws(token);
			
			return true;
		} catch (Exception e) {
			
			return false;
		
		}
	}

	public Long getIdUsuario(String token) {
		
		Claims claims = Jwts.parser()
				.setSigningKey(this.segredoParaGeracaoDoToken)
				.parseClaimsJws(token).getBody();
		
		return Long.parseLong(claims.getSubject());
	}
	
	
	
	
}
