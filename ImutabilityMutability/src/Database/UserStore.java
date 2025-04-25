package Database;

import Model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


/**
 * A simple in-memory store that manages a list of users.
 * Supports adding users, retrieving all users, and searching by name.
 */
public class UserStore {

    private final List<User> users;

    // Constructor that initializes the internal user list
    public UserStore(){
        users = new ArrayList<>();
    }

    //operation to add user to the User list
    public void addUser(User user){

        if(user != null){
            users.add(user);
            System.out.println("User was successfully added!");
        }
        else{
            System.out.println("User was not added!!");
        }
    }


    // Returns an unmodifiable list of all registered users
    public List<User> getAllUsers(){
        return Collections.unmodifiableList(users);
    }


    // Retrieves a user by name (case-insensitive match)
    public Optional<User> getUserByName(String name){

        return users.stream()
                .filter(u-> u.getName().equalsIgnoreCase(name))
                .findFirst();
    }

}
