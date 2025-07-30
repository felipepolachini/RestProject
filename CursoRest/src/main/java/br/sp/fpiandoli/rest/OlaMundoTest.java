package br.sp.fpiandoli.rest;



import static org.hamcrest.Matchers.*;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class OlaMundoTest {
	
	@Test
	public void testOlaMundo() {
		Response response = request(Method.GET, "https://restapi.wcaquino.me/ola");
		Assert.assertTrue(response.getBody().asString().equals("Ola Mundo!"));
		Assert.assertTrue("O status code deveria ser 200",response.statusCode() == 200);
		Assert.assertEquals(200,response.statusCode());
		
		
		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);
	}
	
	@Test
	public void OutrasFormas () {
		Response response = request(Method.GET, "https://restapi.wcaquino.me/ola");
		
		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);
		
		get("https://restapi.wcaquino.me/ola").then().statusCode(200);
		
		given()
		.when()
			.get("https://restapi.wcaquino.me/ola")
		.then().statusCode(200);
	}
	
	@Test
	public void MatchersHamcrest () {
		assertThat("Maria", is("Maria"));
		assertThat(128, is(128));
		assertThat(128, isA(Integer.class));
		assertThat(128, greaterThan(120));
		
		List <Integer> impares = Arrays.asList(1,3,5,7,9);
		
		assertThat(impares, hasSize(5));
		assertThat(impares, hasSize(5));
		assertThat(impares, hasItem(1));
		assertThat(impares, hasItems(1,5));
		
		assertThat("Maria", not("Joao"));
		assertThat("Maria", anyOf(is("Joao"),is("Maria")));
		assertThat("Joaquina", allOf(startsWith("Joa"),endsWith("ina")));
	}
	
	@Test
	public void ValidaBody () {
		given()
		.when()
			.get("https://restapi.wcaquino.me/ola")
		.then()
			.statusCode(200)
			.body(is("Ola Mundo!"))
			.body(containsString("Mundo"))
			.body(not(nullValue()));
	}

}
