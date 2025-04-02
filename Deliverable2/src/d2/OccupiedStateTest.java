package d2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OccupiedStateTest {

    private OccupiedState occupiedState;
    private ParkingSpace space;
    private ParkingLot lot;

    @BeforeEach
    void setUp() {
        lot = new ParkingLot("LotA");
        space = new ParkingSpace(lot, "A1");
        occupiedState = new OccupiedState(space);
        space.setState(occupiedState);
    } 

    @Test
    void testEnableOccupiedSpace() {
        try {
            occupiedState.enable();
            fail("Expected IllegalStateException for enabling an occupied space");
        } catch (IllegalStateException e) {
            assertEquals("Error: Cannot enable an occupied space.", e.getMessage());
        }
    }
 
    @Test
    void testDisableOccupiedSpace() {
        try {
            occupiedState.disable();
            fail("Expected IllegalStateException for disabling an occupied space");
        } catch (IllegalStateException e) {
            assertEquals("Error: Cannot disable an occupied space.", e.getMessage());
        }
    }

    @Test
    void testOccupyAlreadyOccupiedSpace() {
        try {
            occupiedState.occupy();
            fail("Expected IllegalStateException for occupying an already occupied space");
        } catch (IllegalStateException e) {
            assertEquals("Error: Space is already occupied.", e.getMessage());
        }
    }
 
    @Test
    void testVacateOccupiedSpace() {
        occupiedState.vacate();
        assertTrue(space.getState() instanceof VacantState);
    }

    @Test
    void testGetState() {
        assertTrue(space.getState() instanceof OccupiedState);
    }

    @Test
    void testVacateAndOccupyAgain() {
        occupiedState.vacate(); 
        space.occupy();         
        assertTrue(space.getState() instanceof OccupiedState);
    }
}
