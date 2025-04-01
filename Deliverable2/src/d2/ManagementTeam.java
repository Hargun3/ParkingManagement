package d2;

import java.util.*;

public class ManagementTeam {
	private int team_ID;
    private String team_Name;
    private Map<Integer, String> clientID_To_Role; 
    private Set<ParkingLot> managedParkingLots;
    
    public ManagementTeam() {
        this.clientID_To_Role = new HashMap<>();
        this.managedParkingLots = new HashSet<>();
    }

    public boolean validateClient(int client_ID, String client_Role) {
        return client_Role.equals(clientID_To_Role.get(client_ID));
    }

    public Car getCarInfoFromSpace(ParkingSpace space, Car car) { 
        Sensor sensor = new Sensor(space);
        return sensor.scanCarInfo(car);

    }

    public boolean addParkingLot(ParkingLot lot) {
        return managedParkingLots.add(lot);
    }

    public boolean removeParkingLot(ParkingLot lot) {
        return managedParkingLots.remove(lot);
    }

    public boolean enableParkingLot(ParkingLot lot) {
    	if (managedParkingLots.contains(lot)) {
            lot.setLotEnabled(true); 
            return true;
        }
        return false;
    }

    public boolean disableParkingLot(ParkingLot lot) {
    	if (managedParkingLots.contains(lot)) {
            lot.setLotEnabled(false); 
            return true;
        }
        return false;
    }

    public boolean enableParkingSpace(ParkingSpace space) {
        space.enable();
        return true;
    }

    public boolean disableParkingSpace(ParkingSpace space) {
        space.disable();
        return true;
    }

}
