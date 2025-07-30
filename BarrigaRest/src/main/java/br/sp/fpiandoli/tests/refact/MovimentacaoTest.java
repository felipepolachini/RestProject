package br.sp.fpiandoli.tests.refact;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import br.sp.fpiandoli.rest.core.BaseTest;
import br.sp.fpiandoli.rest.tests.Movimentacao;
import br.sp.fpiandoli.utils.DateUtils;

public class MovimentacaoTest extends BaseTest {
	
	@Test
	public void t05_includeMovimention () {
		Movimentacao mov = getMovimentacao();
		
		given()
			.body(mov)
		.when()
			.post("/transacoes")
		.then()
			.statusCode(201);
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
		Integer ACCOUNT_ID = getContaNome("Conta com movimentacao");
		
		given()
			.pathParam("id", ACCOUNT_ID)
		.when()
			.delete("/contas/{id}")
		.then()
			.statusCode(500);
	}
	
	@Test
	public void t10_removeMov () {
		
		given()
			.pathParam("id", getMovNome("Movimentacao para exclusao"))
		.when()
			.delete("/transacoes/{id}")
		.then()
			.statusCode(204);
	}
	
	public Integer getContaNome(String nome) {
		return get("/contas?nome="+nome).then().extract().path("id[0]");
	} 
	
	public Integer getMovNome(String nome) {
		return get("/transacoes?descricao="+nome).then().extract().path("id[0]");
	} 
	
	private Movimentacao getMovimentacao() {
			
			Movimentacao mov = new Movimentacao ();
			mov.setConta_id(getContaNome("Conta para movimentacoes"));
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
