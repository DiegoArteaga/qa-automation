import entities.Auth;
import entities.Booking;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class UpdateBookingEndpointTest {

    private static RestfulBookerAPI api;

    @BeforeClass
    public static void createTestEnvironment() {
        api = new RestfulBookerAPI("https://restful-booker.herokuapp.com");
    }
    @Test
    public void updateBooking() {
        String username = "admin";
        String password = "password123";
        Auth auth = new Auth(username,password);
        String token = api.auth(auth);

        List<Integer> bookingList = api.getBookingIds();
        int random = (int) (Math.random() * (bookingList.size()) + 1);
        System.out.println("This is the random number: " + random);

        Booking booking = api.getBookingById(random);
        booking.setFirstname("Pedro");
        booking.setLastname("Pascal");

        Response response = api.updateBooking(booking, token, bookingList.get(random));
        response.then().log().all();
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertEquals("Names do not match", response.then().extract().path("firstname"), booking.getFirstname());
        Assert.assertEquals("LastNames do not match", response.then().extract().path("lastname"), booking.getLastname());
    }
}
