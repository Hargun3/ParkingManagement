package d2;

public interface ParkingSpaceState {
	
	public void enable() throws IllegalStateException;
	public void disable() throws IllegalStateException;
	public void occupy() throws IllegalStateException;
	public void vacate() throws IllegalStateException;

}
