import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.List;

import static io.restassured.RestAssured.get;


public class GetBookingIdsEndpointTest {

    @Test
    public void getBookingIds(){
        List<String> bookingIdList = get("https://restful-booker.herokuapp.com/booking").path("bookingid");
        Assert.assertTrue(bookingIdList.size()>0);
    }

    @Test
    public void getBookingIdsOtherTests(){
        Response response = get("https://restful-booker.herokuapp.com/booking").andReturn();
        Assert.assertEquals(response.statusCode(),200);
        List<String> bookingIdList = response.path("bookingid");
        Assert.assertTrue(bookingIdList.size()>0);
    }

    private static RestfulBookerAPI api;

    @BeforeClass
    public static void createTestEnvironment() {
        api = new RestfulBookerAPI("https://restful-booker.herokuapp.com");
    }

    @Test
    public void getBookingIdsOtherImplementation(){
        List<Integer> listBookingIds = api.getBookingIds();
        boolean areItemsPositive = true;
        Assert.assertTrue("The list of ids is empty",listBookingIds.size()>0);
        for(Integer bookingId: listBookingIds){
            if(bookingId <=0){
                areItemsPositive=false;
                break;
            }
        }
        Assert.assertTrue("An id is negative",areItemsPositive);
    }
}
