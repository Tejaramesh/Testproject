package pojo;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

public class SerializeTest {
	public static void main(String[] args) {
		AddPlace pl=new AddPlace();
		pl.setAccuracy(50);
		pl.setAddress("29, side layout, cohen 09");
		pl.setLanguage("French-IN");
		pl.setName("Frontline house");
		pl.setPhone_number("(+91) 983 893 3937");
		pl.setWebsite("http://google.com");
		List<String> mytypes=new ArrayList<String>();
		mytypes.add("shoe park");
		mytypes.add("shop");
		pl.setTypes(mytypes);
		Location l=new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		pl.setLocation(l);
		
		RequestSpecification res = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
		.setContentType(ContentType.JSON).build();
		
		ResponseSpecification resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		RequestSpecification resp=given().spec(res).body(pl);
		
		Response response=resp.when().post("/maps/api/place/add/json")
		.then().spec(resspec).extract().response();
		String output = response.asString();
		System.out.println(output);
		
		
	}

}
