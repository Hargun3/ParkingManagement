package d2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CarTest {

    private Car car;
    private Student carOwner;

    @BeforeEach
    void setUp() {
        carOwner = new Student();
        carOwner.setEmail("johndoe@example.com");
        carOwner.setPassword("Password123!");
        carOwner.setId(1);
        car = new Car(carOwner, "Toyota", "Camry", "ABC123");
    }

    @Test
    void testConstructorValidInputs() {
        assertEquals(carOwner, car.getCarOwner());
        assertEquals("Toyota", car.getCarBrand());
        assertEquals("Camry", car.getCarModel());
        assertEquals("ABC123", car.getCarLicensePlate());
    }

    @Test
    void testGetCarOwner() {
        assertEquals(carOwner, car.getCarOwner());
    }

    @Test
    void testGetCarBrand() {
        assertEquals("Toyota", car.getCarBrand());
    }

    @Test
    void testGetCarModel() {
        assertEquals("Camry", car.getCarModel());
    }

    @Test
    void testGetCarLicensePlate() {
        assertEquals("ABC123", car.getCarLicensePlate());
    }

    @Test
    void testSetCarBrand() {
        car.setCarBrand("Honda");
        assertEquals("Honda", car.getCarBrand());
    }

    @Test
    void testSetCarModel() {
        car.setCarModel("Civic");
        assertEquals("Civic", car.getCarModel());
    }

    @Test
    void testSetCarLicensePlate() {
        car.setCarLicensePlate("XYZ789");
        assertEquals("XYZ789", car.getCarLicensePlate());
    }

    @Test
    void testSetCarBrandEmptyString() {
        car.setCarBrand("");
        assertEquals("", car.getCarBrand());
    }

    @Test
    void testSetCarModelEmptyString() {
        car.setCarModel("");
        assertEquals("", car.getCarModel());
    }

    @Test
    void testSetCarLicensePlateEmptyString() {
        car.setCarLicensePlate("");
        assertEquals("", car.getCarLicensePlate());
    }
}