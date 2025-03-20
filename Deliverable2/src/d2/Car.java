package d2;

public class Car {
    private Client car_Owner;
    private String car_Brand;
    private String car_Model;
    private String car_LicensePlate;

    public Car(Client car_Owner, String car_Brand, String car_Model, String car_LicensePlate) {
        this.car_Owner = car_Owner;
        this.car_Brand = car_Brand;
        this.car_Model = car_Model;
        this.car_LicensePlate = car_LicensePlate;
    }

    public Client getCarOwner() {
        return car_Owner;
    }

    public String getCarBrand() {
        return car_Brand;
    }

    public String getCarModel() {
        return car_Model;
    }

    public String getCarLicensePlate() {
        return car_LicensePlate;
    }

    public void setCarBrand(String car_Brand) {
        this.car_Brand = car_Brand;
    }

    public void setCarModel(String car_Model) {
        this.car_Model = car_Model;
    }

    public void setCarLicensePlate(String car_LicensePlate) {
        this.car_LicensePlate = car_LicensePlate;
    }
}
