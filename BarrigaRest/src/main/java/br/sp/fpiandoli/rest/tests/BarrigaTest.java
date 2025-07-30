package br.sp.fpiandoli.rest.tests;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.sp.fpiandoli.rest.core.BaseTest;
import br.sp.fpiandoli.utils.DateUtils;
import io.restassured.specification.FilterableRequestSpecification;

import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BarrigaTest extends BaseTest{

	private static String ACCOUNT_NAME = "Conta" + System.nanoTime();
	private static Integer ACCOUNT_ID;
	private static Integer MOV_ID;
	
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
		
	}

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
	
	@Test
	public void t02_includeAccountSuccesfully() {
		ACCOUNT_ID = given()
			.body("{\"nome\":\""+ACCOUNT_NAME+"\"}")
		.when()
			.post("/contas")
		.then()
			.statusCode(201)
			.extract().path("id");
	}
	
	@Test
	public void t03_editAccountSuccesfully() {
		given()
			.body("{\"nome\":\""+ACCOUNT_NAME+ "Editada\"}")
			.pathParam("id", ACCOUNT_ID)
		.when()
			.put("/contas/{id}")
		.then()
		.log().all()
			.statusCode(200)
			.body("nome", is(ACCOUNT_NAME+"Editada"));
	}
	
	@Test
	public void t04_includeAccountIncluded () {
		given()
			.body("{\"nome\":\""+ACCOUNT_NAME+"\"}")
		.when()
			.post("/contas")
		.then()
			.statusCode(400)
			.body("error", is("Já existe uma conta com esse nome!"));
	}
	
	@Test
	public void t05_includeMovimention () {
		Movimentacao mov = getMovimentacao();
		
		MOV_ID = given()
			.body(mov)
		.when()
			.post("/transacoes")
		.then()
			.statusCode(201)
			.extract().path("id");
	}
	
	@Test
	public void t06_includeMovimentionObgFields () {
		given()
			.body("{}")
		.when()
			.post("/transacoes")
		.then()
			.statusCode(400)
			.body("$",hasSize(8))
			.body("msg",hasItems(
						"Data da Movimentação é obrigatório",
						"Data do pagamento é obrigatório",
						"Descrição é obrigatório",
						"Interessado é obrigatório",
						"Valor é obrigatório",
						"Valor deve ser um número",
						"Conta é obrigatório",
						"Situação é obrigatório"
					));
	}
	
	@Test
	public void t07_includeMovimentionFutureDate () {
		Movimentacao mov = getMovimentacao();
		mov.setData_transacao(DateUtils.getDate(2));
		
		given()
			.body(mov)
		.when()
			.post("/transacoes")
		.then()
			.statusCode(400)
			.body("$",hasSize(1))
			.body("msg", hasItem("Data da Movimentação deve ser menor ou igual à data atual"));
	}
	
	@Test
	public void t08_deleteContaComMov() {
		
		given()
			.pathParam("id", ACCOUNT_ID)
		.when()
			.delete("/contas/{id}")
		.then()
			.statusCode(500);
	}
	
	@Test
	public void t09_calculateSaldo() {
		
		given()
		.when()
			.get("/saldo")
		.then()
			.statusCode(200)
			.body("find{it.conta_id = "+ACCOUNT_ID+"}.saldo", is("600.00"));
	}
	
	@Test
	public void t10_removeMov () {
		
		given()
			.pathParam("id", MOV_ID)
		.when()
			.delete("/transacoes/{id}")
		.then()
			.statusCode(204);
	}
	
	private Movimentacao getMovimentacao() {
		
		Movimentacao mov = new Movimentacao ();
		mov.setConta_id(ACCOUNT_ID);
		//mov.setUsuario_id(id);
		mov.setDescricao("desc movimentacao");
		mov.setEnvolvido("envolvido mov");
		mov.setTipo("REC");
		mov.setData_transacao(DateUtils.getDate(-1));
		mov.setData_pagamento(DateUtils.getDate(5));
		mov.setValor(100f);
		mov.setStatus(true);
		
		return mov;
	}
}
	



