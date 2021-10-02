package br.com.segware.postandvote.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PerfilControllerTest {

	@Autowired
	private MockMvc mockMVC;

	@Value("${forum.authentication.cliente.token}")
	private String tokenParaAutenticacaoDoCliente;
	
	@Value("${forum.authentication.admin.token}")
	private String tokenParaAutenticacaoDoAdmin;

	@Test
	public void naoDeveriaPermitirBuscarTodosOsPerfisDisponiveis() {

		try {

			URI uri = new URI("/perfis");
			
			mockMVC.perform(MockMvcRequestBuilders.get(uri)
					.header("Authorization", this.tokenParaAutenticacaoDoCliente))
					.andExpect(MockMvcResultMatchers.status()
					.is(HttpStatus.FORBIDDEN.value()));

		} catch (Exception e) {

			fail(e.getMessage());

		}

	}
	
	@Test
	public void deveriaBuscarTodosOsPerfisDisponiveis() {

		try {

			URI uri = new URI("/perfis");
			
			mockMVC.perform(MockMvcRequestBuilders.get(uri)
					.header("Authorization", this.tokenParaAutenticacaoDoAdmin))
					.andExpect(MockMvcResultMatchers.status()
					.is(HttpStatus.OK.value()))
					.andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray());

		} catch (Exception e) {

			fail(e.getMessage());

		}

	}


}
