package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

public class HotelResource  {

    private static final CustomerService customerService = CustomerService.getInstance();
    private static final ReservationService reservationService = ReservationService.getInstance();

    public HotelResource(){

    }

    public static Customer getCustomer(String email){
         return customerService.getCustomer(email);
    }

    public static void createACustomer(String firstName,String lastName, String email){
        customerService.addCustomer(firstName,lastName,email);
    }

    public static IRoom getRoom(String roomNumber){
        return reservationService.getARoom(roomNumber);
    }

    public static Reservation bookARoom(String customerEmail,IRoom room, Date checkInDate,Date checkOutDate){
        return reservationService.reserveARoom(customerService.getCustomer(customerEmail),room,checkInDate,checkOutDate);
    }

    public static Collection<Reservation> getCustomersReservations(String customerEmail){
       return reservationService.getCustomersReservations(
               customerService.getCustomer(customerEmail));
    }

    public  static Collection<IRoom> getAvailableRoom(Date checkIn,Date checkOut){
        return reservationService.findARoom(checkIn,checkOut);
    }




}
