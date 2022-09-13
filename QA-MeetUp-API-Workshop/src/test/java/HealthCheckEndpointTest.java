import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.get;

public class HealthCheckEndpointTest {

    @Test
    public void userCanSendRequestToPingEndpoint(){
        get("https://restful-booker.herokuapp.com/ping").then().statusCode(201);
    }

    @Test
    public void invalidEndpointReturns404(){
        get("https://restful-booker.herokuapp.com/weapi/ping").then().statusCode(404);
    }
}
