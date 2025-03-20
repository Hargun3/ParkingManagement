package d2;

public class VacantState extends EnabledState {
    public VacantState(ParkingSpace parkingSpace) {
        super(parkingSpace);
    }

    @Override
    public void enable() throws IllegalStateException {
        throw new IllegalStateException("Error: Vacant state is already enabled.");
    }

    @Override
    public void disable() {
        parkingSpace.setState(new DisabledState(parkingSpace));
    }

    @Override
    public void occupy() {
        parkingSpace.setState(new OccupiedState(parkingSpace));
    }

    @Override
    public void vacate() throws IllegalStateException {
        throw new IllegalStateException("Error: Space is already vacant.");
    }
}