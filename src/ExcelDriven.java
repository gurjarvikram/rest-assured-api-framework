import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.testng.annotations.Test;

import files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import resources.DataDriven;


public class ExcelDriven {

	@Test
	public void addBook() throws IOException {
		
		DataDriven d = new DataDriven();
		ArrayList data = d.getData("RestAddBook","RestAssured");

		HashMap<String, Object>  map = new HashMap<>();
		map.put("name", data.get(1));
		map.put("isbn", data.get(2));
		map.put("aisle", data.get(3));
		map.put("author", data.get(4));
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		Response resp = given()
				.header("Content-Type", "application/json")
				.body(map)
				.when().post("/Library/Addbook.php")
				.then().assertThat().statusCode(200).extract().response();
		JsonPath js= ReUsableMethods.rawToJson(resp);
		   String id=js.get("ID");
		   System.out.println(id);		


	}

}
