import entities.Booking;
import helpers.DataGenerator;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class CreateBookingEndpointTest {

    private static RestfulBookerAPI api;

    @BeforeClass
    public static void createTestEnvironment() {
        api = new RestfulBookerAPI("https://restful-booker.herokuapp.com");
    }

    @Test
    public void userCanCreateABooking() {
        Booking booking = DataGenerator.createRandomBooking();
        Response response = api.createBooking(booking);

        Assert.assertEquals("The status code is different than 200Ok", response.statusCode(), 200);
        String name = response.path("booking.firstname");
        Assert.assertEquals("Names do not match", name, booking.getFirstname());
    }

    @Test
    public void userCanCreateABookingImprovedTest() {
        List<Integer> bookingsInList = api.getBookingIds();
        Booking booking = DataGenerator.createRandomBooking();
        Response response = api.createBooking(booking);

        Assert.assertEquals("The status code is different than 200Ok",response.statusCode(),200);
        List<Integer> newBookingList = api.getBookingIds();
        Assert.assertTrue(newBookingList.size()>bookingsInList.size());

        int id = response.then().extract().path("bookingid");
        Booking createdBooking = api.getBookingById(id);
        Assert.assertEquals("Names do not match", booking.getFirstname(), createdBooking.getFirstname());
    }
}
