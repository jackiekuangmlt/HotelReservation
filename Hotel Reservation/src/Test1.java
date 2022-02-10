import api.HotelResource;
import sun.java2d.pipe.SpanShapeRenderer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Test1 {
    public static boolean on = true;

    public static void main(String[] args) {

//        ArrayList<Date> dates = enterDates();
//        System.out.println("This is an ArrayList" + dates);
//        secondScanner();;
//        String s = "00s56";
//        int x = 00036;
//        System.out.println(doubleReturnTest("1"));

//        while(on){
//            switchCase();
//        }
//        twoHashSetRelationTest();
//        tryAddingArraryList();
        dateControlAndPrint();
        String s = "123";
        s.toUpperCase(Locale.ROOT);
        System.out.println(s);
    }


    //this is to test .next() method
    public static ArrayList<Date> enterDates() {
        HotelResource hotelResource = new HotelResource();
        ArrayList<Date> reservationDates = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        try {
            //SimpleDateFormat is case-sensitive!!  D for day in year, d for day in month,
            //y for year
            System.out.println("Enter your check-in,check-out date(separated by space) in the format of MM/DD/YYYY");
            Date checkInDate = new SimpleDateFormat("MM/dd/yyyy").parse(input.next());
            Date checkOutDate = new SimpleDateFormat("MM/dd/yyyy").parse(input.next());
            System.out.println("Check-In: " + checkInDate + " Check-Out: " + checkOutDate);
            reservationDates.add(0, checkInDate);
            reservationDates.add(1, checkOutDate);
            return reservationDates;
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println(e.getLocalizedMessage());
        }

        return reservationDates;
    }

    public static void secondScanner() {
        Scanner input = new Scanner(System.in);
        System.out.println("This is a second scanner method, please enter something");

        String secondScanner = input.next();

    }

    public static void switchCase() {
        Scanner input = new Scanner(System.in);
        System.out.println("1. Case 1");
        System.out.println("2. Case 2");
        System.out.println("3. Quit");
        switch (input.next()) {
            case "1":
                System.out.println("This is case 1");
                break;
            case "2":
                System.out.println("This is case 2");
                break;
            case "3":
                on = false;

        }

    }

    //If the statements inside the if loop are executed, the return null statment is not executed.
    public static String doubleReturnTest(String x) {
        if (x == "1") {
            return "Print 1";
        }
        return null;
    }

    //When create a second set to hold the old-set values, and the old-set values are changed,
    //the second values will stay the same unless changes are made to the second set.
    public static void twoHashSetRelationTest() {
        Set<String> set1 = new HashSet<>();
        set1.add("1");
        set1.add("2");
        set1.add("3");
        set1.add("4");
        Set<String> set2 = new HashSet<>(set1);
        set1.add("11");
        set1.add("22");
        System.out.println("Set1: " + set1);
        System.out.println("Set2: " + set2);

    }

    public static void tryAddingArraryList() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("a");
        strings.add("s");
        ArrayList<String> stringList = new ArrayList<>(strings);
        System.out.println(strings);
        System.out.println(stringList);
    }

    public static void dateControlAndPrint() {
        String in = "03/02/2022";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            Date checkInDate =formatter.parse(in);
            Date checkOutDate = formatter.parse("03/06/2022");
            System.out.println("Original Dates: " + formatter.format(checkInDate) + " to " + checkOutDate);
            System.out.println("Double checks: " + checkInDate+ " to " + checkOutDate);
            Calendar calendarIn = Calendar.getInstance();
            Calendar calendarOut = Calendar.getInstance();
            calendarIn.setTime(checkInDate);
            calendarIn.add(Calendar.DAY_OF_MONTH, 7);
            calendarOut.setTime(checkOutDate);
            calendarOut.add(Calendar.DAY_OF_MONTH, 7);
            checkInDate = calendarIn.getTime();
            checkOutDate = calendarOut.getTime();
            System.out.println("New Dates: " + formatter.format(checkInDate)  + " to " + checkOutDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

}
