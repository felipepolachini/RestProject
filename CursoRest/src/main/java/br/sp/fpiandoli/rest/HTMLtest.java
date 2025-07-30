package br.sp.fpiandoli.rest;

import static org.hamcrest.Matchers.*;
import org.junit.Test;

import org.hamcrest.Matchers;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;

public class HTMLtest {
	
	@Test
	public void SearchHTML () {
		given()
			.log().all()
		.when()
			.get("https://restapi.wcaquino.me/v2/users")
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.HTML)
			.body("html.body.div.table.tbody.tr.size()", is(3))
			.body("html.body.div.table.tbody.tr[1].td[2]", is("25"))
			.body("html.body.div.table.tbody.tr.find{it.toString().startsWith('2')}.td[1]",is("Maria Joaquina"));
	}
	
	@Test
	public void SearchHTMLwithXPATH () {
		given()
			.log().all()
		.when()
			.get("https://restapi.wcaquino.me/v2/users")
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.HTML)
			.body(hasXPath("count(//table/tr)", is("3")));
		}

}
