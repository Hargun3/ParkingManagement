package d2;

public class EnabledState implements ParkingSpaceState {
	protected ParkingSpace parkingSpace;

    public EnabledState(ParkingSpace parkingSpace) {
        this.parkingSpace = parkingSpace;
    }

    @Override
    public void enable() throws IllegalStateException {
        throw new IllegalStateException("Error: Parking space is already enabled.");
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
    public void vacate() {
        parkingSpace.setState(new VacantState(parkingSpace));
    }
}
 