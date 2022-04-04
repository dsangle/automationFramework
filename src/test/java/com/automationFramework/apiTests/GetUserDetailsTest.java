package com.automationFramework.apiTests;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.automationFramework.helper.LoggerHelper;
import com.automationFramework.testBase.Config;
import com.automationFramework.testBase.TestBase;
import com.automationFramework.testUtil.Excel_reader;
import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * @author sangale_d
 *
 */

public class GetUserDetailsTest extends TestBase {

	private final Logger log = LoggerHelper.getLogger(GetUserDetailsTest.class);
	Excel_reader reader;
	
	@DataProvider
	public Object[][] getData(){
		 reader = new Excel_reader();
		Object testData[][] = reader.getExcelData(reader.apiExcellocation,reader.UserData);
		return testData;
	}
	

	@SuppressWarnings("static-access")
	@Test(dataProvider="getData",description="Get the response and validate user details ")
	public void getUserDetailsTest(String user, String contentType, String statusLine, String email, String id) {
		log.info(GetUserDetailsTest.class.getName() + " started");
		Config config = new Config(OR);

		log.info("Make a request/execute the request:");
		RestAssured.baseURI = config.getServiceUrl();
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.request(Method.GET, "/" + "?page=2");

		log.info("Validate response body and status code");
		String responseBody = response.getBody().asString();
		Assert.assertEquals(responseBody.contains(user), true);

		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, reader.RESPONSE_CODE_200);

		log.info("Get the headers and validate status line and content-type");
		Headers headers = response.getHeaders();
		Assert.assertEquals(headers.getValue("content-type"), contentType);
		Assert.assertEquals(response.getStatusLine(), statusLine);

		log.info("Store response body in response.txt file");
		reader.storeAPIResponseBody(responseBody);

		log.info("Validate email value for user with id: "+id);
		JsonPath jsonPathValue = response.jsonPath();
		String actEmail = jsonPathValue.get("data["+id+"].email");
		Assert.assertEquals(actEmail, email);
		
	}

}