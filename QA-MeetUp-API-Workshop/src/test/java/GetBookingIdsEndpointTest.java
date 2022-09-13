import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.List;

import static io.restassured.RestAssured.get;


public class GetBookingIdsEndpointTest {

    @Test
    public void userCanGetListOfIds(){
        List<String> bookingIdList = get("https://restful-booker.herokuapp.com/booking").path("bookingid");
        Assert.assertTrue(bookingIdList.size()>0);
    }

    @Test
    public void invalidBookingIdsEndpointReturns404(){
        get("https://restful-booker.herokuapp.com/bookings").then().statusCode(404);
    }

    @Test
    public void userCanGetListOfIds_Refactor(){
        Response response = get("https://restful-booker.herokuapp.com/booking").andReturn();
        Assert.assertEquals(response.statusCode(),200);
        List<String> bookingIdList = response.path("bookingid");
        Assert.assertTrue(bookingIdList.size()>0);
    }
}
