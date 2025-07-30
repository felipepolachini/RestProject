package br.sp.fpiandoli.tests.refact;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import br.sp.fpiandoli.rest.core.BaseTest;

public class ContasTest extends BaseTest {
	
	@Test
	public void t02_includeAccountSuccesfully() {
		 given()
			.body("{\"nome\":\"Conta Inserida\"}")
		.when()
			.post("/contas")
		.then()
			.statusCode(201);
	}
	
	@Test
	public void t03_editAccountSuccesfully() {
		Integer ACCOUNT_ID = getContaNome("Conta para alterar");
		given()
			.body("{\"nome\":\"Conta Editada\"}")
			.pathParam("id", ACCOUNT_ID)
		.when()
			.put("/contas/{id}")
		.then()
		.log().all()
			.statusCode(200)
			.body("nome", is("Conta Editada"));
	}
	
	@Test
	public void t04_includeAccountIncluded () {
		given()
			.body("{\"nome\":\"Conta mesmo nome\"}")
		.when()
			.post("/contas")
		.then()
			.statusCode(400)
			.body("error", is("Já existe uma conta com esse nome!"));
	}
	
	public Integer getContaNome(String nome) {
		return get("/contas?nome="+nome).then().extract().path("id[0]");
	} 
}
