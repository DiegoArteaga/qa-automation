import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import entities.Auth;
import entities.Booking;
import http.HttpMessageSender;
import io.restassured.response.Response;

import java.util.List;

public class RestfulBookerAPI {
    private final String url;
    private final HttpMessageSender messageSender;
    private JsonParser parser = new JsonParser();


    public RestfulBookerAPI(String url) {
        this.url = url;
        this.messageSender = new HttpMessageSender(url);
    }

    public List<Integer> getBookingIds() {
        Response response = messageSender.getRequestToEndpoint("/booking");

        List<Integer> listIds = response.then().extract().path("bookingid");

        return listIds;
    }

    public Response doPing(){
        Response response = messageSender.getRequestToEndpoint("/ping");
        return  response;
    }

    public Booking getBookingById(int id) {
        Response response = messageSender.getRequestToEndpoint("/booking/"+id);
        JsonElement jsonResponse = parser.parse(response.body().asString());

        Booking booking = new Gson().fromJson(jsonResponse, Booking.class);
        System.out.println("This is the name of the booking: " + booking.getFirstname() + " " + booking.getLastname());

        return booking;
    }

    public Response createBooking(Booking booking) {
        return messageSender.postRequestToEndpoint(booking,"/booking");
    }

    public String auth(Auth credentials) {

        Response response = messageSender.postRequestToEndpoint(credentials,"/auth");
        String token = response.path("token");

        return token;
    }

    public Response updateBooking(Booking booking, String token, int index) {
        return messageSender.putRequestToEndpoint(booking, token,"/booking/"+index);
    }

    public Response deleteBooking(String token, int index) {
        return messageSender.deleteRequestToEndpoint(token, "/booking/"+index);
    }

    public Response updateBookingWithPatch(Booking booking, String token, int index){
        return messageSender.patchRequestToEndpoint(booking,token,"/booking/"+index);
    }
}
