package d2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

public class AccountGeneratorTest {
 
    private AccountGenerator accountGenerator;
 
    @BeforeEach 
    void setUp() {
        accountGenerator = AccountGenerator.getInstance();
    }

    @Test
    void testGetInstance() { 
        AccountGenerator anotherInstance = AccountGenerator.getInstance();
        assertSame(accountGenerator, anotherInstance);
    }

    @Test
    void testGeneratedAccounts() {
        SuperManager superManager = SuperManager.getInstance();
        Set<Manager> managers = accountGenerator.accessForSuperManager(superManager);
        assertNotNull(managers);
        assertEquals(5, managers.size());
    }

    @Test
    void testNoDuplicateUsernames() {
    	 SuperManager superManager = SuperManager.getInstance();
         Set<Manager> generatedManagers = accountGenerator.accessForSuperManager(superManager);
         Set<String> usernames = new HashSet<>();
         for (Manager manager : generatedManagers) {
             assertTrue(usernames.add(manager.getUsername()), 
                     "Duplicate username found: " + manager.getUsername());
         }
         assertEquals(generatedManagers.size(), usernames.size(),
                 "Number of unique usernames does not match the number of generated managers.");
     } 
}