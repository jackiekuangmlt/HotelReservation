import api.AdminResource;
import api.HotelResource;
import model.*;

import java.util.*;

public class AdminMenu {

    private static List<IRoom> roomList;
    private static boolean menuOn = true;

    public static void AdminMenu() {

//        while (menuOn) {
            System.out.println("\n");
            System.out.println("=========Administration Menu============");
            System.out.println("1. See all Customers");
            System.out.println("2. See all Rooms");
            System.out.println("3. See all Reservations");
            System.out.println("4. Add a room");
            System.out.println("5. Back to Main Menu");
            System.out.println("========================================\n");
            Scanner userInput = new Scanner(System.in);
            String nextStep = userInput.next();

            switch (nextStep) {
                case "1":
                    try {
                        int i = 1;
                        for (Customer guest : AdminResource.getAllCustomers()) {
                            System.out.println(i+": "+guest);
                            i++;
                        }
                    } catch (NullPointerException ex) {
                        System.out.println("Customer system is empty!");
                    }
                    break;

                case "2":
                    for (IRoom ro : AdminResource.getAllRooms()) {
                        System.out.println(ro);
                    }
                    break;

                case "3":
                    AdminResource.displayAllReservations();
                    break;

                case "4":
                    roomList = new LinkedList<>();
                    Scanner enterInput = new Scanner(System.in);
                    String option;
                    boolean isdone = true;
                    while (isdone) {
                        String roomId = enterRoomNumber();
                        RoomType roomType = enterRoomType();
                        double roomPrice = enterRoomPrice();
                        System.out.println("Please confirm room info: " + roomId + " " + roomType + " $" + roomPrice);
                        System.out.println("Enter Y to confirm, N to cancel and return to Main menu.");
                        option = answerYNValidator(enterInput);
                        if (option.equals("Y")) {
                            addRoom(roomId, roomPrice, roomType);
                            System.out.println("Room info are confirmed.");
                            System.out.print("Do you want to create another room before saving room(s)");
                            System.out.println("info in the system? Y/N");
                            System.out.println("Enter Y to create another room.");
                            System.out.print("Enter N to save all room info into the system ");
                            System.out.println("and return to the Admin menu.");
                            System.out.println(roomList);
                            option = answerYNValidator(enterInput);
                            if (option.equals("N")) {
                                addRoomsToSystem();
                                isdone = false;
                            }
                        } else {
                            isdone = false;
                        }
                    }
                    break;

                case "5":
                    MainMenu.MainMenu();
            }
//        }


    }


    public static String enterRoomNumber() {
        String roomNumber = null;
        Scanner input = new Scanner(System.in);
        System.out.println("Please create a new room number with 4 digits.");
        roomNumber = getValidDigitalInput(input);
        System.out.println("Room Number: " + roomNumber);
        return roomNumber;
    }

    public static String getValidDigitalInput(Scanner input) {
        boolean enterMode = true;
        String result = "";
        while (enterMode) {
            try {
                result = input.next();
                Integer.parseInt(result);
                if (isFourDigits(result)) {
                    enterMode = false;
                } else {
                    System.out.println("Please enter an valid 4-digit number(0000~9999)");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid digital number");
            }
        }
        return result;
    }

    public static boolean isFourDigits(String input) {return input.length() == 4;}

    public static RoomType enterRoomType() {
        boolean enterMode = true;
        RoomType roomTypes = null;
        String result = null;
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the room type(single/double)");
        while (enterMode) {
            result = input.next().toUpperCase();
            System.out.println(result);
            if (result.equals("SINGLE") || result.equals("DOUBLE")) {
                roomTypes = RoomType.valueOf(result);
                System.out.println("Room Type: " + roomTypes);
                enterMode = false;
            } else {
                System.out.println("Invalid input, please enter \"single/double\".");
            }
        }
        return roomTypes;


    }

    public static double enterRoomPrice() {
        System.out.println("Is this a free room?(Y/N)");
        Scanner input = new Scanner(System.in);
        double roomCost = 0;
        boolean enterMode = true;
        String choice;
        while (enterMode) {
            try {
                choice = answerYNValidator(input);
                if (choice.equals("N")) {
                    System.out.println("Please enter the room price in USD(in digits only).");
                    roomCost = Double.valueOf(input.next());
                }
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input, Please enter the room price in USD(in digits only).");
            }
            enterMode = false;
        }

        return roomCost;
    }

    public static void addRoom(String roomId, double roomPrice, RoomType roomType) {
        roomList.add(new Room(roomId, roomPrice, roomType));
    }

    public static void addRoomsToSystem() {
        System.out.println("Confirm to add all room(s) to the System (Y/N)?");
        Scanner input = new Scanner(System.in);
        String choice = answerYNValidator(input);
        if (choice.equals("Y")) {
            AdminResource.addRoom(roomList);
        }
    }

    public static String answerYNValidator(Scanner input) {
        String choice = input.next().toUpperCase();
        System.out.println(choice);
        while (!(choice.equals("Y") || choice.equals("N"))) {
            System.out.println("Input is not valid. Please enter Y/N");
            choice = input.next().toUpperCase();
        }
        return choice;
    }


}





