import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.get;

public class HealthCheckEndpointTest {

    @Test
    public void firstAPITest(){
        get("https://restful-booker.herokuapp.com/ping").then().statusCode(201);
    }

    private static RestfulBookerAPI api;

    @BeforeClass
    public static void createTestEnvironment() {
        api = new RestfulBookerAPI("https://restful-booker.herokuapp.com");
    }

    @Test
    public void checkPing(){
        Response response = api.doPing();
        Assert.assertEquals(201, response.getStatusCode());
    }

}
