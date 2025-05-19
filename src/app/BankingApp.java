package app;

import repository.Config;
import ui.Start;

public class BankingApp {
    public static void main(String[] args) throws InterruptedException {
       //Sets Test_Mode false on run time
        Config.TEST_MODE = false;
        //starts up the app
        Start.start();
    }
}
