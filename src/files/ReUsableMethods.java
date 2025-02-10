package files;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ReUsableMethods {
	
	public static JsonPath rawToJson(Response resp)
	{
		String respon=resp.asString();
		JsonPath js1 = new JsonPath(respon);
		System.out.println(respon);
		return js1;
	}

}
