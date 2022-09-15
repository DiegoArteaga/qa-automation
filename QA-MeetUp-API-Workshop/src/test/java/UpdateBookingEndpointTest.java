import entities.Auth;
import entities.Booking;
import helpers.DataGenerator;
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
    public void userCanUpdateABooking() {
        String username = "admin";
        String password = "password123";
        Auth auth = new Auth(username,password);
        String token = api.auth(auth);

        List<Integer> bookingList = api.getBookingIds();
        int random = (int) (Math.random() * (bookingList.size()) + 1);
        System.out.println("This is the random number: " + random);

        Booking booking = api.getBookingById(random);
        booking.setFirstname(DataGenerator.createRandomString());
        booking.setLastname(DataGenerator.createRandomString());

        Response response = api.updateBooking(booking, token, bookingList.get(random));
        response.then().log().all();
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertEquals("Names do not match", response.then().extract().path("firstname"), booking.getFirstname());
        Assert.assertEquals("LastNames do not match", response.then().extract().path("lastname"), booking.getLastname());
    }


    @Test
    public void userCannotUpdateABookingUsingInvalidToken() {
        String username = "admin";
        String password = "password123";
        Auth auth = new Auth(username,password);
        String token = DataGenerator.createRandomString();

        List<Integer> bookingList = api.getBookingIds();
        int random = (int) (Math.random() * (bookingList.size()) + 1);
        System.out.println("This is the random number: " + random);

        Booking booking = api.getBookingById(random);
        booking.setFirstname(DataGenerator.createRandomString());
        booking.setLastname(DataGenerator.createRandomString());

        Response response = api.updateBooking(booking, token, bookingList.get(random));
        response.then().log().all();
        Assert.assertEquals(403, response.getStatusCode());
    }


    @Test
    public void userCannotUpdateAnInvalidBooking() {
        String username = "admin";
        String password = "password123";
        Auth auth = new Auth(username,password);
        String token = api.auth(auth);

        List<Integer> bookingList = api.getBookingIds();
        int random = (int) (Math.random() * (bookingList.size()) + 1);
        System.out.println("This is the random number: " + random);

        Booking booking = api.getBookingById(random);

        booking.setFirstname(DataGenerator.createRandomString());
        booking.setLastname(DataGenerator.createRandomString());

        Response response = api.updateBooking(booking, token, 0);
        response.then().log().all();
        Assert.assertEquals(405, response.getStatusCode());
    }



    @Test
    public void userCanUpdateABookingThroughPatch() {

        //Given the user has a valid token
        String username = "admin";
        String password = "password123";
        Auth auth = new Auth(username,password);
        String token = api.auth(auth);
        //And a valid booking that was recently modified
        List<Integer> bookingList = api.getBookingIds();
        int random = (int) (Math.random() * (bookingList.size()) + 1);
        Booking booking = api.getBookingById(random);
        booking.setFirstname(DataGenerator.createRandomString());
        booking.setLastname(DataGenerator.createRandomString());

        //When I send a request to update the booking using the new PATCH endpoint
        Response response = api.updateBookingWithPatch(booking, token, bookingList.get(random));
        response.then().log().all();

        //Then the response code is 200 OK
        Assert.assertEquals(200, response.getStatusCode());
        //And the values in the response have to match the values of the booking that were modified
        Assert.assertEquals("Names do not match", response.then().extract().path("firstname"), booking.getFirstname());
        Assert.assertEquals("LastNames do not match", response.then().extract().path("lastname"), booking.getLastname());
    }

}
