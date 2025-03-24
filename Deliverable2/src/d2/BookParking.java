package d2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class BookParking implements Command {
    private Booking booking;
    private ParkingSpace space;
    private String userEmail;

    public BookParking(Booking booking, ParkingSpace space, String userEmail) {
        this.booking = booking;
        this.space = space;
        this.userEmail = userEmail;
    }

    @Override
    public boolean execute() throws IOException {
        CsvWriter output = null;
        try {
            output = new CsvWriter(new FileWriter("Deliverable2/booking.csv", true), ',');
            output.write(String.valueOf(booking.id));
            output.write(booking.startTime.toString());
            output.write(space.getspace_Location());
            output.write(booking.getLicensePlate());
            output.write(userEmail);
            output.endRecord();
        } finally {
            if (output != null) {
                output.close();
            }
        }
        return true;
    }

    @Override
    public boolean undo() throws IOException {
        CsvReader reader = new CsvReader("Deliverable2/booking.csv");
        ArrayList<String[]> list = new ArrayList<>();

        while (reader.readRecord()) {
            if (!reader.get("id").equals(String.valueOf(booking.id))) {
                list.add(new String[]{
                    reader.get(0), // id
                    reader.get(1), // startTime
                    reader.get(2), // space
                    reader.get(3), // licensePlate
                    reader.get(4)  // email
                });
                
            }
        }
        reader.close();

        CsvWriter output = null;
        try {
            output = new CsvWriter(new FileWriter("Deliverable2/booking.csv", false), ',');

            // Write header only if the file is empty
            if (list.isEmpty()) {
                output.write("id");
                output.write("startTime");
                output.write("space");
                output.write("licensePlate");
                output.write("email");
                output.endRecord();
            }

            for (String[] entry : list) {
                output.write(entry[0]);
                output.write(entry[1]);
                output.write(entry[2]);
                output.write(entry[3]);
                output.write(entry[4]);
                output.endRecord();
            }
        } finally {
            if (output != null) {
                output.close();
            }
        }

        return true;
    }
}
