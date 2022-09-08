package http;

import entities.Auth;
import entities.Booking;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class HttpMessageSender {

    private String url;

    public HttpMessageSender(String url) {
        this.url = url;
    }

    public Response getRequestToEndpoint(String endpoint) {
        String requestURL = url + endpoint;
        Response response =
                        when().
                        get(requestURL).
                        andReturn();

        return response;
    }

    public Response postRequestToEndpoint(String msg, String endpoint) {
        String requestURL = url + endpoint;
        Response response =
                given().
                        contentType(ContentType.JSON).
                        body(msg).log().all().
                        when().
                        post(requestURL).
                        andReturn();

        return response;
    }

    public Response postRequestToEndpoint(Booking booking, String endpoint) {
        String requestURL = url + endpoint;
        return
                given().
                        contentType(ContentType.JSON).
                        body(booking).
                        when().
                        post(requestURL).
                        andReturn();
    }

    public Response postRequestToEndpoint(Auth credentials, String endpoint) {
        String requestURL = url + endpoint;
        return
                given().
                        contentType(ContentType.JSON).
                        body(credentials).
                        when().
                        post(requestURL).
                        andReturn();
    }

    public Response putRequestToEndpoint(Booking booking, String token, String endpoint){
        String requestURL = url + endpoint;
        return
                given().
                        body(booking).
                        contentType(ContentType.JSON).
                        cookie("token",token).log().all().
                        when().
                        put(requestURL).
                        andReturn();
    }

    public Response deleteRequestToEndpoint(String token, String endpoint) {
        String requestURL = url + endpoint;
        return
                given().
                        contentType(ContentType.JSON).
                        cookie("token",token).log().all().
                        when().
                        delete(requestURL).
                        andReturn();
    }
}
