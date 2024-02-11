package Demo;
import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.Courses;
import pojo.WebAutomation;
import pojo.getcoursedetails;

public class Oauth2_test {
	public static void main(String[] args) {
		
		String[] courseTitles= {"Selenium Webdriver Java", "Cypress", "Protractor"};
		
		String response=given().
		formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
       .formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
       .formParam("grant_type", "trust")
       .when().log().all()
       .post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();
		//System.out.println(response);
		JsonPath js=new JsonPath(response);
		String finalresp =js.getString("access_token");
		//System.out.println(finalresp);
		
		getcoursedetails ob=given()
		.queryParam("access_token", finalresp)
		.when().log().all()
		.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(getcoursedetails.class);
		System.out.println(ob.getLinkedIn());
		System.out.println(ob.getInstructor());
		
	List<Api> apicourses= ob.getCourses().getApi();
	List<WebAutomation> webcourses = ob.getCourses().getWebAutomation();
	for(int i=0;i<apicourses.size();i++)
	{
		if(apicourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
		{
			System.out.println(apicourses.get(i).getPrice());
		}
	}
	
	ArrayList<String> a=new ArrayList<String>();
	for(int i=0;i<webcourses.size();i++)
	{
		a.add(webcourses.get(i).getCourseTitle());
			
	}
	
List<String> expectedresult = Arrays.asList(courseTitles);

	Assert.assertTrue(a.equals(expectedresult));
	
}
}