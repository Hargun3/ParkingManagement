package d2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DisabledStateTest {

    private DisabledState disabledState;
    private ParkingSpace space;
    private ParkingLot lot;
 
    @BeforeEach
    void setUp() {
        lot = new ParkingLot("LotA");
        space = new ParkingSpace(lot, "A1");
        disabledState = new DisabledState(space);
        space.setState(disabledState);
    }

    @Test
    void testEnable() {
        disabledState.enable();
        assertTrue(space.getState() instanceof VacantState);
    }

    @Test
    void testDisableAlreadyDisabled() {
        try {
            disabledState.disable();  
            fail("Expected IllegalStateException was not thrown.");
        } catch (IllegalStateException e) {
            assertEquals("Error: Parking space is already disabled.", e.getMessage());
        }
    }

    @Test
    void testOccupyDisabledSpace() {
        try {
            disabledState.occupy();  
            fail("Expected IllegalStateException was not thrown.");
        } catch (IllegalStateException e) {
            assertEquals("Error: Cannot occupy a disabled space.", e.getMessage());
        }
    }

    @Test
    void testVacateDisabledSpace() {
        try {
            disabledState.vacate();  
            fail("Expected IllegalStateException was not thrown.");
        } catch (IllegalStateException e) {
            assertEquals("Error: Cannot vacate a disabled space.", e.getMessage());
        }
    }

    @Test
    void testGetState() {
        assertTrue(space.getState() instanceof DisabledState);
    }
  
}