package ui;

import service.SessionService;

public class MenuRenderer {
    //For Start up menu display
    public static void showStartUpMenu(){
        System.out.println("""
                    Welcome to Maze Bank
                  --------------------------
                  1) Register new User
                  2) Login
                  3) Exit
                """);
    }

    //`For banking menu display
    public static void showBankingMenu(){
        System.out.printf("""
                       \s
                        Welcome: %s
                    ---------------------
                    1) Open New Account
                    2) View Account Balance
                    3) Deposit Funds
                    4) Withdraw Funds
                    5) Transfer Funds
                    6) View Transaction History
                    7) View Account Details
                    8) Delete Account
                    9) Log out
                %n""", SessionService.getCurrentUser().getUsername());
    }


}
