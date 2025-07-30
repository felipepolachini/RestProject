package br.sp.fpiandoli.tests.refact;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import br.sp.fpiandoli.rest.core.BaseTest;
import io.restassured.specification.FilterableRequestSpecification;

public class AuthTest extends BaseTest {

	@Test
	public void t01_accessFailsNoToken() {
		FilterableRequestSpecification req = (FilterableRequestSpecification) requestSpecification;
		req.removeHeader("Authorization");
		given()
		.when()
			.get("/contas")
		.then()
			.statusCode(401);
	}
	
}
