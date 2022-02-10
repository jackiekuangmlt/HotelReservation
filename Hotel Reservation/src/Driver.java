import model.Customer;

public class Driver {
    public static void main(String[] args){
        Customer customer = new Customer("first","customer","firstcustomer@email.com");
        System.out.println(customer);

        System.out.println(customer.getLastName()+"\n"+customer.getEmail());
    }

}
