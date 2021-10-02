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

import br.com.segware.postandvote.controller.form.UsuarioForm;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UsuarioControllerTest {

	@Autowired
	private MockMvc mockMVC;

	@Value("${forum.authentication.cliente.token}")
	private String tokenParaAutenticacaoDoCliente;
	
	@Value("${forum.authentication.admin.token}")
	private String tokenParaAutenticacaoDoAdmin;

	@Test
	public void deveriaSalvarOUsuario() {

		try {

			URI uri = new URI("/usuarios");
			
			UsuarioForm usuario = new UsuarioForm();
			usuario.setNomeDeUsuario("usuario");
			usuario.setSenha("123456");
			usuario.setIdPerfil(1l);
			String formularioNoFormatoJSON = new ObjectMapper().writeValueAsString(usuario);
			
			mockMVC.perform(MockMvcRequestBuilders.post(uri)
					.content(formularioNoFormatoJSON)
					.contentType(MediaType.APPLICATION_JSON)
					.header("Authorization", this.tokenParaAutenticacaoDoAdmin))
					.andExpect(MockMvcResultMatchers.status()
					.is(HttpStatus.OK.value()))
					.andExpect(MockMvcResultMatchers.jsonPath("$.nomeDeUsuario").isNotEmpty())
					.andExpect(MockMvcResultMatchers.jsonPath("$.perfis").isArray());

		} catch (Exception e) {

			fail(e.getMessage());

		}

	}
	
	@Test
	public void naoDeveriaPermitirSalvarOUsuario() {

		try {

			URI uri = new URI("/usuarios");
			
			UsuarioForm usuario = new UsuarioForm();
			usuario.setNomeDeUsuario("usuario");
			usuario.setSenha("123456");
			usuario.setIdPerfil(1l);
			String formularioNoFormatoJSON = new ObjectMapper().writeValueAsString(usuario);
			
			mockMVC.perform(MockMvcRequestBuilders.post(uri)
					.content(formularioNoFormatoJSON)
					.contentType(MediaType.APPLICATION_JSON)
					.header("Authorization", this.tokenParaAutenticacaoDoCliente))
					.andExpect(MockMvcResultMatchers.status()
					.is(HttpStatus.FORBIDDEN.value()));

		} catch (Exception e) {

			fail(e.getMessage());

		}

	}
	
	
	@Test
	public void deveriaPermitirBuscarPelosUsuarios() {

		try {

			URI uri = new URI("/usuarios");
			
			mockMVC.perform(MockMvcRequestBuilders.get(uri)
					.header("Authorization", this.tokenParaAutenticacaoDoAdmin))
					.andExpect(MockMvcResultMatchers.status()
					.is(HttpStatus.OK.value()))
					.andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray());

		} catch (Exception e) {

			fail(e.getMessage());

		}

	}
	
	
	
	@Test
	public void naoDeveriaPermitirBuscarPelosUsuarios() {

		try {

			URI uri = new URI("/usuarios");
			
			mockMVC.perform(MockMvcRequestBuilders.get(uri)
					.header("Authorization", this.tokenParaAutenticacaoDoCliente))
					.andExpect(MockMvcResultMatchers.status()
					.is(HttpStatus.FORBIDDEN.value()));

		} catch (Exception e) {

			fail(e.getMessage());

		}

	}
	
	
	@Test
	public void deveriaPermitirExcluirOUsuario() {

		try {
			
			URI uri = new URI("/usuarios");

			UsuarioForm usuario = new UsuarioForm();
			usuario.setNomeDeUsuario("usuario_exc");
			usuario.setSenha("123456");
			usuario.setIdPerfil(1l);
			String formularioNoFormatoJSON = new ObjectMapper().writeValueAsString(usuario);
			
			 mockMVC.perform(MockMvcRequestBuilders.post(uri)
					.content(formularioNoFormatoJSON)
					.contentType(MediaType.APPLICATION_JSON)
					.header("Authorization", this.tokenParaAutenticacaoDoAdmin))
					.andExpect(MockMvcResultMatchers.status()
					.is(HttpStatus.OK.value()))
					.andReturn();
			
		
			System.out.println("/usuarios/"+usuario.getNomeDeUsuario());
			
			URI uriDelete = new URI("/usuarios/"+usuario.getNomeDeUsuario());
			mockMVC.perform(MockMvcRequestBuilders.delete(uriDelete)
					.header("Authorization", this.tokenParaAutenticacaoDoAdmin))
					.andExpect(MockMvcResultMatchers.status()
					.is(HttpStatus.NO_CONTENT.value()));

		} catch (Exception e) {

			fail(e.getMessage());

		}

	}
	
	
	@Test
	public void naoDeveriaPermitirExcluirOUsuario() {

		try {
			
			
			URI uri = new URI("/usuarios/cliente");
			mockMVC.perform(MockMvcRequestBuilders.delete(uri)
					.header("Authorization", this.tokenParaAutenticacaoDoCliente))
					.andExpect(MockMvcResultMatchers.status()
					.is(HttpStatus.FORBIDDEN.value()));

		} catch (Exception e) {

			fail(e.getMessage());

		}

	}
	

	@Test
	public void naoDeveriaPermitirExcluirUmUsuarioInexistente() {

		try {
			
			URI uri = new URI("/usuarios/inexistente");

			
			mockMVC.perform(MockMvcRequestBuilders.delete(uri)
					.header("Authorization", this.tokenParaAutenticacaoDoAdmin))
					.andExpect(MockMvcResultMatchers.status()
					.is(HttpStatus.NOT_FOUND.value()));

		} catch (Exception e) {

			fail(e.getMessage());

		}

	}


}