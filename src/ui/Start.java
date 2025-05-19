package ui;

import exception.*;
import service.*;
import util.NoAccountPresent;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import static ui.InputHandler.*;
import static ui.InputHandler.promptAccountNumber;
import static ui.InputHandler.promptAccountNumberReceivingFunds;
import static ui.InputHandler.promptAccountNumberSendingFunds;
import static ui.InputHandler.promptAmount;
import static ui.InputHandler.promptPassword;
import static ui.InputHandler.promptUserName;
import static ui.Processing.processing;
import static ui.SystemMessage.*;
import static ui.SystemMessage.showHeader;

/**
 * Entry point for the banking application UI.
 * Handles both pre-login and post-login menus and logic.
 */
public class Start {
    private static boolean running = true;

    // Starts the main application loop
    public static void start() throws InterruptedException {
        int input;
        while(running) {

            // Show startup menu if no user is logged in
            if (!SessionService.isLoggedIn()) {
                MenuRenderer.showStartUpMenu();
                input = promptMenuSelection();  // Get user input for start menu
                handleStartUpMenu(input);
            } else {

                // Show banking menu if a user is logged in
                MenuRenderer.showBankingMenu();
                input = promptMenuSelection();  // Get user input for banking menu
                handleBankingMenu(input);
            }
        }

    }


    // Handles user choices from the startup menu
    private static void handleStartUpMenu(int input) throws InterruptedException {


        switch (input){
            case 1-> registerU();
            case 2-> logInUser();
            case 3-> {
                showGoodBye();
                running = false;
            }
        }
    }

    // Handles user registration
    private static void registerU(){


        ui.SystemMessage.showHeader("Registering User");

        String name = promptName();
        String username = promptUserName();
        String password =promptPassword();

        try {
            UserService.registerUser(username, password, name);
        }
        catch (NullPointerException | IllegalArgumentException ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }

    // Handles user login
    private static void logInUser(){
        ui.SystemMessage.showHeader("Log In");

        String username = promptUserName();

        String password = promptPassword();

        try {
            UserService.loginUser(username, password);
        }
        catch(NullPointerException | IllegalArgumentException | NoSuchElementException ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }

    // Handles user choices from the banking menu
    private static void handleBankingMenu(int input) throws InterruptedException {

        switch (input){
            case 1 -> openAccount();
            case 2 -> viewAccountBalance();
            case 3 -> depositFunds();
            case 4 -> withdrawFunds();
            case 5 -> transferFundsToDifferentAccount();
            case 6 -> transactionHistory();
            case 7 -> accountDetails();
            case 8 -> deleteAccount();
            case 9 -> {
                showLogOut();
                PersistenceService.saveAll();
                SessionService.logOut();
            }
            default -> System.out.println("Invalid selection. Please choose a number from the menu.");

        }
    }

    // Creates a new account for the logged-in user
    private static void openAccount(){
        ui.SystemMessage.showHeader("Creating Account");
        String type = emptyInputOrSpellingError();

        try {
            AccountService.createAccount(BigDecimal.ZERO, type, SessionService.getCurrentUser());
        }
        catch (IllegalArgumentException | UserNotLoggedInException e){
            System.out.println(e.getMessage());
        }
    }

    // Displays account balance
    private static void viewAccountBalance() {
        if(!NoAccountPresent.noAccount()){
            return;
        }

        String accountNumber = promptAccountNumber();


        try {
            BankingService.viewBalance(accountNumber);
        }
        catch (UnauthorizedAccessException | AccountNotFoundException | UserNotLoggedInException e){
            System.out.println("Error!!: " + e.getMessage());
        }

    }

    // Deposits money into an account
    private static void depositFunds() throws InterruptedException {
        if(!NoAccountPresent.noAccount()){
            return;
        }

        String accountNumber = promptAccountNumber();


        BigDecimal amount = promptAmount("Enter the amount you want to deposit: R");

        processing();
        System.out.println();

        try {
            AccountService.deposit(accountNumber, amount);
        }

        catch ( UnauthorizedAccessException | AccountNotFoundException | InvalidTransactionAmount | UserNotLoggedInException ex){
            System.out.println(ex.getMessage());
        }


    }

    // Withdraws money from an account
    private static void withdrawFunds() throws InterruptedException {
        if(!NoAccountPresent.noAccount()){
            return;
        }

        String accountNumber = promptAccountNumber();

        BigDecimal amount = promptAmount("Enter the amount you want to withdraw: R");

        processing();
        System.out.println();
        try {
            AccountService.withdraw(accountNumber, amount);
        }
        catch (AccountNotFoundException | UnauthorizedAccessException | InvalidTransferAmountFrom | UserNotLoggedInException ex){
            System.out.println("Error: " + ex.getMessage());
        }

    }

    // Displays transaction history
    private static void transactionHistory() throws InterruptedException {
        if(!NoAccountPresent.noAccount()){
            return;
        }

        String accountNumber = promptAccountNumber();

        processing();
        System.out.println();

        System.out.println("""
                           Transactions:
                      Account Number: %s
                      --------------------------------
                    """.formatted(accountNumber));
        try {
            System.out.println(AccountService.getTransactions(accountNumber));
        }
        catch (AccountNotFoundException | UnauthorizedAccessException | UserNotLoggedInException ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }

    // Displays account details
    private static void accountDetails() throws InterruptedException {
        if(!NoAccountPresent.noAccount()){
            return;
        }

        String accountNumber = promptAccountNumber();

        processing();
        System.out.println();
        try {
            System.out.println(AccountService.getAccountDetails(accountNumber));
        }
        catch (UnauthorizedAccessException | AccountNotFoundException | UserNotLoggedInException ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }

    // Deletes an account
    private static void deleteAccount() throws InterruptedException {
        showHeader("\nDeleting Account");

        String accountNumber = promptAccountNumber();

        processing();
        System.out.println();

        try {
            AccountService.deleteAccount(accountNumber);
        }
        catch (UnauthorizedAccessException | AccountNotFoundException | DeletingWithFundsException | UserNotLoggedInException ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }

    // Transfers funds between two accounts
    private static void transferFundsToDifferentAccount() throws InterruptedException {
        showHeader("\nTransferring Funds");

        String accountNumberFrom = promptAccountNumberSendingFunds();

        String accountNumberTo = promptAccountNumberReceivingFunds();

        BigDecimal amount = promptAmount("Enter amount you want to transfer: R");

        processing();
        System.out.println();

        try {
            AccountService.transfer(accountNumberFrom, accountNumberTo, amount);
        }
        catch (UnauthorizedAccessException | AccountNotFoundException | InvalidTransactionAmount |
               InvalidTransferAmountFrom | UserNotLoggedInException e){
            System.out.println("Error: " + e.getMessage());
        }

    }
    }


