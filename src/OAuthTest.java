
import static io.restassured.RestAssured.*;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import pojo.API;
import pojo.Courses;
import pojo.GetCourse;
import pojo.Mobile;
import pojo.WebAutomation;

public class OAuthTest {
	
	// Deserialize JSON response into POJO class with Rest Assured

	@Test
    public void validateCourseTitles() {
		
		// Step 1: Obtain an access token from the authorization server		
		String response = given()
		.formParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.formParam("grant_type", "client_credentials")
		.formParam("scope", "trust")
		.when().log().all()
		.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();
		
		// Print the response from the authorization server
		System.out.println(response);
		
		// Step 2: Extract the access token from the response
		JsonPath js = new JsonPath(response);
		String accessToken = js.getString("access_token");
		
		// Step 3: Use the access token to fetch course details using a POJO class
		GetCourse gc = given()
		.queryParam("access_token", accessToken)
		.when().log().all()
		.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourse.class);  // Deserialize JSON response into POJO class
		
		
		// Print retrieved course details
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getInstructor());
		
		// Expected course titles
        String[] expectedWebAutomationCourses = {"Selenium Webdriver Java", "Cypress", "Protractor"};
        String[] expectedApiCourses = {"Rest Assured Automation using Java", "SoapUI Webservices testing"};
        String[] expectedMobileCourses = {"Appium-Mobile Automation using Java"};

        // Fetching all courses
        Courses courses = gc.getCourses();

        // Asserting Web Automation Courses
        System.out.println("\n---- Web Automation Courses ----");
        List<WebAutomation> webCourses = courses.getWebAutomation();
        for (int i = 0; i < webCourses.size(); i++) {
            String courseTitle = webCourses.get(i).getCourseTitle();
            System.out.println(courseTitle);
            Assert.assertEquals(courseTitle, expectedWebAutomationCourses[i], "Mismatch in Web Automation Course!");
        }

        // Asserting API Courses
        System.out.println("\n---- API Courses ----");
        List<API> apiCourses = courses.getApi();
        for (int i = 0; i < apiCourses.size(); i++) {
            String courseTitle = apiCourses.get(i).getCourseTitle();
            System.out.println(courseTitle);
            Assert.assertEquals(courseTitle, expectedApiCourses[i], "Mismatch in API Course!");
        }

        // Asserting Mobile Courses
        System.out.println("\n---- Mobile Courses ----");
        List<Mobile> mobileCourses = courses.getMobile();
        for (int i = 0; i < mobileCourses.size(); i++) {
            String courseTitle = mobileCourses.get(i).getCourseTitle();
            System.out.println(courseTitle);
            Assert.assertEquals(courseTitle, expectedMobileCourses[i], "Mismatch in Mobile Course!");
        }

        // Finding and verifying price of "SoapUI Webservices testing" in API courses
        System.out.println("\n---- Validating Price of Specific API Course ----");
        for (API apiCourse : apiCourses) {
            if (apiCourse.getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
                System.out.println("Price: " + apiCourse.getPrice());
                Assert.assertNotNull(apiCourse.getPrice(), "Price for SoapUI Webservices testing should not be null!");
            }
        }	
		

	}

}
