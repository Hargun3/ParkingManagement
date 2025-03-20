package d2;

public class ParkingSpace {
    private ParkingSpaceState state;
    private int space_ID;
    private ParkingLot space_Lot;
    private String space_Location;
    
    private ParkingSpace() {
        throw new UnsupportedOperationException("ParkingSpace must have a ParkingLot and location.");
    }

    public ParkingSpace(ParkingLot space_Lot, String space_Location) {
        if (space_Lot == null) {
            throw new IllegalArgumentException("A ParkingSpace must belong to a ParkingLot.");
        }
        this.space_Lot = space_Lot;
        this.space_Location = space_Location;
        this.state = new VacantState(this);  
    }

    public void enable() {
        state.enable();
    }

    public void disable() {
        state.disable();
    }

    public void occupy() {
        state.occupy();
    }

    public void vacate() {
        state.vacate();
    }

    public void setState(ParkingSpaceState state) {
        this.state = state;
    }

    public ParkingSpaceState getState() {
        return state;
    }
}
