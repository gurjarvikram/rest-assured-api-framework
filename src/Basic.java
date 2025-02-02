import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import files.ReUsableMethods;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class Basic {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		  // Set base URI
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        
        //Given - all input details
        //When - submit the API  - resources, http method
        //Then - validate the response
        //Content of the file to string -> content of file can convert into Byte -> Byte data to string
        
        
        // Validate if the Add Place API is working as expected
        //Add place -> update place with new address -> Get place to validate if new address is present in response
        
        
        // Add place
        String addPlaceResponse = given().log().all()
        		.queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(payload.addPlace())  //using addPlace method for payload from another package
                //OR
                 //Content of the file to string -> content of file can convert into Byte -> Byte data to string
                //.body(new String(Files.readAllBytes(Paths.get("/home/b/Desktop/API-Contract/addPlace.json")))) 
                .when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200)
                .body("scope", equalTo("APP"))
                .header("Server", "Apache/2.4.52 (Ubuntu)")
                .extract().response().asString();

        System.out.println("Add place response: " + addPlaceResponse);

        JsonPath js = new JsonPath(addPlaceResponse); // for parsing Json
        String placeId = js.getString("place_id");

        if (placeId == null || placeId.isEmpty()) {
            throw new RuntimeException("Place ID not found in Add Place response");
        }

        System.out.println("Place ID: " + placeId);

        
        
        // Step 2: Update Place with New Address
        
        String newAddress = "Summer walk, Africa";

        given().log().all()
        		.queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body("{\n" + 
                        "  \"place_id\": \"" + placeId + "\",\n" +
                        "  \"address\": \"" + newAddress + "\",\n" +
                        "  \"key\": \"qaclick123\"\n" +
                        "}")
                .when().put("maps/api/place/update/json")
                .then().assertThat().log().all().statusCode(200)
                .body("msg",equalTo("Address successfully updated"));
    
        
        // Get place
        String GetPlaceResponse = given().log().all()
        		.queryParam("key", "qaclick123")
        		.queryParam("place_id", placeId)
                .when().get("maps/api/place/get/json")
                .then().assertThat().log().all().statusCode(200).extract().response().asString();

        JsonPath js1 = ReUsableMethods.rawToJson(GetPlaceResponse);
        String actualAddress = js1.getString("address");
        System.out.println("Actual Address: " + actualAddress);
        Assert.assertEquals(actualAddress, newAddress);

	}

}
