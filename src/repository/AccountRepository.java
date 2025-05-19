package repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Account;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * Repository class for managing account data persistence.
 * Uses JSON files (with Gson) to store and retrieve accounts.
 */
public class AccountRepository {
    private static final String file_Path = "data/accounts.json";  // Path to the account data file

    // Configured Gson instance with custom LocalDateTime adapter and pretty printing
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new util.LocalDateTimeAdapter())
            .setPrettyPrinting()
            .create();

    private static List<Account> accounts = new ArrayList<>();  // In-memory list of all accounts

    // Static block to initialize and load accounts when class is first loaded
    static{
        loadAccounts();
    }

    /**
     * Returns all accounts currently in memory.
     */
    public static List<Account> getAllAccounts(){
        return accounts;
    }

    /**
     * Adds an account to the repository and saves to disk if not in test mode.
     */
    public static void addAccount(Account account){
        accounts.add(account);
        if(!Config.TEST_MODE) {
            saveAccounts();
        }
    }

    /**
     * Serializes the accounts list into JSON and writes it to a file.
     * Skips saving if in test mode.
     */
    public static void saveAccounts(){

        if(Config.TEST_MODE){
            return;
        }

            try (FileWriter writer = new FileWriter(file_Path)) {
                gson.toJson(accounts, writer);
            } catch (IOException e) {
                System.out.println("Failed to save accounts: " + e.getMessage());
            }

    }

    /**
     * Loads the accounts list from a JSON file.
     * Initializes a new empty list if the file doesn't exist or is unreadable.
     */
    public static void loadAccounts(){
        if(Config.TEST_MODE){
            return;
        }
        File file = new File(file_Path);
        if(!file.exists()){
            accounts = new ArrayList<>();
            return;
        }

        try(FileReader reader = new FileReader(file_Path)){
            Type listType = new TypeToken<ArrayList<Account>>() {}.getType();
            accounts = gson.fromJson(reader,listType);
            if(accounts ==null){
                accounts = new ArrayList<>();
            }
        } catch (IOException e) {
            System.out.println("Failed to load accounts: " + e.getMessage());
        }
    }
}
