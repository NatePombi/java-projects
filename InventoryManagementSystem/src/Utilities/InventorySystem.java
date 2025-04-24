package Utilities;

import Model.Product;
import UI.UserInterface;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.TreeMap;

public class InventorySystem {

    // TreeMap automatically keeps products sorted alphabetically by name
    private TreeMap<String, Product> products = new TreeMap<>();

    public InventorySystem(){
        boolean exit = true;
        Scanner scanner = new Scanner(System.in);

        UserInterface.printMenu(); // Print the menu at startup

        // Main loop for menu interaction
        while(exit){
            Scanner inputs = new Scanner(System.in);
            System.out.print("Enter the number for the action: ");
            int input = scanner.nextInt();
            scanner.nextLine(); // consume the leftover newline


            switch (input){
                case 1 -> addProduct(inputs);
                case 2 -> displayAllProducts();
                case 3 -> updateProduct(inputs);
                case 4 -> removeProduct(inputs);
                case 5 -> {
                    System.out.println("Exiting Inventory System. Goodbye!");
                    exit = false;
                }
                default -> System.out.println("Please enter a valid option (1–5)");
            }
        }
    }

    public boolean addProduct(Scanner input){
        System.out.println("Enter Product name: ");
        String name = input.nextLine();
        double price;
        int quantity;

        try {
            System.out.println("Enter Product Price:");
             price = input.nextDouble();

            System.out.println("Enter product quantity:");
            quantity = input.nextInt();
            input.nextLine(); // consume the leftover newline


            Product product = new Product(name,price,quantity);

            // Prevent duplicate product names
            if(products.containsKey(product.getName())){
                System.out.println("Product " + product.getName() + " already exists");
                return false;
            }

            products.put(product.getName(),product);

        }
        catch (InputMismatchException e){
            System.out.println("Please Enter a valid Integer/double");
            input.next();
            return false;
        }


        System.out.println("You have successfully added a new product");
    return true;

    }

    public boolean displayAllProducts(){

       if(products.size()>0){
           System.out.println("--".repeat(20));
           products.forEach((k,v)-> System.out.println(v)); //uses Product.toString
           System.out.println("--".repeat(20));
           return true;
       }
       System.out.println("No products found in Inventory");
       return false;
    }

    public boolean updateProduct(Scanner input){

        System.out.println("""
                What product do you want to update?
                Enter Product name: 
                """);

        String name = input.nextLine();

        if(name.trim().isEmpty()){
            System.out.println("Product name cannot be empty!");
            return false;
        }


        if(products.containsKey(name)){
            System.out.println("Enter what you wish to update about the product (price or quantity");
            String update = input.nextLine();

            switch (update.toLowerCase()){
                case "price" -> changePrice(name, input);
                case "quantity" -> changeQuantity(name,input);
                default -> System.out.println("Please input (price / quantity");
            }
            return true;
        }

        System.out.println("Product not found");
        return false;
    }

    private void changePrice(String name, Scanner input){
        Product prod = products.get(name);

        try {
            System.out.print("Enter new price: ");

            double price = input.nextDouble();


            if(price > 0 ){
                prod.setPrice(price);
                System.out.println("Price has been successfully updated!");
            }
            else {
                System.out.println("Price has not been updated due to the new price being less than 0");
            }

        } catch (InputMismatchException e) {
            System.out.println("Enter a valid number!!");
            input.next();
        }
    }

    private void changeQuantity(String name, Scanner input){
        Product prd = products.get(name);

        try {
            System.out.print("Enter new quantity: ");

            int quantity = input.nextInt();
            input.nextLine(); // consume the leftover newline


            prd.setQuantity(quantity);
            System.out.println("Quantity has been successfully updated!");
        } catch (InputMismatchException e) {
            System.out.println("Enter a valid number!!");
            input.nextLine();
        }
    }

    public boolean removeProduct(Scanner input){

        System.out.print("Enter the name of the product you wish to remove: ");
        String name = input.nextLine();

        if(products.containsKey(name)){
            products.remove(name);
            System.out.println("Product has been successfully removed!!");
            return true;
        }

        System.out.println("The Product " + name + " does not exist");
        return false;
    }
}
