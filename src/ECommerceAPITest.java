import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import pojo.LoginRequest;
import pojo.LoginResponse;
import pojo.OrderDetail;
import pojo.Orders;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

public class ECommerceAPITest {

    // Define a constant for the base URL to avoid repetition
    private static final String BASE_URI = "https://rahulshettyacademy.com/";
    
    public static void main(String[] args) {

        // ================================
        // Step 1: User Login - Authenticate & Retrieve Token
        // ================================
        
        // Create request specification for login
        RequestSpecification req = new RequestSpecBuilder().setBaseUri(BASE_URI)
                .setContentType(ContentType.JSON).build();

        // Create a POJO object with login credentials
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail("testvk1@gmail.com");
        loginRequest.setUserPassword("Admin@123?");

        // Perform login request with relaxed HTTPS validation (for SSL bypass)
        RequestSpecification reqLogin = given().relaxedHTTPSValidation().log().all().spec(req).body(loginRequest);

        // Extract response as LoginResponse POJO
        LoginResponse loginResponse = reqLogin.when().post("/api/ecom/auth/login")
                .then().log().all().extract().response().as(LoginResponse.class);

        // Retrieve and store authentication token and user ID
        String token = loginResponse.getToken();
        String userId = loginResponse.getUserId();
        System.out.println("Authentication Token: " + token);
        System.out.println("User ID: " + userId);

        
        // ================================
        // Step 2: Add Product - Uploading a Product
        // ================================

        // Create request specification for adding a product, including authentication
        RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri(BASE_URI)
                .addHeader("Authorization", token).build();

        // Define product details including an image file
        RequestSpecification reqAddProduct = given().log().all().spec(addProductBaseReq)
                .param("productName", "Laptop")
                .param("productAddedBy", userId)
                .param("productCategory", "fashion")
                .param("productSubCategory", "shirts")
                .param("productPrice", "11500")
                .param("productDescription", "HP")
                .param("productFor", "men")
                .multiPart("productImage", new File("/home/b/Desktop/API-Contract/lappy-testing.jpg"));

        // Perform product addition request
        String addProductResponse = reqAddProduct.when().post("/api/ecom/product/add-product")
                .then().log().all().extract().response().asString();

        // Extract the product ID from response
        JsonPath js = new JsonPath(addProductResponse);
        String productId = js.get("productId");
        System.out.println("Product ID: " + productId);

        
        // ================================
        // Step 3: Create Order - Placing an Order with the Product
        // ================================

        // Create request specification for order creation with authentication
        RequestSpecification createOrderBaseReq = new RequestSpecBuilder().setBaseUri(BASE_URI)
                .addHeader("Authorization", token).setContentType(ContentType.JSON).build();

        // Create Order Detail object and set product information
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setCountry("India");
        orderDetail.setProductOrderedId(productId);

        // Create list to store order details
        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderDetailList.add(orderDetail);

        // Create Orders object and set the list of order details
        Orders orders = new Orders();
        orders.setOrders(orderDetailList);

        // Send request to create an order
        RequestSpecification createOrderReq = given().log().all().spec(createOrderBaseReq).body(orders);

        // Capture and print the order response
        String responseAddOrder = createOrderReq.when().post("/api/ecom/order/create-order")
                .then().log().all().extract().response().asString();
        System.out.println("Order Response: " + responseAddOrder);

        
        // ================================
        // Step 4: Delete Product - Removing the Product
        // ================================

        // Create request specification for deleting a product
        RequestSpecification deleteProdBaseReq = new RequestSpecBuilder().setBaseUri(BASE_URI)
                .addHeader("Authorization", token).setContentType(ContentType.JSON).build();

        // Configure request to delete the product using the extracted product ID
        RequestSpecification deleteProdReq = given().log().all().spec(deleteProdBaseReq)
                .pathParam("productId", productId);

        // Perform DELETE request to remove the product
        String deleteProductResponse = deleteProdReq.when()
                .delete("/api/ecom/product/delete-product/{productId}")
                .then().log().all().extract().response().asString();

        // Extract response message and validate deletion
        JsonPath js1 = new JsonPath(deleteProductResponse);
        Assert.assertEquals(js1.get("message"), "Product Deleted Successfully");

    }
}
