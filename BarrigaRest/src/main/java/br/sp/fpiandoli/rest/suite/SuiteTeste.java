package br.sp.fpiandoli.rest.suite;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.sp.fpiandoli.rest.core.BaseTest;
import br.sp.fpiandoli.tests.refact.AuthTest;
import br.sp.fpiandoli.tests.refact.ContasTest;
import br.sp.fpiandoli.tests.refact.MovimentacaoTest;
import br.sp.fpiandoli.tests.refact.SaldoTest;

@RunWith(Suite.class)
@SuiteClasses({
	ContasTest.class,
	MovimentacaoTest.class,
	SaldoTest.class,
	AuthTest.class
})
public class SuiteTeste extends BaseTest {

	@BeforeClass
	public static void login() {
			Map <String,String> login = new HashMap<>();
			
			login.put("email", "fpiandoli@gmail.com");
			login.put("senha", "12345");
			
			String TOKEN = given()
				.body(login)
			.when()
				.post("/signin")
			.then()
				.statusCode(200)
				.extract().path("token");
			
			requestSpecification.header("Authorization","JWT "+TOKEN);
			get("/reset").then().statusCode(200);
		
	}
	
	
}
