package UI;

import java.util.Scanner;

public class UserInterface {

    public UserInterface(){}

    //displays the main menu to users
    public static void printMenu() {
        System.out.println("""
                Enter the number of the action you wish to perform
                ------------------------------------------------------
                1) Add new Product
                2) Display All Products
                3) Update product details(Price & quantity)
                4) Remove a product
                5) Exit
                
                """);
    }

}



