package util;

import service.SessionService;

public class NoAccountPresent {

    //when a user wants to make an action in the bank menu without having an account
    public static boolean noAccount(){
        if (SessionService.getCurrentUser().getAccounts().isEmpty()){
            System.out.println("No Accounts Present: First create an account");
            return false;
        }

        return true;
    }
}
