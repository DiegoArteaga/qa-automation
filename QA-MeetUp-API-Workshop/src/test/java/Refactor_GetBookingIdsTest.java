import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class Refactor_GetBookingIdsTest {

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
