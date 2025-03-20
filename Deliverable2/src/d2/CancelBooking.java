package d2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class CancelBooking implements Command{
	
	private Booking booking;
	
	public CancelBooking(Booking booking) {
		this.booking = booking;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	@Override
	public boolean execute() throws NumberFormatException, IOException {
		Date currentDate = new Date();
		if (currentDate.getTime() <= booking.getStartTime().getTime()) {
			return false;
		}
		CsvReader reader = new CsvReader("booking.csv");
		ArrayList<String[]> list = new ArrayList<>();
		while(reader.readRecord() && reader.get("id") != ""){ 
			if (booking.getId() != Integer.valueOf(reader.get("id"))) {
				String[] temp = {reader.get("id"), reader.get("start_time"), reader.get("space")};
				list.add(temp);
			} 
		}
		CsvWriter output = new CsvWriter(new FileWriter("booking.csv", false), ',');
		for (String[] entry: list) {
			output.write(entry[0]);
			output.write(entry[1]);
			output.write(entry[2]);
			output.endRecord();
			output.close();
		}
		return true;
	}

	@Override
	public boolean undo() throws IOException {
		CsvWriter output = new CsvWriter(new FileWriter("booking.csv", true), ',');
		output.write(String.valueOf(booking.id));
		output.write(booking.startTime.toString());
		output.write(booking.bookedSpace.getspace_Location());
		output.endRecord();
		output.close();
		return true;
	}
	
	

}
