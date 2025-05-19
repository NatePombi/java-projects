package test.service;

import exception.AccountNotFoundException;
import exception.InvalidTransactionAmount;
import model.Account;
import model.AccountType;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import repository.Config;
import repository.UserRepository;
import service.BankingService;
import service.SessionService;
import service.UserService;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class BankingServiceTest {

    @BeforeEach
     void startUp(){
        // Enable test mode to avoid production data usage
        Config.TEST_MODE = true;

        // Create and login a test user
        UserService.registerUser("bankTest","bank1234","bank");
        UserService.loginUser("bankTest","bank1234");
    }

    @Test
    void testOpenAccount_Success(){
        BankingService.openAccount(new BigDecimal("100"),"savings");
        User user = SessionService.getCurrentUser();
        assertEquals(1,user.getAccounts().size());

        Account account = user.getAccounts().get(0);
        assertEquals(new BigDecimal("100"), account.getBalance());
        assertEquals(AccountType.SAVINGS,account.getType());

    }

    @Test
    void testOpenAccount_NegativeAmount_Failure(){
        Exception exception = assertThrows(InvalidTransactionAmount.class,()->{
            BankingService.openAccount(new BigDecimal("-50"),"checking");
        });
        assertTrue(exception.getMessage().contains("-50"));

    }

    @Test
    void testOpenAccount_BlankType_ThrowException(){
        Exception exception = assertThrows(IllegalArgumentException.class, ()->{
          BankingService.openAccount(new BigDecimal("50"),"");
        });
        assertTrue(exception.getMessage().contains("Account type was not specified"));

    }

    @Test
    void testOpenAccount_ValidAccount_PrintBalance(){
        BankingService.openAccount(new BigDecimal("200"),"savings");
        String acc = SessionService.getCurrentUser().getAccounts().get(0 ).getAccountNumber();

        assertDoesNotThrow(()->{
            BankingService.viewBalance(acc);
        });
    }

    @Test
    void testViewBalance_inValidAccount_ThrowException(){
        Exception exception = assertThrows(AccountNotFoundException.class, ()->{
            BankingService.viewBalance("533564123");
        });
        assertTrue(exception.getMessage().contains("Account with number " + 533564123 + " not found"));
    }

    // Utility method to clear test data after each test
    @AfterEach
    public void cleanUp() {
        UserRepository.getAllUsers().clear();
    }
}
