package d2;
import java.io.FileWriter;
import java.util.List;
import com.csvreader.CsvWriter;

public class ParkingSpace {
    private ParkingSpaceState state;
    private int space_ID;
    private ParkingLot space_Lot;
    private String space_Location;
    private static final String CSV_FILE = "parking_spaces.csv";
    private static boolean headerWritten = false;
    
    
    public int getSpace_ID() {
		return space_ID;
	}

	public void setSpace_ID(int space_ID) {
		this.space_ID = space_ID;
	}

	public ParkingLot getSpace_Lot() {
		return space_Lot;
	}

	public void setSpace_Lot(ParkingLot space_Lot) {
		this.space_Lot = space_Lot;
	}

	public String getspace_Location() {
		return space_Location;
	}

	public void setSpace_Location(String space_Location) {
		this.space_Location = space_Location;
	}

	private ParkingSpace() {
        throw new UnsupportedOperationException("ParkingSpace must have a ParkingLot and location.");
    }

    public ParkingSpace(ParkingLot space_Lot, String space_Location) {
        if (space_Lot == null) {
            throw new IllegalArgumentException("A ParkingSpace must belong to a ParkingLot.");
        }
        this.space_Lot = space_Lot;
        this.space_Location = space_Location;
        this.state = new VacantState(this);  
	try {
            writeToCSV(); 
        } catch (Exception e) {
            System.out.println("Failed to write parking space into CSV: " + e.getMessage());
        }
    }

    public void enable() {
        state.enable();
    }

    public void disable() {
        state.disable();
    }

    public void occupy() {
        state.occupy();
    }

    public void vacate() {
        state.vacate();
    }

    public void setState(ParkingSpaceState state) {
        this.state = state;
    }

    public ParkingSpaceState getState() {
        return state;
    }

private void writeToCSV() throws Exception {
        boolean fileExists = new File(CSV_FILE).exists();
        CsvWriter writer = new CsvWriter(new FileWriter(CSV_FILE, true), ',');
        if (!fileExists || !headerWritten) {
            writer.write("LotName");
            writer.write("SpaceID");
            writer.write("Location");
            writer.write("State");
            writer.endRecord();
            headerWritten = true;
        }
        writer.write(space_Lot.getLotName());
        writer.write(String.valueOf(space_ID));
        writer.write(space_Location);
        writer.write(state.getClass().getSimpleName());
        writer.endRecord();
        writer.close();
    }
}
