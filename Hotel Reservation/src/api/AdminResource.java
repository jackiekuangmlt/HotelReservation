package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {

    private static final CustomerService customerService = CustomerService.getInstance();
    private static final ReservationService reservationService = ReservationService.getInstance();

    private AdminResource(){

    }

    public static Customer getCustomer(String email){return customerService.getCustomer(email);}

    public static void addRoom(List<IRoom> rooms){
        int i = 0;
        for(IRoom ro : rooms) {
            i++;
            System.out.println(i);
            reservationService.addRoom(ro);
        }
    }

    public static Collection<IRoom> getAllRooms(){
        return reservationService.getRooms();
    }

    public static Collection<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    public static void displayAllReservations(){
        reservationService.printAllReservations();
    }


}
