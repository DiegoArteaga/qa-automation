import entities.Auth;
import helpers.DataGenerator;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class DeleteBookingEndpointTest {


    private static RestfulBookerAPI api;

    @BeforeClass
    public static void createTestEnvironment() {
        api = new RestfulBookerAPI("https://restful-booker.herokuapp.com");
    }

    @Test
    public void userCanDeleteABooking() {
        String username = "admin";
        String password = "password123";
        Auth auth = new Auth(username,password);
        String token = api.auth(auth);

        List<Integer> bookingList = api.getBookingIds();

        int random = (int) (Math.random() * (bookingList.size())+1);

        Response response = api.deleteBooking(token, bookingList.get(random));

        Assert.assertEquals("The status code is not the expected one",response.statusCode(), 201);

        List<Integer> updatedList = api.getBookingIds();

        Assert.assertFalse("The booking wasn't deleted as expected", updatedList.contains(bookingList.get(random)));
        Assert.assertTrue(updatedList.size() < bookingList.size());
    }


    @Test
    public void userCannotDeleteABookingWithInvalidToken() {
        String token = DataGenerator.createRandomString();

        List<Integer> bookingList = api.getBookingIds();

        int random = (int) (Math.random() * (bookingList.size())+1);

        Response response = api.deleteBooking(token, bookingList.get(random));
        response.then().log().all();
        Assert.assertEquals("The status code is not the expected one", response.statusCode(), 403);
    }

    @Test
    public void userCannotDeleteABookingWithInvalidTokenV1() {
        // given
        String token = DataGenerator.createRandomString();
        List<Integer> bookingList = api.getBookingIds();
        int random = (int) (Math.random() * (bookingList.size())+1);
        // when
        Response response = api.deleteBooking(token, bookingList.get(random));
        response.then().log().all();
        //then
        Assert.assertEquals("The status code is not the expected one", response.statusCode(), 403);
    }
}
