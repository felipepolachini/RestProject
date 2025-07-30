package br.sp.fpiandoli.rest.core;

import org.junit.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class BaseTest implements Consts {

	@BeforeClass
	public static void setup () {
		baseURI = APP_BASE_URL;
		port = APP_PORT;
		basePath = APP_BASE_PATH;
		
		RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
		reqBuilder.setContentType(APP_CONTENT_TYPE);
		requestSpecification = reqBuilder.build();
		
		ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
		resBuilder.expectResponseTime(lessThan(MAX_TIMEOUT));
		responseSpecification = resBuilder.build();
		
		enableLoggingOfRequestAndResponseIfValidationFails();
		
	}
}
