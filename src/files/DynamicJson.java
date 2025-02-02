package files;

import static io.restassured.RestAssured.*;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson {
	
	@Test(dataProvider="BookData")
	public void addBook(String isbn, String aisle)
	{
		RestAssured.baseURI="http://216.10.245.166";
		
		String response = given()
		.header("Content-Type", "application/json")
		.body(payload.Addbook(isbn,aisle))
		.when().post("Library/Addbook.php")
		.then().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js = ReUsableMethods.rawToJson(response);
		String id = js.get("ID");
		System.out.println(id);
		
		
		// Call deleteBook test after adding the book
		deleteBook(id);
	}
	
		//DeleteBook Test
		public void deleteBook(String bookID)
		{
			System.out.println("Deleting Book ID: " + bookID);
			
			String deleteResponse = given()
			.header("Content-Type", "application/json")
			.body("{ \"ID\" : \"" + bookID + "\" }")
			.when().post("Library/DeleteBook.php")
			.then().assertThat().statusCode(200)
			.extract().response().asString();
			
			JsonPath js = ReUsableMethods.rawToJson(deleteResponse);
			String message = js.getString("msg");
			System.out.println("Delete Response: " + message);
		}
	
	
	@DataProvider(name = "BookData")
	public Object[][] getData()
	{
		//array = collection of elements
		//Multidimensional array = collection of arrays		
		return new Object[][] {{"dofod","5434"},{"iofgo","5656"},{"dfhhg","6878"}};
	}

}
