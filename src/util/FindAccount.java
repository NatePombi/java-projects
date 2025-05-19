package util;

import exception.AccountNotFoundException;
import model.Account;
import repository.AccountRepository;
import service.SessionService;

public class FindAccount {
    //finding the account with the specified account number in repository
    public static Account findUserAccount(String accountNumber){

        return AccountRepository.getAllAccounts().stream()
                .filter(acc-> acc.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElseThrow(()->new AccountNotFoundException(accountNumber));
    }
}
