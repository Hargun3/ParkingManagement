package d2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

public class ParkingLotTest {

    private ParkingLot lot;
    private ParkingSpace space1;
    private ParkingSpace space2;

    @BeforeEach
    void setUp() {
        lot = new ParkingLot("LotA"); 
        space1 = new ParkingSpace(lot, "A1");
        space2 = new ParkingSpace(lot, "A2");
    } 

    @Test
    void testDefaultConstructor() {
        ParkingLot emptyLot = new ParkingLot();
        assertNotNull(emptyLot.getVacantSpaces()); 
    }

    @Test
    void testConstructorWithName() {
        assertEquals("LotA", lot.getLotName());
        assertTrue(lot.getLotEnabled());  
    }
 
    @Test
    void testGetLotName() {
        assertEquals("LotA", lot.getLotName());
    }

    @Test
    void testGetLotEnabledDefault() {
        assertTrue(lot.getLotEnabled());
    }

    @Test
    void testSetLotEnabledTrue() {
        lot.setLotEnabled(true); 
        assertTrue(lot.getLotEnabled());
    }

    @Test
    void testGetVacantSpacesInitiallyEmpty() {
        Set<ParkingSpace> vacantSpaces = lot.getVacantSpaces();
        assertTrue(vacantSpaces.isEmpty());
    }

    @Test
    void testGetVacantSpacesWithSpaces() {
        lot.getVacantSpaces().add(space1);
        lot.getVacantSpaces().add(space2);
        Set<ParkingSpace> vacantSpaces = lot.getVacantSpaces();
        assertEquals(2, vacantSpaces.size());
        assertTrue(vacantSpaces.contains(space1));
        assertTrue(vacantSpaces.contains(space2));
    }

    @Test
    void testDisableLotAndCheckStatus() {
        lot.setLotEnabled(false);
        assertFalse(lot.getLotEnabled());
    }

}

