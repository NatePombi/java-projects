package Service;

import Database.UserStore;
import Model.User;

import java.util.List;

/**
 * Service layer responsible for user operations like registration,
 * retrieval, and listing. Acts as an intermediary between the main
 * application and the data store.
 */

public class UserService {

    private final UserStore store;

    //constructor that initializes the store field
    public UserService() {
        store = new UserStore();
    }
    // Registers a new user if name and roles are valid
    public void registerUser(String name, List<String> roles){

        if(!name.isBlank() && !roles.isEmpty()){
            store.addUser( new User(name,roles));
        }
        else{
            System.out.println("All fields was not met, could not register User");
        }
    }

    // Finds and displays a user by name
    public void findUser(String name){

        if(!name.isBlank()){
            System.out.println(store.getUserByName(name));
        }
        else {
            System.out.println("No name was entered");
        }
    }

    //Retrieves all users that registered
    public void listAllUsers(){
        List<User> user = store.getAllUsers();

        if(user.isEmpty()){
            System.out.println("No users registered!");
        }
        else{
            user.forEach(s->{
                System.out.println("Name: " + s.getName());
                System.out.println("Roles: " + s.getRoles());
                System.out.println("---".repeat(20));
            });
        }
    }
}
