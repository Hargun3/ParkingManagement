package d2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
 
public class SuperManagerTest {

    private SuperManager superManager;
    private AccountGenerator accountGenerator;

    @BeforeEach
    void setUp() {
        superManager = SuperManager.getInstance(); 
        accountGenerator = AccountGenerator.getInstance();
    }

    @Test
    void testGetInstance() {
        SuperManager anotherInstance = SuperManager.getInstance();
        assertSame(superManager, anotherInstance);
    }

    @Test
    void testAccessAccountGenerator() {
        Set<Manager> managers = superManager.accessAccountGenerator(accountGenerator);
        assertNotNull(managers);
        assertEquals(5, managers.size()); 
    }

    @Test
    void testNoDuplicateAccounts() {
        Set<Manager> firstManagers = superManager.accessAccountGenerator(accountGenerator);
        Set<Manager> secondManagers = superManager.accessAccountGenerator(accountGenerator);
        Set<String> usernames = new HashSet<>();
        for (Manager manager : firstManagers) {
            assertTrue(usernames.add(manager.getUsername()), "Duplicate username found: " + manager.getUsername());
        }
        for (Manager manager : secondManagers) {
            assertTrue(usernames.add(manager.getUsername()), "Duplicate username found: " + manager.getUsername());
        }
        assertEquals(10, usernames.size());
    }
}