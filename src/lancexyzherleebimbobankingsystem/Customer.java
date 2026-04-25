/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lancexyzherleebimbobankingsystem;

/**
 *
 * @author Lance
 */
public class Customer {
 
    private int    customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
 
    public Customer(String firstName, String lastName, String email, String phoneNumber) {
        this.firstName   = firstName;
        this.lastName    = lastName;
        this.email       = email;
        this.phoneNumber = phoneNumber;
    }
 
    public Customer(int customerId, String firstName, String lastName, String email, String phoneNumber) {
        this.customerId  = customerId;
        this.firstName   = firstName;
        this.lastName    = lastName;
        this.email       = email;
        this.phoneNumber = phoneNumber;
    }
 
    public int    getCustomerId()  { return customerId; }
    public String getFirstName()   { return firstName; }
    public String getLastName()    { return lastName; }
    public String getEmail()       { return email; }
    public String getPhoneNumber() { return phoneNumber; }
 
    public void setCustomerId(int customerId)    { this.customerId  = customerId; }
    public void setFirstName(String firstName)   { this.firstName   = firstName; }
    public void setLastName(String lastName)     { this.lastName    = lastName; }
    public void setEmail(String email)           { this.email       = email; }
    public void setPhoneNumber(String phone)     { this.phoneNumber = phone; }
 
    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}