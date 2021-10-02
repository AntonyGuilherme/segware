package br.com.segware.postandvote.controller;

import static org.junit.jupiter.api.Assertions.fail;

import java.net.URI;
import java.net.URISyntaxException;

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
public class AutenticacaoControllerTest {

	@Autowired
	private MockMvc mockMVC;

	@Value("${forum.authentication.admin.username}")
	private String usernameAdmin;

	@Value("${forum.authentication.admin.password}")
	private String senhaAdmin;

	@Test
	public void deveriaLogarComSucessoCasoOsDadosDeAutenticacaoEstejamValidos() {

		try {
			URI uri = new URI("/auth");

			UsuarioForm usuario = new UsuarioForm();
			usuario.setNomeDeUsuario(this.usernameAdmin);
			usuario.setSenha(this.senhaAdmin);
			String formularioNoFormatoJSON = new ObjectMapper().writeValueAsString(usuario);

			this.mockMVC
					.perform(MockMvcRequestBuilders.post(uri).content(formularioNoFormatoJSON)
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
					.andExpect(MockMvcResultMatchers.jsonPath("$.token").isNotEmpty())
					.andExpect(MockMvcResultMatchers.jsonPath("$.tipo").isNotEmpty());

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void deveriaDevolver400CasoOsDadosDeAutenticacaoEstejamInvalidos() {

		try {
			
			URI uri = new URI("/auth");

			UsuarioForm usuario = new UsuarioForm();
			usuario.setNomeDeUsuario("usuarioInexistente");
			usuario.setSenha("123456");
			String formularioNoFormatoJSON = new ObjectMapper().writeValueAsString(usuario);

			this.mockMVC
					.perform(MockMvcRequestBuilders.post(uri).content(formularioNoFormatoJSON)
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}