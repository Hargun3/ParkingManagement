package d2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

public class ParkingSpaceTest {

    private ParkingSpace space;
    private ParkingLot lot;
    private Booking booking;
    private Set<ParkingSpace> vacantSpaces;

    @BeforeEach
    void setUp() {
        lot = new ParkingLot("LotA");
        space = new ParkingSpace(lot, "A1");
        vacantSpaces = new HashSet<>();
        vacantSpaces.add(space);
        
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 1); 
        Date oneHourLater = calendar.getTime();

        booking = new Booking(1, new Date(), oneHourLater, space, vacantSpaces, 10.0);
    }

    @Test
    void testConstructorValidInputs() {
        assertEquals("A1", space.getspace_Location());
        assertEquals(lot, space.getSpace_Lot());
        assertNotNull(space.getState()); 
    }

    @Test
    void testConstructorWithNullLot() {
        try {
            new ParkingSpace(null, "A1");
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("A ParkingSpace must belong to a ParkingLot.", e.getMessage());
        } 
    }

    @Test
    void testSetAndGetSpaceID() {
        space.setSpace_ID(101);
        assertEquals(101, space.getSpace_ID());
    }

    @Test
    void testSetAndGetSpaceLocation() {
        space.setSpace_Location("B2");
        assertEquals("B2", space.getspace_Location());
    }

    @Test
    void testSetAndGetSpaceLot() {
        ParkingLot lotB = new ParkingLot("LotB");
        space.setSpace_Lot(lotB);
        assertEquals(lotB, space.getSpace_Lot());
    }
    
    @Test
    void testSetAndGetBooking() {
        space.setBooking(booking);
        assertEquals(booking, space.getBooking());
    }

    @Test
    void testEnable() {
    	space.disable();
        space.enable();
        assertTrue(space.getState() instanceof VacantState);
    }

    @Test
    void testDisable() {
        space.disable();
        assertTrue(space.getState() instanceof DisabledState);
    }

    @Test
    void testOccupy() {
        space.occupy();
        assertTrue(space.getState() instanceof OccupiedState);
    }

    @Test
    void testVacate() {
        space.occupy();  
        space.vacate();  
        assertTrue(space.getState() instanceof VacantState);
    }

    @Test
    void testGetBookingInitiallyNull() {
        assertNull(space.getBooking()); 
    }

    @Test
    void testSetBookingToNull() {
        space.setBooking(null);
        assertNull(space.getBooking());
    }

    @Test
    void testSetAndGetState() {
        ParkingSpaceState newState = new OccupiedState(space);
        space.setState(newState);
        assertEquals(newState, space.getState());
    }
}