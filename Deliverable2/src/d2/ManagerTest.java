package d2;

import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class ManagerTest {
 
    private Manager manager;
    private final String csvPath = "Deliverable2/user.csv";
    private final List<String> addedTestLines = new ArrayList<>();

    @BeforeEach
    void setUp() throws IOException { 
        manager = new Manager();
        appendToCSV("manager1@example.com,Password123!,Manager,approved,1");
        appendToCSV("manager2@example.com,Password456!,Manager,pending,2");
        appendToCSV("manager3@example.com,Password789!,Admin,approved,3");
    }

    @AfterEach
    void tearDown() throws IOException {
        List<String> allLines = Files.readAllLines(Paths.get(csvPath));
        allLines.removeAll(addedTestLines); 
        Files.write(Paths.get(csvPath), allLines); 
        addedTestLines.clear(); 
    } 

    private void appendToCSV(String line) throws IOException {
        File file = new File(csvPath);
        file.getParentFile().mkdirs();
        boolean writeHeader = !file.exists() || file.length() == 0;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            if (writeHeader) {
                writer.write("email,password,role,status,id\n");  
            }
            writer.write(line + "\n");
            addedTestLines.add(line); 
        }
    }
 
    @Test
    void testLoginWithValidCredentials() throws IOException {
        assertTrue(manager.login("manager1@example.com", "Password123!"));
        assertEquals("manager1@example.com", manager.getEmail());
        assertEquals("Password123!", manager.getPassword());
    }

    @Test
    void testLoginWithInvalidCredentials() throws IOException {
        assertFalse(manager.login("invalid@example.com", "WrongPassword"));
        assertNull(manager.getEmail());
    }

    @Test
    void testLoginWithWrongPassword() throws IOException {
        assertFalse(manager.login("manager1@example.com", "WrongPassword"));
    }

    @Test
    void testLoginWithUnapprovedStatus() throws IOException {
        assertFalse(manager.login("manager2@example.com", "Password456!"));
    }

    @Test
    void testLoginWithNonManagerRole() throws IOException {
        assertFalse(manager.login("manager3@example.com", "Password789!"));
    }

    @Test
    void testLogout() {
        manager = new Manager("manager1@example.com", "Password123!");
        assertTrue(manager.logout());
    }

    @Test
    void testGetEmail() {
        manager = new Manager("manager1@example.com", "Password123!");
        assertEquals("manager1@example.com", manager.getEmail());
    }

    @Test
    void testGetUsername() {
        manager = new Manager("manager1@example.com", "Password123!");
        assertEquals("manager1@example.com", manager.getUsername());
    }

    @Test
    void testGetPassword() {
        manager = new Manager("manager1@example.com", "Password123!");
        assertEquals("Password123!", manager.getPassword());
    }

    @Test
    void testGetRate() {
        assertEquals(0.0, manager.getRate(), 0.01);
    }
}
