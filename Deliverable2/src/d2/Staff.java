package d2;

import java.io.FileWriter;
import java.io.IOException;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class Staff implements Client{
	
	private int id;
	private String email;
	private String password;
	private String licensePlate;
	private Booking booking;
	private PaymentMethod method;
	private Double rate = 10.0;
	
	
	
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getLicensePlate() {
		return licensePlate;
	}



	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}



	public Booking getBooking() {
		return booking;
	}



	public void setBooking(Booking booking) {
		this.booking = booking;
	}



	public PaymentMethod getMethod() {
		return method;
	}



	public void setMethod(PaymentMethod method) {
		this.method = method;
	}



	public double getRate() {
		return rate;
	}


	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public boolean register(String email, String password, int id) throws NumberFormatException, IOException {
		// add managment validate
		CsvReader reader = new CsvReader("user.csv"); 
		Staff staff = new Staff();
		if (password.matches("(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).+") == false) {
			return false;
		}
		while(reader.readRecord() && reader.get("id") != ""){ 
			//name,id,email,password
			staff.setId(Integer.valueOf(reader.get("id")));
			staff.setEmail(reader.get("email"));
			staff.setPassword(reader.get("password"));
			if (staff.getId() == id || staff.getEmail() == email) {
				return false;
			} 
		}
		CsvWriter output = new CsvWriter(new FileWriter("user.csv", true), ',');
		output.write(String.valueOf(id));
		output.write(email);
		output.write(password);
		output.endRecord();
		output.close();
		return true;
	}
	
	public boolean login(String email, String password) throws NumberFormatException, IOException {
		CsvReader reader = new CsvReader("user.csv"); 
		Staff staff = new Staff();
		while(reader.readRecord() && reader.get("id") != ""){ 
			//name,id,email,password
			staff.setId(Integer.valueOf(reader.get("id")));
			staff.setEmail(reader.get("email"));
			staff.setPassword(reader.get("password"));
			if (staff.getEmail() == email && staff.getPassword() == password) {
				return true;
			} 
		}
		return false;
		
	}
	
	public boolean logout() {
		return true;
	}

	public void initiateBooking() {
		Booking booking = new Booking();
	}
	
	public void initiatePayment() {
		Payment payment = new Payment();
	}

}
