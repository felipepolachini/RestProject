package br.sp.fpiandoli.tests.refact;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import br.sp.fpiandoli.rest.core.BaseTest;

public class SaldoTest extends BaseTest {

	@Test
	public void t09_calculateSaldo() {
		Integer ACCOUNT_ID = getContaNome("Conta para saldo");
		
		given()
		.when()
			.get("/saldo")
		.then()
			.statusCode(200)
			.body("find{it.conta_id = "+ACCOUNT_ID+"}.saldo", is("100.00"));
	}
	
	public Integer getContaNome(String nome) {
		return get("/contas?nome="+nome).then().extract().path("id[0]");
	} 
}
