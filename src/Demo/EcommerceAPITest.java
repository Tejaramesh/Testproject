package Demo;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.LoginResponse;
import pojo.Loginrequest;
import pojo.OrderDetails;
import pojo.Orders;
import pojo.ResponsecreateProduct;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
public class EcommerceAPITest {

	public static void main(String[] args) {
		Loginrequest log=new Loginrequest();
		log.setUserEmail("tejaramesh2000@gmail.com");
		log.setUserPassword("Teja@1234");
		
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();
		RequestSpecification requestLogin = given().log().all().spec(req).body(log);
		
		LoginResponse finalres = requestLogin.when().post("/api/ecom/auth/login").then().log().all().extract().response().as(LoginResponse.class);
		
		System.out.println(finalres.getToken());
		String token = finalres.getToken();
		
		System.out.println(finalres.getUserId());
		String userId = finalres.getUserId();
		
		System.out.println(finalres.getMessage());
		
		RequestSpecification reqAddProduct = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token).build();
		ResponseSpecification respro = new ResponseSpecBuilder().expectStatusCode(201).build();
		
		RequestSpecification reqpro = given().log().all().spec(reqAddProduct)
		.param("productName", "Laptop").param("productAddedBy",userId).param("productCategory", "Electronic and accessories")
		.param("productSubCategory", "electronic items").param("productPrice", "75000").param("productDescription", "Lenovo Brand")
		.param("productFor", "IT Employee").multiPart("productImage",new File("C:\\Users\\pc\\Downloads\\Lenovophots\\laptop.jpg"));
		
		ResponsecreateProduct resproduct = reqpro.when().post("/api/ecom/product/add-product")
		.then().log().all().spec(respro).extract().as(ResponsecreateProduct.class);
		System.out.println(resproduct.getProductId());
		String productid = resproduct.getProductId();
		System.out.println(resproduct.getMessage());
		
			//create a order
		 RequestSpecification orderreqbase = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token)
		 .setContentType(ContentType.JSON).build();
		OrderDetails orddetails=new OrderDetails();
		orddetails.setCountry("India");
		orddetails.setProductOrderedId(productid);
		List<OrderDetails> orddetailslist= new ArrayList<OrderDetails>();
		orddetailslist.add(orddetails);
		Orders order=new Orders();
		order.setOrders(orddetailslist);
		RequestSpecification createorderreq = given().spec(orderreqbase).body(order);
		 String orderes = createorderreq.when().post("/api/ecom/order/create-order")
		 .then().log().all().extract().response().asString();
 
 //delete order
 
 RequestSpecification delteorderbase = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token)
 .setContentType(ContentType.JSON).build();
 RequestSpecification deleteorderreq = given().log().all().spec(delteorderbase).pathParam("productOrderId", productid);
 String deleteres = deleteorderreq.when().delete("/api/ecom/product/delete-product/{productOrderId}")
		            .then().log().all().extract().response().asString();
 JsonPath js=new JsonPath(deleteres);
Assert.assertEquals("Product Deleted Successfully", js.get("message"));
}
}

