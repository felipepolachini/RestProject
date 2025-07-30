package br.sp.fpiandoli.rest;

import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

import static io.restassured.RestAssured.*;

public class FileTest {
	
	@Test
	public void MandatoryFile () {
		given()
			.log().all()
		.when()
			.post("https://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(404)
			.body("error", is("Arquivo não enviado"));
	}
	
	@Test
	public void uploadFile () {
		given()
			.log().all()
			.multiPart("arquivo",new File("src/main/resources/usersPdf.pdf"))
		.when()
			.post("https://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(200)
			.body("name",is("usersPdf.pdf"));
	}
	
	@Test
	public void uploadFileLitte () {
		given()
			.log().all()
			.multiPart("arquivo",new File("src/main/resources/usersPdf.pdf"))
		.when()
			.post("https://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.time(lessThan(2000L))
			.statusCode(200);
	}
	
	@Test
	public void donwloadFile () throws IOException {
		byte[] image = given()
			.log().all()
		.when()
			.get("https://restapi.wcaquino.me/download")
		.then()
			//.log().all()
			.statusCode(200)
			.extract().asByteArray();
		
		File imagem = new File("src/main/resources/file.jpeg");
		FileOutputStream out = new FileOutputStream(imagem);
		out.write(image);
		out.close();
	}

}
