package d2;

import java.io.FileWriter;
import java.util.ArrayList;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class EditBooking implements Command{
	
	private Booking booking;
	private ParkingSpace newSpace;
	private ParkingSpace oldSpace;
	
	@Override
	public boolean execute() {
		oldSpace = booking.getBookedSpace();
		booking.setBookedSpace(newSpace);
		CsvReader reader = new CsvReader("booking.csv");
		ArrayList<String[]> list = new ArrayList<>();
		while(reader.readRecord() && reader.get("id") != ""){ 
				String[] temp = {reader.get("id"), reader.get("start_time"), reader.get("space")};
				list.add(temp);
		}
		CsvWriter output = new CsvWriter(new FileWriter("booking.csv", false), ',');
		for (String[] entry: list) {
			output.write(entry[0]);
			output.write(entry[1]);
			if (entry[2] == oldSpace) {
				output.write(newSpace.getspace_Location());
			} else {
				output.write(entry[2]);
			}
			output.endRecord();
			output.close();
		}
		return true;
	}
	
	@Override
	public boolean undo() {
		booking.setBookedSpace(oldSpace);
		CsvReader reader = new CsvReader("booking.csv");
		ArrayList<String[]> list = new ArrayList<>();
		while(reader.readRecord() && reader.get("id") != ""){ 
				String[] temp = {reader.get("id"), reader.get("start_time"), reader.get("space")};
				list.add(temp);
		}
		CsvWriter output = new CsvWriter(new FileWriter("booking.csv", false), ',');
		for (String[] entry: list) {
			output.write(entry[0]);
			output.write(entry[1]);
			if (entry[2] == newSpace.getspae_Location()) {
				output.write(oldSpace.getspace_Location());
			} else {
				output.write(entry[2]);
			}
			output.endRecord();
			output.close();
		}
		return true;

	}
	
	
	
}
