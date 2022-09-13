import entities.Booking;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class Refactor_GetBookingEnpointTest {

    private static RestfulBookerAPI api;
    private static int bookingId;

    @BeforeClass
    public static void createTestEnvironment() {
        api = new RestfulBookerAPI("https://restful-booker.herokuapp.com");
        List<Integer> listBookingIds = api.getBookingIds();
        bookingId = (int) (Math.random() * (listBookingIds.size())) + 1;
    }

    @Test
    public void getBookingById() {
        Booking booking = api.getBookingById(bookingId);

        Assert.assertNotNull("Booking is null",booking);
        Assert.assertNotNull(booking.getFirstname());
        Assert.assertNotNull(booking.getLastname());
        Assert.assertTrue(booking.getTotalprice()>0);
    }
}
