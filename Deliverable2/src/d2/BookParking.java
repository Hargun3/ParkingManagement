package d2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class BookParking implements Command{
	
	private Booking booking;
	private ParkingSpace space;
	
	@Override
	public boolean execute() {
		CsvWriter output = new CsvWriter(new FileWriter("booking.csv", true), ',');
		output.write(String.valueOf(booking.id));
		output.write(booking.startTime.toString());
		output.write(space.getSpace_Location);
		output.endRecord();
		output.close();
	}
	
	@Override
	public boolean undo() throws NumberFormatException, IOException {
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
	
	

}
