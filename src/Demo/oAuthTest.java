package Demo;
import static io.restassured.RestAssured.given;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.restassured.path.json.JsonPath;
public class oAuthTest {
	public static void main(String[] args) throws InterruptedException {
	
		//System.setProperty("webdriver.chrome.driver", "C:\\Users\\pc\\eclipse-workspace\\Testproject\\reference\\chromedriver.exe");
		//WebDriver driver=new ChromeDriver();
		
		//driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
		//driver.findElement(By.cssSelector("input[type='email']")).sendKeys("tejaramesh2000@gmail.com");
		//driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
		//Thread.sleep(4000);
		//driver.findElement(By.cssSelector("input[type='password']")).sendKeys("Teja@1234");
		//driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
		//Thread.sleep(8000);
		//String url=driver.getCurrentUrl();
		
		String url="https://rahulshettyacademy.com/getCourse.php?code=4%2F0AfJohXmCF3H4L0wZHlmQpPKWDgBraaS1G3vCdMvG1gfiK7V62Ht36CNRAGFvxlUU_s5Zeg&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
		
		
		
		String partialcode=url.split("code=")[1];
		String code=partialcode.split("&scope")[0];
		
	
		
		String AccessTokenResponse = given().urlEncodingEnabled(false)
				.queryParam("code", code)
		.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.queryParam("grant_type", "authorization_code")
		.when().log().all()
		.post("https://www.googleapis.com/oauth2/v4/token").asString();
		JsonPath js=new JsonPath(AccessTokenResponse);
		String AccessToken = js.getString("access_token");
		
		
		
		String response = given().queryParam("access_token", AccessToken)
		.when().log().all().get("https://rahulshettyacademy.com/getCourse.php").asString();
		
	System.out.println(response);
	}

}
