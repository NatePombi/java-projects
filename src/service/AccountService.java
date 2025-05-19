package service;

import model.Account;
import model.Transaction;
import model.User;
import repository.AccountRepository;
import util.FindAccount;
import util.OwnershipValidation;
import exception.*;


import java.math.BigDecimal;
import java.util.List;

public class AccountService {

    // Creates a new account and associates it with the logged-in user
    public static Account createAccount(BigDecimal balance, String type, User user){

        if(SessionService.getCurrentUser()==null){
            throw new UserNotLoggedInException();
        }

        Account acc = new Account(balance,type, user.getUsername());
        user.addAccount(acc);
        AccountRepository.addAccount(acc);
        System.out.println("You have successfully created a " + acc.getType() + " with an account number " + acc.getAccountNumber() + ".");
        return acc;
    }

    // Deposits funds into a specified account
    public static void deposit(String accountNumber,BigDecimal amount){

        if(!SessionService.isLoggedIn()){
            throw new UserNotLoggedInException();
        }

         if(amount.compareTo(BigDecimal.ZERO) <=0){
            throw new InvalidTransactionAmount(amount);
        }

        Account account = FindAccount.findUserAccount(accountNumber);

        if(!OwnershipValidation.isOwner(accountNumber)){
            throw new UnauthorizedAccessException(accountNumber);
        }


        account.deposit(amount);
        PersistenceService.saveAll();
        System.out.println("Amount successfully deposited");
    }

    // Withdraws funds from a specified account
    public static void withdraw(String accountNumber, BigDecimal amount)  {

        if(!SessionService.isLoggedIn()){
            throw new UserNotLoggedInException();
        }

        if(amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidTransactionAmount(amount);
        }

        Account account = FindAccount.findUserAccount(accountNumber);

        if(!OwnershipValidation.isOwner(accountNumber)){
            throw new UnauthorizedAccessException(accountNumber);
        }


        if(account.getBalance().compareTo(amount)<0){
            throw new InvalidTransactionAmount(amount);
        }

        account.withdraw(amount);
        PersistenceService.saveAll();
        System.out.println("Withdrawal was successful");
    }

    // Retrieves details of a specific account
    public static Account getAccountDetails(String accountNumber){
        if(!SessionService.isLoggedIn()){
            throw new UserNotLoggedInException();
        }

        Account acc = FindAccount.findUserAccount(accountNumber);

        if(!OwnershipValidation.isOwner(accountNumber)){
            throw new UnauthorizedAccessException(accountNumber);
        }


        return acc;
    }

    // Returns the list of transactions for a given account
    public static List<Transaction> getTransactions(String accountNumber) {
        if(!SessionService.isLoggedIn()){
            throw new UserNotLoggedInException();
        }

        Account account = FindAccount.findUserAccount(accountNumber);

        if(!OwnershipValidation.isOwner(accountNumber)){
            throw new UnauthorizedAccessException(accountNumber);
        }

        return account.getTransactions();
    }

    // Deletes an account if it belongs to the current user and has zero balance
    public static void deleteAccount(String accountNumber){
        if(!SessionService.isLoggedIn()){
            throw new UserNotLoggedInException();
        }

        User currentUser = SessionService.getCurrentUser();

       Account accountToDelete = FindAccount.findUserAccount(accountNumber);

       if(!accountToDelete.getOwnerUsername().equals(currentUser.getUsername())){
           throw new UnauthorizedAccessException(accountNumber);
       }

       if(accountToDelete.getBalance().compareTo(BigDecimal.ZERO)>0){
           throw new DeletingWithFundsException(accountToDelete.getBalance(),accountNumber);
       }

        currentUser.removeAccount(accountNumber);

        List<Account> accounts = AccountRepository.getAllAccounts();


        accounts.removeIf(acc-> acc.getAccountNumber().equals(accountNumber));

        PersistenceService.saveAll();
        System.out.println("Account with number " + accountNumber + " has been successfully deleted");
    }

    // Transfers funds between two accounts
    public static void transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount){
        if(!SessionService.isLoggedIn()){
            throw new UserNotLoggedInException();
        }

        Account accountFrom = FindAccount.findUserAccount(fromAccountNumber);

        if(!OwnershipValidation.isOwner(fromAccountNumber)){
            throw new UnauthorizedAccessException(fromAccountNumber);
        }

        if(!(amount.compareTo(BigDecimal.ZERO) >0)){
            throw new InvalidTransactionAmount(amount);
        }



        if(!(accountFrom.getBalance().compareTo(amount) >=0)){
            throw new InvalidTransferAmountFrom(amount);
        }

        Account accountTo = FindAccount.findUserAccount(toAccountNumber);


        if(accountTo.getAccountNumber().equals(fromAccountNumber)){
            System.out.println("Failed: You're Transferring to the same account");

        }else {

            accountTo.deposit(amount);
            accountFrom.withdraw(amount);

            PersistenceService.saveAll();
            System.out.println("Transfer successfully");
        }

    }


}
