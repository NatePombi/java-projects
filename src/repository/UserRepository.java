package repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Account;
import model.User;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing user data and persistence.
 * Users are stored in a JSON file and loaded into memory.
 * Their associated accounts are matched after loading.
 */
public class UserRepository {
    private static final String File_Path = "data/user.json";  // Path to the user data file

    // Gson instance with support for LocalDateTime serialization and pretty JSON output
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new util.LocalDateTimeAdapter())
            .setPrettyPrinting()
            .create();

    private static List<User> users = new ArrayList<>();

    // Static initializer loads users from the file when the class is loaded
    static {
        loadUsers();
    }


    /**
     * Returns the list of all loaded users.
     */
    public static List<User> getAllUsers(){
        return users;
    }

    /**
     * Adds a new user to the list and saves to disk (unless in test mode).
     */
    public static void addUser(User user){
        users.add(user);
        if(!Config.TEST_MODE) {
            saveUsers();
        }
    }

    /**
     * Finds and returns a user by their username.
     */
    public static User findUserByUsername(String username){
        return users.stream()
                .filter(s-> s.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    /**
     * Saves the list of users to the file as JSON.
     * Skips saving if in test mode.
     */
    public static void saveUsers(){

        if(Config.TEST_MODE){
            return;
        }

            try (FileWriter writer = new FileWriter(File_Path)) {
                gson.toJson(users, writer);
            } catch (IOException e) {
                System.out.println("Failed to save user: " + e.getMessage());
            }

    }


    /**
     * Loads users from the JSON file.
     * Also restores user-account associations based on owner usernames.
     */
    public static void loadUsers(){
        if(Config.TEST_MODE){
            return;
        }
        File file = new File(File_Path);
        if(!file.exists()){
            users = new ArrayList<>();
            return;
        }
        try(FileReader reader = new FileReader(File_Path)){
            Type listType = new TypeToken<ArrayList<User>>() {}.getType();
            users = gson.fromJson(reader, listType);
            if(users == null){
                users = new ArrayList<>();
            }


            //matching Accounts to respective user

            List<Account> allAccounts = AccountRepository.getAllAccounts();
            for(User user : users){
                for(Account account : allAccounts){

                    if(account.getOwnerUsername()!=null && account.getOwnerUsername().equals(user.getUsername())) {
                        user.addAccount(account);//restores account to user
                    }
                }
            }

        }  catch (IOException e) {
            System.out.println("Failed to load users: " + e.getMessage());
            users = new ArrayList<>();
        }
    }
}
