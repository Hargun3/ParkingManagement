package d2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VacantStateTest {

    private VacantState vacantState;
    private ParkingSpace space;
    private ParkingLot lot;

    @BeforeEach
    void setUp() {
        lot = new ParkingLot("LotA");
        space = new ParkingSpace(lot, "A1");
        vacantState = new VacantState(space);
        space.setState(vacantState);
    }

    @Test
    void testEnableAlreadyEnabled() {
        try {
            vacantState.enable();
            fail("Expected IllegalStateException was not thrown.");
        } catch (IllegalStateException e) {
            assertEquals("Error: Vacant state is already enabled.", e.getMessage());
        }
    }

    @Test
    void testDisableVacantSpace() {
        vacantState.disable();
        assertTrue(space.getState() instanceof DisabledState);
    }

    @Test
    void testOccupyVacantSpace() {
        vacantState.occupy();
        assertTrue(space.getState() instanceof OccupiedState);
    }

    @Test
    void testVacateAlreadyVacantSpace() {
        try {
            vacantState.vacate();
            fail("Expected IllegalStateException was not thrown.");
        } catch (IllegalStateException e) {
            assertEquals("Error: Space is already vacant.", e.getMessage());
        }
    } 

    @Test
    void testGetState() {
        assertTrue(space.getState() instanceof VacantState);
    }

    @Test
    void testDisableAndReEnable() {
        vacantState.disable(); 
        space.enable();         
        assertTrue(space.getState() instanceof VacantState);
    }

    @Test
    void testOccupyAndVacate() {
        vacantState.occupy();
        assertTrue(space.getState() instanceof OccupiedState);
        space.vacate();
        assertTrue(space.getState() instanceof VacantState);
    }
}
