/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lancexyzherleebimbobankingsystem;

/**
 *
 * @author Lance
 */
import java.time.LocalDateTime;
 
public class Account {
 
    private int           accountId;
    private int           customerId;
    private String        accountType;
    private double        balance;
    private LocalDateTime createdAt;
 
    public Account(int customerId, String accountType, double balance) {
        this.customerId  = customerId;
        this.accountType = accountType;
        this.balance     = balance;
    }
 
    public Account(int accountId, int customerId, String accountType, double balance) {
        this.accountId   = accountId;
        this.customerId  = customerId;
        this.accountType = accountType;
        this.balance     = balance;
    }
 
    public int           getAccountId()   { return accountId; }
    public int           getCustomerId()  { return customerId; }
    public String        getAccountType() { return accountType; }
    public double        getBalance()     { return balance; }
    public LocalDateTime getCreatedAt()   { return createdAt; }
 
    public void setAccountId(int accountId)      { this.accountId   = accountId; }
    public void setCustomerId(int customerId)    { this.customerId  = customerId; }
    public void setAccountType(String type)      { this.accountType = type; }
    public void setBalance(double balance)       { this.balance     = balance; }
    public void setCreatedAt(LocalDateTime dt)   { this.createdAt   = dt; }
 
    @Override
    public String toString() {
        return "Account #" + accountId + " (" + accountType + ")";
    }
}