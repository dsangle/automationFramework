package com.automationFramework.apiTests;


import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.automationFramework.helper.LoggerHelper;
import com.automationFramework.testBase.Config;
import com.automationFramework.testBase.TestBase;
import com.automationFramework.testUtil.AddUser;
import com.automationFramework.testUtil.Excel_reader;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
/**
 * 
 * @author sangale_d
 *
 */
public class AddUserTest extends TestBase{
	

	private final Logger log = LoggerHelper.getLogger(AddUserTest.class);
	Excel_reader reader;
	
		
	@DataProvider
	public Object[][] getData(){
		 reader = new Excel_reader();
		Object testData[][] = reader.getExcelData(reader.apiExcellocation,reader.UserDetails);
		return testData;
	}
	
	
	@SuppressWarnings("unchecked")
	@Test(dataProvider="getData",description="Validate adding new user ")
	public void addUserTest(String name, String job, String statusLine) {
		
		log.info(AddUserTest.class.getName()+" started");
		
		Config config = new Config(OR);
		RestAssured.baseURI = config.getServiceUrl();
		
		RequestSpecification httpRequest = RestAssured.given();
		
		log.info("Get name and job details "+ name + " "+ job);
		AddUser user = new AddUser();
		user.setName(name);
		user.setJob(job);

		log.info("Send job and name details as body");
		JSONObject jsonob = new JSONObject();
		jsonob.put("name", name);
		jsonob.put("job", job);

		httpRequest.body(jsonob.toJSONString());
		Response resp = httpRequest.request(Method.POST, RestAssured.baseURI);

		log.info("validate status code and status line..");
		int stCode = resp.getStatusCode();
		Assert.assertEquals(stCode, reader.RESPONSE_CODE_201);
		
		String actStatusLine = resp.getStatusLine();
		Assert.assertEquals(actStatusLine, statusLine);
		
	}
	
}
