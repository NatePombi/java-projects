package service;

import model.User;

public class SessionService {
    private static User currentUser;

    // Sets the current user (used after successful login)
    public static void setCurrentUser(User currentUser){
        SessionService.currentUser =currentUser;
    }

    // Returns the currently logged-in user, or throws if no one is logged in
    public static User getCurrentUser(){
        if(currentUser == null){
            throw new IllegalStateException("No currentUser is signed in");
        }
        return currentUser;
    }

    // Logs out the current user
    public static void logOut(){
        currentUser = null;
    }

    // Checks if a user is currently logged in
    public static boolean isLoggedIn(){
        return SessionService.currentUser != null;
    }


}