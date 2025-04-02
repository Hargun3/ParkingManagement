package d2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnabledStateTest {

    private EnabledState enabledState;
    private ParkingSpace space;
    private ParkingLot lot;

    @BeforeEach
    void setUp() {
        lot = new ParkingLot("LotA");
        space = new ParkingSpace(lot, "A1");
        enabledState = new EnabledState(space);
        space.setState(enabledState);
    }

    @Test 
    void testEnableAlreadyEnabled() {
        try {
            enabledState.enable();  
            fail("Expected IllegalStateException was not thrown.");
        } catch (IllegalStateException e) {
            assertEquals("Error: Parking space is already enabled.", e.getMessage());
        }
    }

    @Test
    void testDisable() {
        enabledState.disable();
        assertTrue(space.getState() instanceof DisabledState);
    }

    @Test
    void testOccupy() {
        enabledState.occupy();
        assertTrue(space.getState() instanceof OccupiedState);
    }

    @Test
    void testVacate() {
        enabledState.vacate();
        assertTrue(space.getState() instanceof VacantState); 
    }

    @Test
    void testGetState() {
        assertTrue(space.getState() instanceof EnabledState);
    }
    
    @Test
    void testDisableAndReEnable() {
        enabledState.disable();
        assertTrue(space.getState() instanceof DisabledState);
        space.enable();
        assertTrue(space.getState() instanceof VacantState);
    }

    @Test
    void testOccupyAndVacate() {
        enabledState.occupy();
        assertTrue(space.getState() instanceof OccupiedState);
        space.vacate();
        assertTrue(space.getState() instanceof VacantState);
    }

    
}
