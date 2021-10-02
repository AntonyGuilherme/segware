package br.com.segware.postandvote.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.segware.postandvote.controller.dto.PosteDTO;
import br.com.segware.postandvote.controller.form.PosteForm;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PosteControllerTest {

	
	@Autowired
	private MockMvc mockMVC;
	
	@Value("${forum.authentication.cliente.token}")
	private String tokenParaAutenticacaoDoCliente;
	
	@Value("${forum.authentication.admin.token}")
	private String tokenParaAutenticacaoDoAdmin;
	
	@Value("${forum.authentication.admin.username}")
	private String nomeDeUsuarioAdmin;
	
	
	
	@Test
	public void deveriaSalvarUmPoste() {
		
		try {

			URI uri = new URI("/poste");
			
			PosteForm formularioPoste = new PosteForm();
			formularioPoste.setTexto("TEXTO PARA TESTE");
			String formularioNoFormatoJSON = new ObjectMapper().writeValueAsString(formularioPoste);
			
			mockMVC.perform(MockMvcRequestBuilders.post(uri)
					.content(formularioNoFormatoJSON)
					.contentType(MediaType.APPLICATION_JSON)
					.header("Authorization", this.tokenParaAutenticacaoDoAdmin))
					.andExpect(MockMvcResultMatchers.status()
					.is(HttpStatus.OK.value()))
					.andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
					.andExpect(MockMvcResultMatchers.jsonPath("$.nomeDeUsuario").isNotEmpty())
					.andExpect(MockMvcResultMatchers.jsonPath("$.texto").isNotEmpty())
					.andExpect(MockMvcResultMatchers.jsonPath("$.numeroDeAprovacoes").isNumber());

		} catch (Exception e) {

			fail(e.getMessage());

		}
		
	}
	
	@Test
	public void naoDeveriaPermitirSalvarUmPosteComTextoInvalido() {
		
		try {

			URI uri = new URI("/poste");
			
			PosteForm formularioPoste = new PosteForm();
			formularioPoste.setTexto("");
			String formularioNoFormatoJSON = new ObjectMapper().writeValueAsString(formularioPoste);
			
			mockMVC.perform(MockMvcRequestBuilders.post(uri)
					.content(formularioNoFormatoJSON)
					.contentType(MediaType.APPLICATION_JSON)
					.header("Authorization", this.tokenParaAutenticacaoDoAdmin))
					.andExpect(MockMvcResultMatchers.status()
					.is(HttpStatus.BAD_REQUEST.value()));

		} catch (Exception e) {

			fail(e.getMessage());

		}
		
	}
	
	
	@Test
	public void naoDeveriaPermitirSalvarUmPosteComUmUsuarioNaoAutenticado() {
		
		try {

			URI uri = new URI("/poste");
			
			PosteForm formularioPoste = new PosteForm();
			formularioPoste.setTexto("");
			String formularioNoFormatoJSON = new ObjectMapper().writeValueAsString(formularioPoste);
			
			mockMVC.perform(MockMvcRequestBuilders.post(uri)
					.content(formularioNoFormatoJSON)
					.contentType(MediaType.APPLICATION_JSON)
					.header("Authorization", "TOKEN_INVALIDO"))
					.andExpect(MockMvcResultMatchers.status()
					.is(HttpStatus.FORBIDDEN.value()));

		} catch (Exception e) {

			fail(e.getMessage());

		}
		
	}
	
	
	@Test
	public void deveriaBuscarPorTodosOsPostes() {
		
		try {

			URI uri = new URI("/poste");
			
			PosteForm formularioPoste = new PosteForm();
			formularioPoste.setTexto("TEXTO PARA TESTE");
			String formularioNoFormatoJSON = new ObjectMapper().writeValueAsString(formularioPoste);
			
			mockMVC.perform(MockMvcRequestBuilders.post(uri)
					.content(formularioNoFormatoJSON)
					.contentType(MediaType.APPLICATION_JSON)
					.header("Authorization", this.tokenParaAutenticacaoDoAdmin))
			.andReturn();
			
			mockMVC.perform(MockMvcRequestBuilders.get(uri)
					.header("Authorization", this.tokenParaAutenticacaoDoAdmin))
					.andExpect(MockMvcResultMatchers.status()
					.is(HttpStatus.OK.value()))
					.andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
					.andExpect(MockMvcResultMatchers.jsonPath("$.content[*].id").exists())
					.andExpect(MockMvcResultMatchers.jsonPath("$.content[*].texto").exists())
					.andExpect(MockMvcResultMatchers.jsonPath("$.content[*].nomeDeUsuario").exists())
					.andExpect(MockMvcResultMatchers.jsonPath("$.content[*].numeroDeAprovacoes").exists());

		} catch (Exception e) {

			fail(e.getMessage());

		}
		
		
	}
	
	@Test
	public void naoDeveriaPermitirBuscarPorTodosOsPostesComUmUsuarioNaoAutenticado() {
		
		try {

			URI uri = new URI("/poste");
			
			
			mockMVC.perform(MockMvcRequestBuilders.get(uri)
					.header("Authorization", "TOKEN_INVALIDO"))
					.andExpect(MockMvcResultMatchers.status()
					.is(HttpStatus.FORBIDDEN.value()));

		} catch (Exception e) {

			fail(e.getMessage());

		}
		
		
	}
	
	
	
	@Test
	public void deveriaBuscarPorTodosOsPostesDoUsuario() {
		
		try {

			String comparacaoDoNomeDeUsuario = "$.content[?(@.nomeDeUsuario != '"+this.nomeDeUsuarioAdmin+"')]";
			
			URI uriBucarPostes = new URI("/poste/usuario");
			URI uriSalvarPoste =  new URI("/poste");
			PosteForm formularioPoste = new PosteForm();
			formularioPoste.setTexto("TEXTO PARA TESTE");
			String formularioNoFormatoJSON = new ObjectMapper().writeValueAsString(formularioPoste);
			
			mockMVC.perform(MockMvcRequestBuilders.post(uriSalvarPoste)
					.content(formularioNoFormatoJSON)
					.contentType(MediaType.APPLICATION_JSON)
					.header("Authorization", this.tokenParaAutenticacaoDoAdmin))
			.andReturn();
			
			String response  = mockMVC.perform(MockMvcRequestBuilders.get(uriBucarPostes)
					.header("Authorization", this.tokenParaAutenticacaoDoAdmin))
					.andExpect(MockMvcResultMatchers.status()
					.is(HttpStatus.OK.value()))
					.andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
					.andExpect(MockMvcResultMatchers.jsonPath("$.content[*]['id']").exists())
					.andExpect(MockMvcResultMatchers.jsonPath("$.content[*]['texto']").exists())
					.andExpect(MockMvcResultMatchers.jsonPath(comparacaoDoNomeDeUsuario).doesNotExist())
					.andExpect(MockMvcResultMatchers.jsonPath("$.content[*].numeroDeAprovacoes").exists())
					.andReturn().getResponse().getContentAsString();
			
			System.out.println(response);

		} catch (Exception e) {

			fail(e.getMessage());

		}
		
		
	}
	
	
	@Test
	public void naoDeveriaPermitirBuscarPorTodosOsPostesDoUsuarioComUmUsuarioNaoAutenticado() {
		
		try {

			URI uri = new URI("/poste/usuario");
			
			mockMVC.perform(MockMvcRequestBuilders.get(uri)
					.header("Authorization", "TOKEN_INVALIDO"))
					.andExpect(MockMvcResultMatchers.status()
					.is(HttpStatus.FORBIDDEN.value()));

		} catch (Exception e) {

			fail(e.getMessage());

		}
		
		
	}
	
	
	@Test
	public void deveriaDeletarPostesDoUsuario() {
		
		try {

			URI uri = new URI("/poste");
			
			PosteForm formularioPoste = new PosteForm();
			formularioPoste.setTexto("TEXTO PARA TESTE");
			String formularioNoFormatoJSON = new ObjectMapper().writeValueAsString(formularioPoste);
			
			String posteDTONoFormatoJSON = mockMVC.perform(MockMvcRequestBuilders.post(uri)
					.content(formularioNoFormatoJSON)
					.contentType(MediaType.APPLICATION_JSON)
					.header("Authorization", this.tokenParaAutenticacaoDoAdmin))
			.andReturn()
			.getResponse()
			.getContentAsString();
			
			ObjectMapper conversorJSON = new ObjectMapper();
			
			PosteDTO posteDTO = conversorJSON.readValue(posteDTONoFormatoJSON,PosteDTO.class);
			
			URI uriParaDelete = new URI("/poste/"+posteDTO.getId());
			
			mockMVC.perform(MockMvcRequestBuilders.delete(uriParaDelete)
					.header("Authorization", this.tokenParaAutenticacaoDoAdmin))
					.andExpect(MockMvcResultMatchers.status()
					.is(HttpStatus.NO_CONTENT.value()));

		} catch (Exception e) {

			fail(e.getMessage());

		}
		
	}
	
	
	@Test
	public void naoDeveriaPermitirDeletarPosteDeUmOutroUsuario() {
		
		try {

			URI uri = new URI("/poste");
			
			PosteForm formularioPoste = new PosteForm();
			formularioPoste.setTexto("TEXTO PARA TESTE");
			String formularioNoFormatoJSON = new ObjectMapper().writeValueAsString(formularioPoste);
			
			String posteDTONoFormatoJSON = mockMVC.perform(MockMvcRequestBuilders.post(uri)
					.content(formularioNoFormatoJSON)
					.contentType(MediaType.APPLICATION_JSON)
					.header("Authorization", this.tokenParaAutenticacaoDoCliente))
			.andReturn()
			.getResponse()
			.getContentAsString();
			
			ObjectMapper conversorJSON = new ObjectMapper();
			
			PosteDTO posteDTO = conversorJSON.readValue(posteDTONoFormatoJSON,PosteDTO.class);
			
			URI uriParaDelete = new URI("/poste/"+posteDTO.getId());
			
			mockMVC.perform(MockMvcRequestBuilders.delete(uriParaDelete)
					.header("Authorization", this.tokenParaAutenticacaoDoAdmin))
					.andExpect(MockMvcResultMatchers.status()
					.is(HttpStatus.FORBIDDEN.value()));

		} catch (Exception e) {

			fail(e.getMessage());

		}
		
	}
	
	
	@Test
	public void naoDeveriaPermitirDeletarUmPosteInexistente() {
		
		try {

			
			URI uriParaDelete = new URI("/poste/9999");
			
			mockMVC.perform(MockMvcRequestBuilders.delete(uriParaDelete)
					.header("Authorization", this.tokenParaAutenticacaoDoAdmin))
					.andExpect(MockMvcResultMatchers.status()
					.is(HttpStatus.NOT_FOUND.value()));

		} catch (Exception e) {

			fail(e.getMessage());

		}
		
	}
	
	
	@Test
	public void deveriaAtualizarOPosteDoUsuario() {
		
		try {

			URI uri = new URI("/poste");
			
			PosteForm formularioPoste = new PosteForm();
			formularioPoste.setTexto("TEXTO PARA TESTE");
			String formularioNoFormatoJSON = new ObjectMapper().writeValueAsString(formularioPoste);
			
			String posteDTONoFormatoJSON = mockMVC.perform(MockMvcRequestBuilders.post(uri)
					.content(formularioNoFormatoJSON)
					.contentType(MediaType.APPLICATION_JSON)
					.header("Authorization", this.tokenParaAutenticacaoDoAdmin))
			.andReturn()
			.getResponse()
			.getContentAsString();
			
			ObjectMapper conversorJSON = new ObjectMapper();
			
			PosteDTO posteDTO = conversorJSON.readValue(posteDTONoFormatoJSON,PosteDTO.class);
			
			formularioPoste.setTexto("TEXTO_PARA_ATUALIZACAO");
			String formularioNoFormatoJSONParaAtualizacao = new ObjectMapper().writeValueAsString(formularioPoste);
			
			
			URI uriParaAtualizacao = new URI("/poste/"+posteDTO.getId());
			
			mockMVC.perform(MockMvcRequestBuilders.delete(uriParaAtualizacao)
					.content(formularioNoFormatoJSONParaAtualizacao)
					.contentType(MediaType.APPLICATION_JSON)
					.header("Authorization", this.tokenParaAutenticacaoDoAdmin))
					.andExpect(MockMvcResultMatchers.status()
					.is(HttpStatus.NO_CONTENT.value()));

		} catch (Exception e) {

			fail(e.getMessage());

		}
		
	}
	
	
	
	@Test
	public void naoDeveriaPermitirAtualizarOPosteDeUmOutroUsuario() {
		
		try {

			URI uri = new URI("/poste");
			
			PosteForm formularioPoste = new PosteForm();
			formularioPoste.setTexto("TEXTO PARA TESTE");
			String formularioNoFormatoJSON = new ObjectMapper().writeValueAsString(formularioPoste);
			
			String posteDTONoFormatoJSON = mockMVC.perform(MockMvcRequestBuilders.post(uri)
					.content(formularioNoFormatoJSON)
					.contentType(MediaType.APPLICATION_JSON)
					.header("Authorization", this.tokenParaAutenticacaoDoAdmin))
			.andReturn()
			.getResponse()
			.getContentAsString();
			
			ObjectMapper conversorJSON = new ObjectMapper();
			
			PosteDTO posteDTO = conversorJSON.readValue(posteDTONoFormatoJSON,PosteDTO.class);
			
			formularioPoste.setTexto("TEXTO_PARA_ATUALIZACAO");
			String formularioNoFormatoJSONParaAtualizacao = new ObjectMapper().writeValueAsString(formularioPoste);
			
			
			URI uriParaAtualizacao = new URI("/poste/"+posteDTO.getId());
			
			mockMVC.perform(MockMvcRequestBuilders.delete(uriParaAtualizacao)
					.content(formularioNoFormatoJSONParaAtualizacao)
					.contentType(MediaType.APPLICATION_JSON)
					.header("Authorization", this.tokenParaAutenticacaoDoCliente))
					.andExpect(MockMvcResultMatchers.status()
					.is(HttpStatus.FORBIDDEN.value()));

		} catch (Exception e) {

			fail(e.getMessage());

		}
		
	}
	
	
	@Test
	public void naoDeveriaPermitirAtualizarUmPosteInexistente() {
		
		try {
			
			PosteForm formularioPoste = new PosteForm();
			formularioPoste.setTexto("TEXTO_PARA_ATUALIZACAO");
			String formularioNoFormatoJSONParaAtualizacao = new ObjectMapper().writeValueAsString(formularioPoste);
			
			
			URI uriParaAtualizacao = new URI("/poste/9999");
			
			mockMVC.perform(MockMvcRequestBuilders.patch(uriParaAtualizacao)
					.content(formularioNoFormatoJSONParaAtualizacao)
					.contentType(MediaType.APPLICATION_JSON)
					.header("Authorization", this.tokenParaAutenticacaoDoCliente))
					.andExpect(MockMvcResultMatchers.status()
					.is(HttpStatus.NOT_FOUND.value()));

		} catch (Exception e) {

			fail(e.getMessage());

		}
		
	}
	
	
	@Test()
	public void deveriaAprovarOPoste() {
		
		try {

			URI uri = new URI("/poste");
			
			PosteForm formularioPoste = new PosteForm();
			formularioPoste.setTexto("TEXTO PARA TESTE");
			String formularioNoFormatoJSON = new ObjectMapper().writeValueAsString(formularioPoste);
			
			String posteDTONoFormatoJSON = mockMVC.perform(MockMvcRequestBuilders.post(uri)
					.content(formularioNoFormatoJSON)
					.contentType(MediaType.APPLICATION_JSON)
					.header("Authorization", this.tokenParaAutenticacaoDoAdmin))
			.andReturn()
			.getResponse()
			.getContentAsString();
			
			ObjectMapper conversorJSON = new ObjectMapper();
			
			PosteDTO posteDTO = conversorJSON.readValue(posteDTONoFormatoJSON,PosteDTO.class);
			
			URI uriParaAtualizacao = new URI("/poste/"+posteDTO.getId()+"/aprovar");
			
			mockMVC.perform(MockMvcRequestBuilders.patch(uriParaAtualizacao)
					.header("Authorization", this.tokenParaAutenticacaoDoCliente))
					.andExpect(MockMvcResultMatchers.status()
					.is(HttpStatus.OK.value()))
					.andExpect(MockMvcResultMatchers.jsonPath("$.numeroDeAprovacoes").value(1));

		} catch (Exception e) {

			fail(e.getMessage());

		}
		
		
	}
	
	
	@Test()
	public void naoDeveriaPermitirAprovarOPosteComOUsuarioDeCriacao() {
		
		try {

			URI uri = new URI("/poste");
			
			PosteForm formularioPoste = new PosteForm();
			formularioPoste.setTexto("TEXTO PARA TESTE");
			String formularioNoFormatoJSON = new ObjectMapper().writeValueAsString(formularioPoste);
			
			String posteDTONoFormatoJSON = mockMVC.perform(MockMvcRequestBuilders.post(uri)
					.content(formularioNoFormatoJSON)
					.contentType(MediaType.APPLICATION_JSON)
					.header("Authorization", this.tokenParaAutenticacaoDoAdmin))
			.andReturn()
			.getResponse()
			.getContentAsString();
			
			ObjectMapper conversorJSON = new ObjectMapper();
			
			PosteDTO posteDTO = conversorJSON.readValue(posteDTONoFormatoJSON,PosteDTO.class);
			
			URI uriParaAtualizacao = new URI("/poste/"+posteDTO.getId()+"/aprovar");
			
			mockMVC.perform(MockMvcRequestBuilders.patch(uriParaAtualizacao)
					.header("Authorization", this.tokenParaAutenticacaoDoAdmin))
					.andExpect(MockMvcResultMatchers.status()
					.is(HttpStatus.NOT_FOUND.value()));

		} catch (Exception e) {

			fail(e.getMessage());

		}
		
		
	}
	
	
	@Test()
	public void naoDeveriaAprovarUmPosteInexistente() {
		
		try {

			
			
			URI uriParaAtualizacao = new URI("/poste/9999/aprovar");
			
			mockMVC.perform(MockMvcRequestBuilders.patch(uriParaAtualizacao)
					.header("Authorization", this.tokenParaAutenticacaoDoAdmin))
					.andExpect(MockMvcResultMatchers.status()
					.is(HttpStatus.NOT_FOUND.value()));

		} catch (Exception e) {

			fail(e.getMessage());

		}
		
		
	}
	
	
	@Test()
	public void naoDeveriaAprovarUmPosteComUmUsuarioNaoAutenticado() {
		
		try {

			URI uri = new URI("/poste");
			
			PosteForm formularioPoste = new PosteForm();
			formularioPoste.setTexto("TEXTO PARA TESTE");
			String formularioNoFormatoJSON = new ObjectMapper().writeValueAsString(formularioPoste);
			
			String posteDTONoFormatoJSON = mockMVC.perform(MockMvcRequestBuilders.post(uri)
					.content(formularioNoFormatoJSON)
					.contentType(MediaType.APPLICATION_JSON)
					.header("Authorization", this.tokenParaAutenticacaoDoAdmin))
			.andReturn()
			.getResponse()
			.getContentAsString();
			
			ObjectMapper conversorJSON = new ObjectMapper();
			
			PosteDTO posteDTO = conversorJSON.readValue(posteDTONoFormatoJSON,PosteDTO.class);
			
			URI uriParaAtualizacao = new URI("/poste/"+posteDTO.getId()+"/aprovar");
			
			mockMVC.perform(MockMvcRequestBuilders.patch(uriParaAtualizacao)
					.header("Authorization", "TOKEN_INVALIDO"))
					.andExpect(MockMvcResultMatchers.status()
					.is(HttpStatus.FORBIDDEN.value()));

		} catch (Exception e) {

			fail(e.getMessage());

		}
		
		
	}
	
	
	
	
	
	
	
	

}
