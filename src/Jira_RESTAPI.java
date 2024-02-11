import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;

public class Jira_RESTAPI {
	
	public static void main(String[] args) {
		
		//Login into Jira
		RestAssured.baseURI="http://localhost:8080";
		SessionFilter session=new SessionFilter();
		String response=given().header("Content-Type","application/json").body("{\r\n"
				+ "    \"username\": \"tejaramesh\",\r\n"
				+ "    \"password\": \"Teja@1234\"\r\n"
				+ "}").log().all().filter(session).when().post("/rest/auth/1/session").
		then().log().all().extract().response().asString();
		
		String expectedMessage ="Hii How are you?";
		
	//Add comment into an issue	
	String commentdetails=given().pathParam("key", "10019").log().all().header("Content-Type","application/json").body("{\r\n"
				+ "    \"body\": \""+expectedMessage+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}").filter(session)
	.when().post("/rest/api/2/issue/{key}/comment")
	.then().log().all().assertThat().statusCode(201).extract().response().asString();
	
	JsonPath js=new JsonPath(commentdetails);
	String commentid=js.getString("id");
	
	
	//Add attachment
	given().header("X-Atlassian-Token","no-check").filter(session).pathParam("key","10019").
	header("Content-Type","multipart/form-data").
	multiPart("file", new File("testing.txt")).
	when().post("/rest/api/2/issue/{key}/attachments").
	then().log().all().assertThat().statusCode(200);
	
	//get issue
	String issuedetails=given().log().all().filter(session).pathParam("key", "10019").queryParam("fields","comment").when().get("/rest/api/2/issue/{key}").
	then().log().all().extract().response().asString(); 
	System.out.println(issuedetails);
	
	JsonPath js1=new JsonPath(issuedetails);
	
	int commentsCount =js1.getInt("fields.comment.comments.size()");
	for(int i=0;i<commentsCount;i++)
	{
		String commentIdIssue=js1.get("fields.comment.comments["+i+"].id").toString();
		if(commentIdIssue.equalsIgnoreCase(commentid))
		{
			
			String message=js1.get("fields.comment.comments["+i+"].body.").toString();
			System.out.println("message");
			Assert.assertEquals(message, expectedMessage);
		}
	}
	
	}

}