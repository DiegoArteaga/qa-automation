package entities;

import java.util.Date;

public class BookingDates {

    private Date checkin;
    private Date checkout;

    public BookingDates(Date checkin, Date checkout) {
        this.checkin = checkin;
        this.checkout = checkout;
    }

    public Date getCheckin() {
        return checkin;
    }

    public Date getCheckout() {
        return checkout;
    }
}
