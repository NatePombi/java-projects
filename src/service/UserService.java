package service;

import model.User;
import repository.UserRepository;
import util.PasswordUtil;

import java.util.NoSuchElementException;

public class UserService {

    // Registers a new user
    public static User registerUser(String username, String password, String name) {
        // Validate input fields
        if (username == null || username.isBlank()) {
            throw new NullPointerException("Username is empty");
        }
        if (password == null || password.isBlank()) {
            throw new NullPointerException("Password field is empty");
        }
        if (name == null || name.isBlank()) {
            throw new NullPointerException("Name field is empty");
        }

        // Trim input to avoid whitespace issues
        String userN = username.trim();
        String pass = password.trim();
        String nameU = name.trim();

        // Check if username is already taken
        if (UserRepository.findUserByUsername(userN) != null) {
            throw new IllegalArgumentException("User already exists!");
        }

        // Create and store the new user
        User user = new User(userN, pass, nameU);
        UserRepository.addUser(user);
        System.out.println("User successfully registered!");
        return user;
    }

    // Authenticates and logs in a user
    public static User loginUser(String username, String password) {
        if (username == null || username.isBlank()) {
            throw new NullPointerException("Username field is empty!");
        }
        if (password == null || password.isBlank()) {
            throw new NullPointerException("Password field is empty!");
        }

        // Look up user by username
        User user = UserRepository.findUserByUsername(username.trim());
        if (user == null) {
            throw new NoSuchElementException("User not found.");
        }

        // Verify password using secure hash comparison
        boolean isValid = PasswordUtil.verifyPassword(password, user.getPassword(), user.getSalt());
        if (!isValid) {
            throw new IllegalArgumentException("Incorrect password.");
        }

        // Set the user as logged in
        SessionService.setCurrentUser(user);
        System.out.println("User logged in successfully!");
        return user;
    }
    // Retrieves a user by username
    public static User getUserByUsername(String username) {
        User user = UserRepository.findUserByUsername(username.trim());
        if (user == null) {
            throw new NoSuchElementException("User not found.");
        }
        return user;
    }
}
