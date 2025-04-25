package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Immutable User model class that stores a username and a list of roles.
 * This class demonstrates defensive copying and unmodifiable access to protect internal state.
 */
public final class User {
    private final String username;
    private final List<String> roles;

    // Constructor that sets the username and creates a defensive copy of the roles list to preserve immutability
    public User(String name, List<String> roles) {
        this.username = name;
        this.roles =  new ArrayList<>(roles);// defensive copying
    }

    //returns username
    public String getName() {
        return username;
    }

    //returns unmodifiable read only list of roles
    public List<String> getRoles() {
        return Collections.unmodifiableList(roles); //Read only
    }

    //Overriding toString for better format for output
    @Override
    public String toString() {
        return """
                User:
                Name: %s
                Roles: %s
                """.formatted(username,roles);

    }
}