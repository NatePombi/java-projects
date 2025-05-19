package model;

import util.PasswordUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a user in the banking system.
 * Each user has a username, name, hashed password with salt, and a list of accounts.
 */
public class User {
    private final String username;           // Unique identifier for the user
    private  String passwordHash;            // Hashed password
    private  String salt;                    // Salt used for hashing the password
    private final String name;               // Full name of the user
    private final List<Account> accounts;    // List of accounts owned by the user


    /**
     * Constructs a new User with the given credentials and name.
     * Password is hashed securely with a generated salt.
     */
    public User(String username, String passwordHash, String name) {
        this.username = username;
        this.salt = PasswordUtil.generateSalt();                  // Generate a new salt
        this.passwordHash = PasswordUtil.hash(passwordHash,salt); // Hash the password with salt
        this.name = name;
        this.accounts = new ArrayList<>();
    }

    /**
     * removes account with the associated account number
     */
    public void removeAccount(String accountNumber){
        accounts.removeIf(acc ->acc.getAccountNumber().equals(accountNumber));
    }
    //Getters for every field

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return passwordHash;
    }

    public String getName() {
        return name;
    }

    public String getSalt() {
        return salt;
    }


    /**
     * Adds an account into the accounts list
     */
    public void addAccount(Account account){
        accounts.add(account);
    }

    /**
     * Returns an unmodifiable list of accounts to prevent external modification.
     */
    public List<Account> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    /**
     * Users are considered equal if their usernames are equal.
     */
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username);
    }

    /**
     * Returns a formatted string of the user including name, username, and accounts.
     */
    @Override
    public String toString() {
        return String.format("""
                User: %s
                Username: %s
                Accounts: %s
                """, name,username,accounts);
    }
}
