import api.AdminResource;
import api.HotelResource;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class MainMenu {

    private static boolean menuOn = true;
    private static final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    public static void MainMenu() {

        while (menuOn) {
            System.out.println("\n");
            System.out.println("===============Main Menu================");
            System.out.println("1. Find and reserve a room              ");
            System.out.println("2. See my reservations                  ");
            System.out.println("3. Create an account                    ");
            System.out.println("4. Admin                                ");
            System.out.println("5. Exit                                 ");
            System.out.println("========================================\n");

            Scanner userInput = new Scanner(System.in);

            String nextStep = userInput.next();
            switch (nextStep) {
                case "1":
                    String guestEmail = enterEmail();
                    try {if (!(guestEmail == null)) {
                            ArrayList<Date> reservedDates = new ArrayList<>(enterDates());
                            if (!reservedDates.isEmpty()) {
                                String roomNum = enterRoomNumber(reservedDates);
                                if (roomNum != null && !(roomNum.equals("N"))) {
                                    System.out.println("Please enter \"Y\" to confirm making a reservation.");
                                    System.out.println("If you need to change the date,");
                                    System.out.println("Please enter \"N\" to return to main menu to re-select the dates");
                                    System.out.println("User: " + guestEmail);
                                    System.out.println("Reservation Dates: " + formatter.format(reservedDates.get(0)) +
                                            " to " + formatter.format(reservedDates.get(1)));
                                    System.out.println("Reservation room: " + roomNum);
                                    nextStep = answerYNValidator(userInput);
                                    if (nextStep.equals("Y")) {
                                        System.out.println(HotelResource.bookARoom(guestEmail, HotelResource.getRoom(roomNum)
                                                , reservedDates.get(0), reservedDates.get(1)));
                                        System.out.println("Reservation is created successfully!");
                                    }
                                } else {System.out.println("Reservation cancelled.");}
                            }
                        }
                    }catch (NullPointerException ex){ex.getLocalizedMessage();}
                    break;

                case "2":
                    System.out.println(retrieveReservation());
                    break;

                case "3":
                    createAnAccount();

                    break;

                case "4":
                    AdminMenu.AdminMenu();

                    break;

                case "5":
                    menuOn = false;
                    break;


            }
        }
    }


    public static String enterEmail() {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter your Email in the format of x..x@x..x.com,");
        boolean enterMode = true;
        while (enterMode) {
            try {
                String inputEmail = input.next();
                //check if the email exist in the system or not
                //outcomes, contain&ON, contain&off,!contain&off,!contain&on
                    while (!AdminResource.getAllCustomers().contains(HotelResource.getCustomer(inputEmail))
                            && enterMode) {
                        System.out.println("Invalid Email address" +
                                "if you have not created an account please select \"3\" in the main menu to create an account");
                        System.out.println("Do you want to proceed to make a reservation?");
                        System.out.println("Enter Y to re-enter your Email address");
                        System.out.println("Enter N to return to Main Menu");
                        String choice = answerYNValidator(input);
                        if (choice.equals("Y")) {
                            System.out.print("Please enter your Email address and your reservation ");
                            System.out.println("will be associated with it.");
                            inputEmail = input.next();
                            if (AdminResource.getAllCustomers().contains(HotelResource.getCustomer(inputEmail))) {
                                enterMode = false;
                            }
                        } else {
                            System.out.println("Invalid Email address!");
                            enterMode = false;
                        }
                    }
                return HotelResource.getCustomer(inputEmail).getEmail();
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
            enterMode = false;
        }
        return null;
    }

    public static ArrayList<Date> enterDates() {
        ArrayList<Date> reservationDates = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        try {
            System.out.println("Enter your check-in,check-out date(separated by space)" +
                    " in the format of MM/DD/YYYY");

            Date checkInDate = formatter.parse(input.next());
            Date checkOutDate = formatter.parse(input.next());
            while (!checkInDate.before(checkOutDate)) {
                System.out.println("Please enter an valid date range,the input check-out date is before" +
                        "the check-in date");
                checkInDate = formatter.parse(input.next());
                checkOutDate = formatter.parse(input.next());
            }
            System.out.println("Check-In: " + formatter.format(checkInDate) +
                    " Check-Out: " + formatter.format(checkOutDate));
            reservationDates.add(0, checkInDate);
            reservationDates.add(1, checkOutDate);
            return reservationDates;
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    public static String enterRoomNumber(ArrayList<Date> reservationDates2) {
        //Workflow: 1.search for room with original reserved dates.
        Date checkInDate = reservationDates2.get(0);
        Date checkOutDate = reservationDates2.get(1);
        List<IRoom> availableRooms = new ArrayList<>
                (HotelResource.getAvailableRoom(checkInDate, checkOutDate));

        //Outcome 1: have available rooms.
        if (!availableRooms.isEmpty()) {
            return selectedRoomNum(availableRooms);
        } else {
            //Outcome 2: No available room, checkin/out dates plus 7days,
            //search again. return results then stop.
            System.out.println("For the selected dates: from " +
                    formatter.format(checkInDate) + " to " + formatter.format(checkOutDate));
            System.out.println("There is no available room found.");
            System.out.println("Here are the recommended available rooms for the next 7 days: ");
            Calendar calendarIn = Calendar.getInstance();
            calendarIn.setTime(checkInDate);calendarIn.add(Calendar.DAY_OF_MONTH, 7);
            checkInDate = calendarIn.getTime();
//            Calendar calendarOut = Calendar.getInstance();
//            calendarOut.setTime(checkOutDate);calendarOut.add(Calendar.DAY_OF_MONTH, 7);
//            checkOutDate = calendarOut.getTime();
            checkOutDate = new Date(checkOutDate.getTime()+ Duration.ofDays(7).toMillis());
            availableRooms= new ArrayList<> (HotelResource.getAvailableRoom(checkInDate, checkOutDate));
            if (!availableRooms.isEmpty()) {
                System.out.println("From " + formatter.format(checkInDate) + " to " + formatter.format(checkOutDate));
                reservationDates2.set(0,checkInDate);
                 reservationDates2.set(1,checkOutDate);
                return selectedRoomNum(availableRooms);
            } else {
                System.out.println("There is no available room for the next 7 days: " +
                        "From " + formatter.format(checkInDate) + " to " + formatter.format(checkOutDate));
            }
        }
        return "N";
    }

    public static String selectedRoomNum(List<IRoom> availableRoomList) {
        Scanner input = new Scanner(System.in);
        System.out.println("Available rooms for the selected dates: ");
        List<String> roomNums = new ArrayList<>();
        roomNums.add("N");
        for (IRoom room : availableRoomList) {
            roomNums.add(room.getRoomNumber());
            System.out.println(room);
        }
        System.out.println("Please select an available room by entering the room number.");
        System.out.println("To cancel, simply enter \"N\".");
        String selection = input.next().toUpperCase();
        if(!selection.equals("N")) {
            while (!isFourDigits(selection) || !roomNums.contains(selection)) {
                System.out.println("Please select an available room from the list above.");
                selection = input.next();
            }
        }
        System.out.println("Your selection is " + selection);
        return selection;
    }

    public static boolean isFourDigits(String input) {return input.length() == 4;}

    public static Collection<Reservation> retrieveReservation() {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter your Email in the format of x..x@x..x.com " +
                "to retrieve your reservations.");
        return HotelResource.getCustomersReservations(input.next());
    }

    public static void createAnAccount() {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter your information in the order of first name,last name," +
                "Email(in the format of x..x@x..x.com), each separated with a space");
        String firstName = input.next();
        String lastName = input.next();
        String email = input.next();
        HotelResource.createACustomer(firstName, lastName, email);
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