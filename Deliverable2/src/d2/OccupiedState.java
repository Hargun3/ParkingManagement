package d2;

public class OccupiedState extends EnabledState {
	public OccupiedState(ParkingSpace parkingSpace) {
        super(parkingSpace);
    }

    @Override
    public void enable() throws IllegalStateException {
        throw new IllegalStateException("Error: Cannot enable an occupied space.");
    }

    @Override
    public void disable() throws IllegalStateException {
        throw new IllegalStateException("Error: Cannot disable an occupied space.");
    }

    @Override
    public void occupy() throws IllegalStateException {
        throw new IllegalStateException("Error: Space is already occupied.");
    }

    @Override
    public void vacate() {
        parkingSpace.setState(new VacantState(parkingSpace));
    }
}
