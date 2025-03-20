package d2;

public class Sensor implements ParkingSpaceObserver {
    private int sensor_ID;
    private ParkingSpace sensor_ParkingSpace;  
    private Car current_Car;

    public Sensor(ParkingSpace sensor_ParkingSpace) {
        this.sensor_ParkingSpace = sensor_ParkingSpace;
        this.current_Car = null; 
    }

    @Override
    public void updateVacancy() {
    	if (current_Car == null) {
            sensor_ParkingSpace.setState(new VacantState(sensor_ParkingSpace)); 
        } else {
            sensor_ParkingSpace.setState(new OccupiedState(sensor_ParkingSpace)); 
        }
    }

    public Car scanCarInfo(Car car) {
        this.current_Car = car;
        updateVacancy();
        return current_Car;
    }

    public boolean sendCarInfo() {
        return current_Car != null;
    }
    
    public Car getCurrentCar() {
        return current_Car;
    }

}