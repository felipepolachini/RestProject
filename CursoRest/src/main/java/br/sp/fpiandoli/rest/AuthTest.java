package br.sp.fpiandoli.rest;

import org.junit.Test;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

public class AuthTest {
	
	@Test
	public void accessSWAPI () {
		given()
			.log().all()
		.when()
			.get("https://swapi.co/api/people/1")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", is("Luke Skywalker"));
	}
	
	@Test
	public void getWheather() {
		given()
				.log().all()
				.queryParam("q", "Fortaleza,BR")
				.queryParam("appid", "99999999")
				.queryParam("units", "metric")
		.when()
			.get("https://api.openweathermap.org/data/2.5/weather")
		.then()
			.log().all()
			.statusCode(200);
	
	}
	
	@Test
	public void accessSucess () {
		
		given()
			.log().all()
		.when()
			.get("https://admin:senha@restapi.wcaquino.me/basicauth")
		.then()
			.log().all()
			.statusCode(200);
	}
	
	@Test
	public void accessSucess2 () {
		given()
			.log().all()
			.auth().basic("admin", "senha")
		.when()
			.get("https://restapi.wcaquino.me/basicauth")
		.then()
			.log().all()
			.statusCode(200);
	}
	
	@Test
	public void accessSucessChallenge () {
		given()
			.log().all()
			.auth().preemptive().basic("admin", "senha")
		.when()
			.get("https://restapi.wcaquino.me/basicauth2")
		.then()
			.log().all()
			.statusCode(200);
	}
	
	@Test
	public void authToken() {
		Map<String,String> login = new HashMap<String,String>();
		login.put("email","fpiandoli@gmail.com");
		login.put("senha", "12345");
		
		String token = given()
			.log().all()
			.body(login)
			.contentType(ContentType.JSON)
		.when()
			.post("https://barrigarest.wcaquino.me/signin")
		.then()
			.log().all()
			.statusCode(200)
			.extract().path("token");
		
		given()
			.log().all()
			.header("Authorization","JWT " + token)
		.when()
			.get("https://barrigarest.wcaquino.me/contas")
		.then()
			.log().all()
			.statusCode(200)
			.body("nome", hasItem("Teste 2"));
	}
	
	@Test
	public void accessAppWeb () {
		String cookie = given()
			.log().all()
			.formParam("email", "fpiandoli@gmail.com")
			.formParam("senha", "12345")
			.contentType(ContentType.URLENC.withCharset("UTF-8"))
		.when()
			.post("https://seubarriga.wcaquino.me/logar")
		.then()
			.log().all()
			.statusCode(200)
			.extract().header("set-cookie");
		
		cookie = cookie.split("=")[1].split(";")[0];
		System.out.println(cookie);
		
		given()
			.log().all()
			.cookie("connect.sid",cookie)
		.when()
			.get("https://seubarriga.wcaquino.me/contas")
		.then()
			.log().all()
			.statusCode(200)
			.body("html.body.table.tbody.tr[0].td[0]",is("Teste 2"));
		
	}
	


}
