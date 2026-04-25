/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lancexyzherleebimbobankingsystem;

/**
 *
 * @author Lance
 */

import java.sql.Timestamp;
 
public class Transaction {
 
    private int       transactionId;
    private int       accountId;
    private String    transactionType;
    private double    amount;
    private Timestamp transactionDate;
 
    public Transaction(int accountId, String transactionType, double amount) {
        this.accountId       = accountId;
        this.transactionType = transactionType;
        this.amount          = amount;
    }
 
    public Transaction(int transactionId, int accountId, String transactionType,
                       double amount, Timestamp transactionDate) {
        this.transactionId   = transactionId;
        this.accountId       = accountId;
        this.transactionType = transactionType;
        this.amount          = amount;
        this.transactionDate = transactionDate;
    }
 
    public int       getTransactionId()   { return transactionId; }
    public int       getAccountId()       { return accountId; }
    public String    getTransactionType() { return transactionType; }
    public double    getAmount()          { return amount; }
    public Timestamp getTransactionDate() { return transactionDate; }
}