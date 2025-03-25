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

	private String licensePlate;

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
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
	private boolean isPaid = false;

	public boolean isPaid() {
		return isPaid;
	}
	
	public void setPaid(boolean paid) {
		this.isPaid = paid;
	}
		

	public void extendByHours(int hours) {
		long extensionMillis = hours * 60L * 60L * 1000L;

		if (exitTime == null) {
			// Default to 1 hour after start time if not already set
			this.exitTime = new Date(startTime.getTime() + 60L * 60L * 1000L);
		}

		this.exitTime = new Date(exitTime.getTime() + extensionMillis);
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
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	@Override
	public String toString() {
		return "Booking #" + id + " | Start: " + startTime + " | End: " + exitTime + " | Space: "
				+ bookedSpace.getspace_Location();
	}

}
