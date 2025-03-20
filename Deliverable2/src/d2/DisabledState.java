package d2;

public class DisabledState implements ParkingSpaceState {
	private ParkingSpace parkingSpace;

    public DisabledState(ParkingSpace parkingSpace) {
        this.parkingSpace = parkingSpace;
    }

    @Override
    public void enable() throws IllegalStateException {
        parkingSpace.setState(new VacantState(parkingSpace));
    }

    @Override
    public void disable() throws IllegalStateException {
        throw new IllegalStateException("Error: Parking space is already disabled.");
    }

    @Override
    public void occupy() throws IllegalStateException {
        throw new IllegalStateException("Error: Cannot occupy a disabled space.");
    }

    @Override
    public void vacate() throws IllegalStateException {
        throw new IllegalStateException("Error: Cannot vacate a disabled space.");
    }
}
