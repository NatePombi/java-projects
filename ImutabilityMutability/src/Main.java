
import Service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    //Scanner for user input
    public static Scanner scanner = new Scanner(System.in);
    //service layer to handle user operations
    static UserService service = new UserService();
    public static void main(String[] args) {
        boolean exit = true;
        System.out.println("--".repeat(20));
        System.out.println("Welcome to AvTech");

        while(exit){
            //displays user menu
            System.out.println("--".repeat(20));
            System.out.println("\n1) Register");
            System.out.println("2) List Users");
            System.out.println("3) Find user by name");
            System.out.println("4) Exit");
            System.out.print("Enter the number of the action above: ");
            int input = scanner.nextInt();
            scanner.nextLine(); //clears buffer
            switch (input){
                case 1-> register(); //handles user registration
                case 2 ->  service.listAllUsers(); //displays all Users
                case 3 -> findUser();
                case 4 -> {

                    exit = false;
                    System.out.println("Exiting...Goodbye");
                }
                default -> System.out.println("Please enter the number of the actions above");
            }
        }
    }

    //method to register user
    public static void register(){
        System.out.println("Enter Name:");
        String name = scanner.nextLine();

        System.out.println("Enter roles seperated by a (comma)");
        String input = scanner.nextLine();

        //converts comma seperated roles to list
        List<String> roles = Arrays.stream(input.split(","))
                .map(String::trim)
                .toList();

        service.registerUser(name,roles);
    }

    //method to find a user by name
    public static void findUser(){
        System.out.print("Enter the Users name: ");
        String name = scanner.nextLine();

        service.findUser(name);
    }
}