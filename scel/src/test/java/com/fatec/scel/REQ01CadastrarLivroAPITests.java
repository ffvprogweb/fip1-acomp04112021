package com.fatec.scel;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fatec.scel.mantemLivro.model.Livro;

import com.google.gson.Gson;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class REQ01CadastrarLivroAPITests {
	String urlBase = "/api/v1/livros";
	@Autowired
	TestRestTemplate testRestTemplate;
	

	@Test
	void ct01_cadastrar_livro_com_sucesso() {
//Dado – que o atendente tem um livro não cadastrado
		Livro livro = new Livro("3333", "User Stories", "Cohn");
		Gson dadosDeEntrada = new Gson();
		String entity = dadosDeEntrada.toJson(livro);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<String>(entity, headers);
//Quando – o atendente cadastra um livro com informações válidas
		ResponseEntity<String> resposta = testRestTemplate.exchange(urlBase, HttpMethod.POST, httpEntity, String.class);
//Então – o sistema valida os dados e permite a consulta do livro
		assertEquals("201 CREATED", resposta.getStatusCode().toString());
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		String bodyEsperado = "{\"id\":1,\"isbn\":\"3333\",\"titulo\":\"User Stories\",\"autor\":\"Cohn\"}";
		assertEquals(bodyEsperado, resposta.getBody());
	}
}
