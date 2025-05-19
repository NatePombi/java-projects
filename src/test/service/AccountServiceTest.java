package test.service;

import exception.InvalidTransactionAmount;
import exception.InvalidTransferAmountFrom;
import model.Account;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.Config;
import repository.UserRepository;
import service.AccountService;
import service.UserService;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class AccountServiceTest {

    static User user;
    static Account acc1;
    static Account acc2;
    @BeforeEach
    void startUp(){

        // Enable test mode to avoid production data usage
        Config.TEST_MODE = true;

        // Create and login a test user
        user = UserService.registerUser("Testing","testing12","Tester");
        UserService.loginUser("Testing","testing12");

        // Create two accounts: one with 1000, another with 500
        acc1 = AccountService.createAccount(new BigDecimal("1000"),"savings", user);
        acc2 = AccountService.createAccount(new BigDecimal("500"),"checking",user);
    }

    @Test
    void testDeposit_ValidAmount(){
        AccountService.deposit(acc1.getAccountNumber(),new BigDecimal("50"));
        assertEquals(new BigDecimal("1050"),acc1.getBalance());



    }

    @Test
    void testDeposit_inValidAmount(){
        Exception exception = assertThrows(InvalidTransactionAmount.class,()->{
            AccountService.deposit(acc1.getAccountNumber(),new BigDecimal("-100"));
        });
        assertTrue(exception.getMessage().contains("-100"));
    }

    @Test
    void testWithdraw_ValidAmount(){
        AccountService.withdraw(acc2.getAccountNumber(),new BigDecimal("100"));
        assertEquals(new BigDecimal("400"),acc2.getBalance());

    }

    @Test
    void testWithdraw_InsufficientFunds(){
        Exception exception = assertThrows(InvalidTransactionAmount.class,()->{
            AccountService.withdraw(acc2.getAccountNumber(), new BigDecimal("800"));
        });
        assertTrue(exception.getMessage().contains("800"));
    }

    @Test
    void testTransfer_Success(){
        AccountService.transfer(acc1.getAccountNumber(), acc2.getAccountNumber(), new BigDecimal("500"));
        assertEquals(new BigDecimal("1000"),acc2.getBalance());

    }

    @Test
    void testTransfer_InsufficientFunds(){
        Exception exception = assertThrows(InvalidTransferAmountFrom.class,()->{
            AccountService.transfer(acc1.getAccountNumber(), acc2.getAccountNumber(), new BigDecimal("2000"));
        });
        assertTrue(exception.getMessage().contains("2000"));

    }

    // Utility method to clear test data after each test
    @AfterEach
    public void cleanUp() {
        UserRepository.getAllUsers().clear();
    }

}
