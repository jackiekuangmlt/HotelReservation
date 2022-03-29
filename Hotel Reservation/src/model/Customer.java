package model;

import java.util.regex.Pattern;

public class Customer {
    private final String firstName;
    private final String lastName;
    private final String email;
    String emailExp = "^(.+)@(.+).com$";
    Pattern pattern = Pattern.compile(emailExp);

    public Customer(String firstName,String lastName,String email) {
        if(!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid Email address!"){
            } ;
        }else {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
        }

    }

    public String getFirstName(){return firstName;}

    public String getLastName(){return lastName;}

    public String getEmail() {return email;}

    @Override
    public String toString(){
        return "Customer: " + firstName +" " + lastName + " Email Address: " + email;
    }
}
