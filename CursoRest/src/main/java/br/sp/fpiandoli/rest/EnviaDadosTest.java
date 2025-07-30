package br.sp.fpiandoli.rest;

import static org.hamcrest.Matchers.*;
import org.junit.Test;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;

public class EnviaDadosTest {
	
	@Test
	public void sendValueQuery () {
		given()
			.log().all()
		.when()
			.get("https://restapi.wcaquino.me/v2/users?format=xml")
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.XML);
	}
	
	@Test
	public void sendValueQueryByParam () {
		given()
			.log().all()
			.queryParam("format", "xml")
		.when()
			.get("https://restapi.wcaquino.me/v2/users")
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.XML)
			.contentType(containsString("utf"));
	}
	
	@Test
	public void sendValueQueryByHeader () {
		given()
			.log().all()
			.accept(ContentType.JSON)
		.when()
			.get("https://restapi.wcaquino.me/v2/users")
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.JSON);
	}

}
