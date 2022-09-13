import entities.Booking;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import static io.restassured.RestAssured.get;
import static io.restassured.path.json.JsonPath.from;


public class GetBookingEndpointTest {

    private static RestfulBookerAPI api;
    private static int bookingId;

    @BeforeClass
    public static void createTestEnvironment() {
        api = new RestfulBookerAPI("https://restful-booker.herokuapp.com");
        List<Integer> listBookingIds = api.getBookingIds();
        bookingId = (int) (Math.random() * (listBookingIds.size())) + 1;
    }

    @Test
    public void userCanGetBookingWithValidID() {
        get("https://restful-booker.herokuapp.com/booking/"+bookingId).then().statusCode(200);
    }

    @Test
    public void userCannotGetBookingWithInvalidID() {
        get("https://restful-booker.herokuapp.com/booking/"+0).then().statusCode(404);
    }

    @Test
    public void dataOfBookingIsCorrect() {
        String response = get("https://restful-booker.herokuapp.com/booking/"+bookingId).asString();
        String firstName = from(response).getString("firstname");
        String lastName = from(response).getString("lastname");
        Assert.assertNotNull(firstName);
        Assert.assertNotNull(lastName);
    }
}
