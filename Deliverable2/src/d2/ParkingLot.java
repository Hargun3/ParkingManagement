package d2;

import java.util.HashSet;
import java.util.Set;


public class ParkingLot {
    private int lot_ID;
    private String lot_Name;
    private boolean lot_IsEnabled;
    private String lot_Location;
    private Set<ParkingSpace> allSpaces;
    private Set<ParkingSpace> vacantSpaces;

    public ParkingLot() {
        this.allSpaces = new HashSet<>();
        this.vacantSpaces = new HashSet<>();
    }

    public ParkingLot(String lot_Name) {
        this();
        this.lot_Name = lot_Name;
        this.lot_IsEnabled = true; 
    }

    public String getLotName() {
        return lot_Name;
    }

    public boolean getLotEnabled() {
        return lot_IsEnabled;
    }

    public Set<ParkingSpace> getVacantSpaces() {
        return vacantSpaces;
    }
    
    public void setLotEnabled(boolean enabled) {
        this.lot_IsEnabled = enabled;
    }
}

