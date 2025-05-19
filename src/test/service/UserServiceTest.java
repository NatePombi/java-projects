package test.service;

import model.User;
import org.junit.jupiter.api.*;
import repository.Config;
import repository.UserRepository;
import service.UserService;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;


public class UserServiceTest {
    @BeforeEach
    void startUp(){
        // Enable test mode to avoid production data usage
        Config.TEST_MODE = true;
    }
    @Test
    void testRegisterUser_Success(){
        User user = UserService.registerUser("testUser","1234","Test");
        assertNotNull(user);
        assertEquals("testUser",user.getUsername());
        assertEquals("Test",user.getName());
    }

    @Test
    void testRegisterUser_Existing(){
        UserService.registerUser("UserTest","4321","Test1");
        Exception exception = assertThrows(IllegalArgumentException.class, ()->{
            UserService.registerUser("UserTest","4321","Test1");
        });

        assertTrue(exception.getMessage().contains("User already exists!"));
    }

    @Test
    void testRegisterUser_BlankInput(){
        Exception exception = assertThrows(NullPointerException.class, ()->{
            UserService.registerUser("UserTest1","","Test2");
        });
        assertEquals("Password field is empty",exception.getMessage());

    }

    @Test
    void testLoginUser_Success(){
        UserService.registerUser("test1","1234","testLog");
        User login = UserService.loginUser("test1","1234");
        assertNotNull(login);
        assertEquals("test1",login.getUsername());
    }

    @Test
    void testLoginUser_WrongPassword_Fail(){
        UserService.registerUser("wrongPass","wrong1234","wrong");
        Exception exception = assertThrows(IllegalArgumentException.class, ()->{
            UserService.loginUser("wrongPass","wrong");
        });
        assertTrue(exception.getMessage().contains("Incorrect password."));
    }

    @Test
    void testGetUserByUsername_Success(){
        UserService.registerUser("findUser","find1234","find");
        User found = UserService.getUserByUsername("findUser");
        assertNotNull(found);
        assertEquals("findUser", found.getUsername());
    }

    @Test
    void testGetUserByUsername_Failure(){
        Exception exception = assertThrows(NoSuchElementException.class, ()->{
            UserService.getUserByUsername("nonexistent");
        });
        assertTrue(exception.getMessage().contains("User not found."));
    }

    // Utility method to clear test data after each test
    @AfterEach
    public void cleanUp() {
        UserRepository.getAllUsers().clear();
    }


}
