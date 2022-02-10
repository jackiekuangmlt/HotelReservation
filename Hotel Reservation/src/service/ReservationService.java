package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {

    private static ReservationService reservationService;
    //Storing Email as key, ArrayList of reservations as values
    private final Map<String, ArrayList<Reservation>> mapOfReservations;
    private final Set<IRoom> listOfRooms;

    private ReservationService() {
        mapOfReservations = new HashMap<>();
        listOfRooms = new HashSet<>();
    }

    public static ReservationService getInstance() {
        if (reservationService == null) {
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    //Each room should have unique room number
    public void addRoom(IRoom room) {
        boolean isUsed = false;
        //first check the rooms' numbers
        Set<IRoom> dummyList = new HashSet<>(listOfRooms);
        if (!dummyList.isEmpty()) {
            for (IRoom roomy : dummyList) {
                if (roomy.getRoomNumber().equals(room.getRoomNumber())) {
                    isUsed = true;
                    System.out.println("Room number: " + room.getRoomNumber() + "is used, " +
                            "please Enter an un-used room number.");
                }
            }
        }
        if (!isUsed) {
            System.out.println("Room: " + room + "\n Added successfully!");
            listOfRooms.add(room);
        }
    }

    public IRoom getARoom(String roomId) {
        if (!listOfRooms.isEmpty()) {
            for (IRoom room : listOfRooms) {
                if (roomId.equals(room.getRoomNumber())) {
                    return room;
                }
            }
        } else {
            System.out.println("Room system is empty");
        }
        return null;
    }

    public Collection<IRoom> getRooms() {
        if (listOfRooms.isEmpty()) {
            System.out.println("Room system is empty!");
        }
        return listOfRooms;
    }

    Collection<IRoom> getRoomList() {
        if (listOfRooms.isEmpty()) {
            System.out.println("Room system is empty!");
        }
        return listOfRooms;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        mapOfReservations.putIfAbsent(customer.getEmail(), new ArrayList<>());
        mapOfReservations.get(customer.getEmail()).add(reservation);
        return reservation;
    }

    public Collection<Reservation> getCustomersReservations(Customer customer) {
        try {
            if (mapOfReservations.containsKey(customer.getEmail())) {
                return mapOfReservations.get(customer.getEmail());
            }
        } catch (NullPointerException ex) {
            System.out.println("Invalid Email address, please re-enter an valid Email address!");
        }

        return null;
    }

    //It put all rooms into a list for searchRoom() to filter out booked room.
    //Finally, returns a list contains available rooms.
    public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        HashSet<IRoom> availableRooms = new HashSet<>(listOfRooms);
        searchRoom(availableRooms,checkIn,checkOut);
        return availableRooms;
    }

    //It should check the checkin/out dates and return the corresponding available rooms.
    public void searchRoom(HashSet<IRoom> availableRoomList, Date checkInDate, Date checkOutDate) {
        Iterator<IRoom> eachRoom = availableRoomList.iterator();
        if (!mapOfReservations.isEmpty()) {
            while (eachRoom.hasNext()) {
                //check each room's availability
                //Extract each reservation quest and check the reserved dates
                IRoom roomy = eachRoom.next();
                Iterator<ArrayList<Reservation>> iOfExistedReserv = mapOfReservations.values().iterator();
                while (iOfExistedReserv.hasNext()) {
                    ArrayList<Reservation> eachReservationList = iOfExistedReserv.next();
                    for (Reservation eachReservation : eachReservationList) {
                        if (roomy.getRoomNumber().equals(eachReservation.getRoom().getRoomNumber())) {

                            //true if targeted check-in/our date are both before the reserved dates
                            boolean before = checkInDate.before(eachReservation.getCheckInDate())
                                    && (checkOutDate.before(eachReservation.getCheckInDate())
                                    || checkOutDate.equals(eachReservation.getCheckInDate()));

                            //true if targeted check-in/our date are both after the reserved dates
                            boolean after = (checkInDate.after(eachReservation.getCheckOutDate())
                                    || checkInDate.equals(eachReservation.getCheckOutDate()))
                                    && checkOutDate.after(eachReservation.getCheckOutDate());

                            // if either before/after is not true;
                            if (!(before || after)){
                                eachRoom.remove();
                            }
                        }
                    }
                }
            }
        }
    }


    public void printAllReservations() {
        if(!mapOfReservations.isEmpty()) {
            for (ArrayList<Reservation> reservations : mapOfReservations.values()) {
                int i = 1;
                for (Reservation reservation: reservations) {
                    System.out.println(i + " "+ reservation);
                    i++;
                }
            }
        }
        else{
            System.out.println("There is no reservation in the system.");
        }
    }




}
