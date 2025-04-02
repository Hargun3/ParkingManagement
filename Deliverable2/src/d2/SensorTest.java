package d2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SensorTest {

    private Sensor sensor;
    private ParkingSpace space;
    private ParkingLot lot;
    private Car car;
    private Student carOwner;

    @BeforeEach
    void setUp() {
        lot = new ParkingLot("LotA");
        space = new ParkingSpace(lot, "A1");
        sensor = new Sensor(space);
        carOwner = new Student();
        carOwner.setEmail("john@test.com");
        carOwner.setPassword("Password123!");
        carOwner.setId(1);
        car = new Car(carOwner, "Toyota", "Camry", "ABC123");
    }

    @Test
    void testConstructor() {
        assertNotNull(sensor);
        assertNull(sensor.getCurrentCar()); 
    }

    @Test
    void testScanCarInfoWithValidCar() {
        Car scannedCar = sensor.scanCarInfo(car);
        assertEquals(car, scannedCar);
        assertEquals(car, sensor.getCurrentCar());
        assertTrue(space.getState() instanceof OccupiedState);
    }

    @Test
    void testScanCarInfoWithNullCar() {
        sensor.scanCarInfo(null);
        assertNull(sensor.getCurrentCar());
        assertTrue(space.getState() instanceof VacantState);
    }

    @Test
    void testUpdateVacancyCarPresent() {
        sensor.scanCarInfo(car); 
        assertTrue(space.getState() instanceof OccupiedState);
    }

    @Test
    void testGetCurrentCarWithCar() {
        sensor.scanCarInfo(car);
        assertEquals(car, sensor.getCurrentCar());
    }

    @Test
    void testGetCurrentCarNoCar() {
        assertNull(sensor.getCurrentCar()); 
    }

    @Test
    void testSendCarInfoWithCar() {
        sensor.scanCarInfo(car);
        assertTrue(sensor.sendCarInfo()); 
    }

    @Test
    void testSendCarInfoNoCar() {
        sensor.scanCarInfo(null);
        assertFalse(sensor.sendCarInfo()); 
    }

    @Test
    void testScanCarInfoStateUpdate() {
        sensor.scanCarInfo(car);
        assertTrue(space.getState() instanceof OccupiedState);
        sensor.scanCarInfo(null);
        assertTrue(space.getState() instanceof VacantState);
    }

    @Test
    void testScanMultipleCars() {
        Car car1 = new Car(carOwner, "Honda", "Civic", "XYZ789");
        Car car2 = new Car(carOwner, "BMW", "M3", "DEF456");

        sensor.scanCarInfo(car1);
        assertEquals(car1, sensor.getCurrentCar());
        assertTrue(space.getState() instanceof OccupiedState);

        sensor.scanCarInfo(car2);
        assertEquals(car2, sensor.getCurrentCar());
        assertTrue(space.getState() instanceof OccupiedState);

        sensor.scanCarInfo(null);
        assertNull(sensor.getCurrentCar());
        assertTrue(space.getState() instanceof VacantState);
    }
}