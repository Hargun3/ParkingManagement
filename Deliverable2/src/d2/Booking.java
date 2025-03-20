package d2;

import java.util.Date;
import java.util.Set;

public class Booking {
	
	protected int id;
	protected Date startTime;
	protected Date exitTime;
	protected ParkingSpace bookedSpace;
	protected Set<ParkingSpace> vacantSpaces;
	protected double rate;
	
	
	
	public Booking(int id, Date startTime, Date exitTime, ParkingSpace bookedSpace, Set<ParkingSpace> vacantSpaces,
			double rate) {
		this.id = id;
		this.startTime = startTime;
		this.exitTime = exitTime;
		this.bookedSpace = bookedSpace;
		this.vacantSpaces = vacantSpaces;
		this.rate = rate;
	}

	public Booking() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getExitTime() {
		return exitTime;
	}

	public void setExitTime(Date exitTime) {
		this.exitTime = exitTime;
	}

	public ParkingSpace getBookedSpace() {
		return bookedSpace;
	}

	public void setBookedSpace(ParkingSpace bookedSpace) {
		this.bookedSpace = bookedSpace;
	}

	public Set<ParkingSpace> getAllVacantSpaces() {
		return vacantSpaces;
	}

	public void setVacantSpaces(Set<ParkingSpace> vacantSpaces) {
		this.vacantSpaces = vacantSpaces;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	private boolean checkLicensePlate(String licensePlate) {
        return licensePlate.matches("^[A-Z0-9]{1,7}(-| )?[A-Z0-9]{1,7}$");
	}
	
	public double calculateBookingCost(Date startTime, Date endTime, double rate) {
		long time = endTime.getTime() - startTime.getTime();
		double hours = time / (1000.0 * 60 * 60);
		return hours * rate;
	}
	
	public double calculateDeposit(double rate) {
		return rate;
	}
	
}
