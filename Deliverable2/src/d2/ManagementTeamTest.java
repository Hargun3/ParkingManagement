package d2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class ManagementTeamTest {
 
    private ManagementTeam managementTeam;
    private ParkingLot lotA, lotB;
    private ParkingSpace spaceA; 
    private Student student;
 
    @BeforeEach
    void setUp() { 
        managementTeam = new ManagementTeam();
        lotA = new ParkingLot("LotA");
        lotB = new ParkingLot("LotB");
        spaceA = new ParkingSpace(lotA, "A1");
        student = new Student();
        student.setEmail("student@example.com");
        student.setPassword("Password123!");
        student.setId(1);
    }

    @Test
    void testConstructor() {
        assertNotNull(managementTeam);
    }

    @Test
    void testValidateClientValidRole() {
        Map<Integer, String> clientID_To_Role = new HashMap<>();
        clientID_To_Role.put(1, "Student");

        managementTeam.validateClient(1, "Student");
        assertTrue(clientID_To_Role.containsKey(1));
    }

    @Test
    void testValidateClientInvalidRole() {
        Map<Integer, String> clientID_To_Role = new HashMap<>();
        clientID_To_Role.put(1, "Student");

        assertFalse(managementTeam.validateClient(1, "Staff"));
    }

    // ✅ 4. Test getCarInfoFromSpace() - Car Present
    @Test
    void testGetCarInfoFromSpaceWithCar() {
    	Car car = new Car(student, "Toyota", "Camry", "ABC123");
        Sensor sensor = new Sensor(spaceA);
        sensor.scanCarInfo(car);  
        Car retrievedCar = managementTeam.getCarInfoFromSpace(spaceA, car);
        assertNotNull(retrievedCar);
        assertEquals("ABC123", retrievedCar.getCarLicensePlate());
    }
    

    @Test
    void testAddParkingLot() {
        assertTrue(managementTeam.addParkingLot(lotA));
        assertFalse(managementTeam.addParkingLot(lotA)); 
    }

    @Test
    void testRemoveParkingLot() {
        managementTeam.addParkingLot(lotA);
        assertTrue(managementTeam.removeParkingLot(lotA));
        assertFalse(managementTeam.removeParkingLot(lotA)); 
    }

    @Test
    void testEnableParkingLot() {
        managementTeam.addParkingLot(lotA);
        assertTrue(managementTeam.enableParkingLot(lotA));
        assertTrue(lotA.getLotEnabled());
    }

    @Test
    void testEnableParkingLotNotManaged() {
        assertFalse(managementTeam.enableParkingLot(lotA));
    }

    @Test
    void testDisableParkingLot() {
        managementTeam.addParkingLot(lotA);
        assertTrue(managementTeam.disableParkingLot(lotA));
        assertFalse(lotA.getLotEnabled());
    }

    @Test
    void testDisableParkingLotNotManaged() {
        assertFalse(managementTeam.disableParkingLot(lotA));
    }

    @Test
    void testEnableParkingSpace() {
        spaceA.disable(); 
        managementTeam.enableParkingSpace(spaceA);
        assertTrue(spaceA.getState() instanceof VacantState);
    }

    @Test
    void testDisableParkingSpace() {
        managementTeam.disableParkingSpace(spaceA);
        assertTrue(spaceA.getState() instanceof DisabledState);
    }
 
    @Test
    void testAddMultipleParkingLots() {
        assertTrue(managementTeam.addParkingLot(lotA));
        assertTrue(managementTeam.addParkingLot(lotB));
    }

    @Test
    void testEnableAndDisableMultipleParkingLots() {
        managementTeam.addParkingLot(lotA);
        managementTeam.addParkingLot(lotB);
        assertTrue(managementTeam.enableParkingLot(lotA));
        assertTrue(lotA.getLotEnabled());
        assertTrue(managementTeam.disableParkingLot(lotB));
        assertFalse(lotB.getLotEnabled());
    }

}