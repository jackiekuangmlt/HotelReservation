package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation {
    private Customer customer;
    private IRoom room;
    private Date checkInDate;
    private Date checkOutDate;
    private SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

    public  Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){

        this.customer=customer;
        this.room=room;
        this.checkInDate=checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public IRoom getRoom(){
        return room;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public String toString(){
        return  customer + " will stay here: from" + formatter.format(checkInDate) + " to "
                + formatter.format(checkOutDate) + "\nThe reserved room is: " + room;
    }


}
