package model;


import util.CurrencyFormatter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Objects;

/**
 * A transaction record for an account.
 * Includes the type of transaction, amount, description, and timestamp.
 */
public class Transaction {
    private final TransactionType type;
    private final BigDecimal amount;
    private final String description;
    private final LocalDateTime timeStamp;

    /**
     * Constructor to create a new Transaction.
     * @param type The type of the transaction as a string (converted to enum).
     * @param amount The amount of the transaction (must not be null).
     * @param description A short message about the transaction (defaults if null).
     */
    public Transaction(String type, BigDecimal amount, String description) {
        this.type = Objects.requireNonNull(TransactionType.getEnum(type));
        this.amount = Objects.requireNonNull(amount);
        this.description = description!=null? description : "No details provided";
        timeStamp = LocalDateTime.now(); // Set the timestamp at creation time
    }

    //Getters for every field

    public TransactionType getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    /**
     * Returns a formatted string representing the transaction.
     * The output includes type, amount (in currency), and timestamp.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm a");
        String formattedDate = timeStamp.format(formatter);
        return String.format("""
                %-15s |%14s        |  %s
               """,type, CurrencyFormatter.getCurrency(amount),formattedDate);
    }
}
