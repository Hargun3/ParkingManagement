package d2;

import java.util.Date;

public class ExtendBooking implements Command{
	
	private Booking booking;
	
	private Date toTime;
	
	private Date prevTime;

	public ExtendBooking(Booking booking, Date toTime) {
		this.booking = booking;
		this.toTime = toTime;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public Date getToTime() {
		return toTime;
	}

	public void setToTime(Date toTime) {
		this.toTime = toTime;
	}

	public Date getPrevTime() {
		return prevTime;
	}

	public void setPrevTime(Date prevTime) {
		this.prevTime = prevTime;
	}

	@Override
	public boolean execute() {
		Date currentDate = new Date();
		if (currentDate.getTime() <= booking.getExitTime().getTime()) {
			return false;
		}
		prevTime = booking.getExitTime();
		booking.setExitTime(toTime);
		return true;
	}

	@Override
	public boolean undo() {
		booking.setExitTime(prevTime);
		return true;
	}
	

}
