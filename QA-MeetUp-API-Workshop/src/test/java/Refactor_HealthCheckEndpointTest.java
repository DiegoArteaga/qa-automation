import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class Refactor_HealthCheckEndpointTest {

    private static RestfulBookerAPI api;

    @BeforeClass
    public static void createTestEnvironment() {
        api = new RestfulBookerAPI("https://restful-booker.herokuapp.com");
    }

    @Test
    public void userCanSendRequestToPingEndpoint(){
        Response response = api.doPing();
        Assert.assertEquals(201, response.getStatusCode());
    }
}
