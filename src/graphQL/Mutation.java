package graphQL;
import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class Mutation {
	
	public static void main(String[] args) {
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		String response = given().log().all().contentType("application/json").
		body("{\r\n"
				+ "    \"query\": \"mutation($locationName: String!, $characterName: String!, $episodeName: String!)\\n{\\n  createLocation(location:{name:$locationName, type:\\\"Asia\\\",dimension:\\\"456\\\"})\\n  {\\n    id\\n  }\\n  createCharacter(character:{name:$characterName, type:\\\"abc\\\",status:\\\"active\\\",species:\\\"gbn\\\",gender:\\\"male\\\",image:\\\"fvgb\\\",originId:6697,locationId:6697})\\n  {\\n  id  \\n  }\\n  \\n  createEpisode(episode:{name: $episodeName, air_date:\\\"2023, Feb\\\", episode:\\\"morning\\\"})\\n  {  \\n  id\\n  }\\n}\",\r\n"
				+ "    \"variables\": {\r\n"
				+ "        \"locationName\": \"India\",\r\n"
				+ "        \"characterName\": \"Kiran\",\r\n"
				+ "        \"episodeName\": \"Aspirants\"\r\n"
				+ "    }\r\n"
				+ "}")
		.when().post("/gq/graphql")
		.then().log().all().extract().response().asString();
		
		System.out.println(response);
		JsonPath js=new JsonPath(response);
		String name = js.getString("data.createLocation.id");
		System.out.println(name);
		
	}

}
