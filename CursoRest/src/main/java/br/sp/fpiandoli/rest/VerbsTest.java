package br.sp.fpiandoli.rest;

import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import io.restassured.http.ContentType;


import static io.restassured.RestAssured.*;

public class VerbsTest {
	
	@Test
	public void saveUser () {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"name\":\"Jose\",\"age\":50}")
		.when()
			.post("https://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("Jose"))
			.body("age", is(50));
	}

	@Test
	public void noSaveNoNameUser () {
			given()
				.log().all()
				.contentType("application/json")
				.body("{\"age\":50}")
			.when()
				.post("https://restapi.wcaquino.me/users")
			.then()
				.log().all()
				.statusCode(400)
				.body("id", is(nullValue()))
				.body("error", is("Name é um atributo obrigatório"));
	}
	
	@Test
	public void saveUserXML () {
		given()
			.log().all()
			.contentType(ContentType.XML)
			.body("<user><name>Dr Jose</name><age>50</age></user>")
		.when()
			.post("https://restapi.wcaquino.me/usersXML")
		.then()
			.log().all()
			.statusCode(201)
			.body("user.@id", is(notNullValue()))
			.body("user.name", is("Dr Jose"))
			.body("user.age", is("50"));
	}
	
	@Test
	public void editUser () {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"name\":\"Pedro\",\"age\":80}")
		.when()
			.put("https://restapi.wcaquino.me/users/1")
		.then()
			.log().all()
			.statusCode(200)
			.body("id", is(1))
			.body("name", is("Pedro"))
			.body("age", is(80));
	}
	
	@Test
	public void editUserCustomURL () {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"name\":\"Pedro\",\"age\":80}")
			.pathParam("entity", "users")
			.pathParam("userId", 1)
		.when()
			.put("https://restapi.wcaquino.me/{entity}/{userId}")
		.then()
			.log().all()
			.statusCode(200)
			.body("id", is(1))
			.body("name", is("Pedro"))
			.body("age", is(80));
	}
	
	@Test
	public void deleteUser () {
		given()
			.log().all()
		.when()
			.delete("https://restapi.wcaquino.me/users/1")
		.then()
			.log().all()
			.statusCode(204);
	}
	
	@Test
	public void saveUserMap () {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("name", "Pedro Map");
		params.put("age",25);
		
		given()
			.log().all()
			.contentType("application/json")
			.body(params)
		.when()
			.post("https://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("Pedro Map"))
			.body("age", is(25));
	}
	
	@Test
	public void saveUserObj () {
		User user = new User("Pedro Obj",35); 
	
		given()
			.log().all()
			.contentType("application/json")
			.body(user)
		.when()
			.post("https://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("Pedro Obj"))
			.body("age", is(35));
	}
	
	@Test
	public void saveUserDeserializaObj () {
		User user = new User("Pedro DesObj",35); 
	
		User insertedUser = given()
			.log().all()
			.contentType("application/json")
			.body(user)
		.when()
			.post("https://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201)
			.extract().body().as(User.class);
		
		System.out.println(insertedUser);
		Assert.assertThat(insertedUser.getId(), notNullValue());
		Assert.assertEquals("Pedro DesObj", insertedUser.getName());
		Assert.assertSame(35, insertedUser.getAge());		
	}
	
	@Test
	public void saveUserXMLObj () {
		User user = new User("Pedro Obj XML",40); 
		
		given()
			.log().all()
			.contentType(ContentType.XML)
			.body(user)
		.when()
			.post("https://restapi.wcaquino.me/usersXML")
		.then()
			.log().all()
			.statusCode(201)
			.body("user.@id", is(notNullValue()))
			.body("user.name", is("Pedro Obj XML"))
			.body("user.age", is("40"));
	}
	
	@Test
	public void saveUserXMLDesObj () {
		User user = new User("Pedro Obj XML",40); 
		
		User insertedUser = given()
			.log().all()
			.contentType(ContentType.XML)
			.body(user)
		.when()
			.post("https://restapi.wcaquino.me/usersXML")
		.then()
			.log().all()
			.statusCode(201)
			.extract().body().as(User.class);
		
		System.out.println(insertedUser);
		Assert.assertThat(insertedUser.getId(), notNullValue());
		Assert.assertEquals("Pedro Obj XML", insertedUser.getName());
		Assert.assertSame(40, insertedUser.getAge());
	}
}


	

