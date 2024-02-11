package pojo;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

public class SpecbuildTest {
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
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		String res=given().queryParam("key", "qaclick123").body(pl)
		.when().post("/maps/api/place/add/json").asString();	
		System.out.println(res);
	}

}
