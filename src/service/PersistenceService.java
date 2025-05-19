package service;

import repository.AccountRepository;
import repository.UserRepository;

public class PersistenceService {

    //calls the save methods in User and Account Repository
    public static void saveAll(){
        UserRepository.saveUsers();
        AccountRepository.saveAccounts();
    }
}
