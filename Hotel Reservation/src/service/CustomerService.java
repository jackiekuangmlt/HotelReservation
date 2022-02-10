package service;

import model.Customer;

import java.util.*;

public class CustomerService {

    private static CustomerService customerService;//a static reference
    private final Map<String, Customer> mapOfCustomers;

    // constructor just creates a new HashMap()
    private CustomerService() {
        mapOfCustomers = new HashMap<>();
    }

    //Singleton pattern
    public static CustomerService getInstance() {
        if (customerService == null) {
            customerService = new CustomerService();
        }
        return customerService;
    }

    //Each customer should have an unique email
    public void addCustomer(String firstName, String lastName, String email) {
        // first check if the input email is used
        try {
            Customer guest = new Customer(firstName, lastName, email);
            if (!mapOfCustomers.containsKey(email)) {
                mapOfCustomers.put(email, new Customer(firstName, lastName, email));
                System.out.print("Success! ");
                System.out.println("The following customer has been added to the system: \n"+guest);
            } else {
                System.out.println("The following email address has been used: " + email);
            }
        }catch(ClassCastException |NullPointerException ex){
            System.out.println("Error");
            ex.printStackTrace();
        }catch (IllegalArgumentException ex){
            ex.getLocalizedMessage();
            ex.printStackTrace();
        }
    }

    public Customer getCustomer(String customerEmail) {
        try {
            if (!mapOfCustomers.values().isEmpty() && mapOfCustomers.containsKey(customerEmail)) {
                return mapOfCustomers.get(customerEmail);
            } else {
                System.out.println("Please re-enter an valid email address or create a new account," +
                        " the following email does not exists in our system: " + customerEmail);
            }
        } catch (NullPointerException | ClassCastException ex) {
            System.out.println("Error");
        }
        return null;
    }

    public Collection<Customer> getAllCustomers() {
        if (!mapOfCustomers.isEmpty()) {
            return new ArrayList<>(mapOfCustomers.values());
        }
        System.out.println("Customer system is empty!");
        return null;
    }
}
