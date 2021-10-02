package br.com.segware.postandvote.factory;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class RequisicaoFactory implements ItemParaRequisicaoFactory<RequestBuilder> {

	private URI uri;
	private MediaType tipoDeConteudoEnviado;
	
	public RequisicaoFactory(
			String url, 
			String formularioNoFormatoJSON,
			MediaType tipoDeConteudoEnviado
			) throws Exception {
		
		this.uri = new URI(url);
		
		
	}
	
	
	@Override
	public RequestBuilder criarInstancia() {
		return null;
	}

	
	
	
	
}
