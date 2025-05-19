package util;

import exception.AccountNotFoundException;
import model.Account;
import model.User;
import repository.AccountRepository;
import service.SessionService;

import java.util.List;
import java.util.NoSuchElementException;

public class OwnershipValidation {

    //checking if the current logged-in user is the owner of the account with specified account number
    public static boolean isOwner(String accountNumber){

        User user = SessionService.getCurrentUser();
        return user.getAccounts().stream()
                .anyMatch(acc-> acc.getAccountNumber().equals(accountNumber));

    }
}
