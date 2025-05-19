package service;

import exception.AccountNotFoundException;
import exception.InvalidTransactionAmount;
import exception.UnauthorizedAccessException;
import exception.UserNotLoggedInException;
import model.Account;
import model.Transaction;
import model.User;
import util.CurrencyFormatter;
import util.FindAccount;
import util.OwnershipValidation;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;


public class BankingService {

    // Opens a new account with the specified amount and type for the logged-in user
    public static void openAccount(BigDecimal amount, String type){
        if(!SessionService.isLoggedIn()){
            throw new UserNotLoggedInException();
        }
        type = type.trim();
       if(amount.compareTo(BigDecimal.ZERO)>=0 && !type.isEmpty()) {
          Account acc = AccountService.createAccount(amount, type,SessionService.getCurrentUser());
           System.out.println("Account created successfully!");

           System.out.println(acc);
       }
       else if(amount.compareTo(BigDecimal.ZERO) < 0){
            throw new InvalidTransactionAmount(amount);
       }

       else{
           throw new IllegalArgumentException("Account type was not specified");
       }
    }


    // Displays the current balance of a given account
    public static void viewBalance(String accountNumber){

        if(!SessionService.isLoggedIn()){
            throw new UserNotLoggedInException();
        }

        Account account = FindAccount.findUserAccount(accountNumber);

        if(!OwnershipValidation.isOwner(accountNumber)){
            throw new UnauthorizedAccessException(accountNumber);
        }



       System.out.println("Current balance for account " + account.getAccountNumber() + ": " + CurrencyFormatter.getCurrency(account.getBalance()));
    }

}
