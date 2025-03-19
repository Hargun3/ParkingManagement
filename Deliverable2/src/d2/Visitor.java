package d2;

import java.io.FileWriter;
import java.io.IOException;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class Visitor implements Client{
	
	private int id;
	private String email;
	private String password;
	private String licensePlate;
	private Booking booking;
	private PaymentMethod method;
	private Double rate = 15.0;
	
	
	
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
		CsvReader reader = new CsvReader("user.csv"); 
		Visitor visitor = new Visitor();
		if (password.matches("(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).+") == false) {
			return false;
		}
		while(reader.readRecord() && reader.get("id") != ""){ 
			//name,id,email,password
			visitor.setId(Integer.valueOf(reader.get("id")));
			visitor.setEmail(reader.get("email"));
			visitor.setPassword(reader.get("password"));
			if (visitor.getId() == id || visitor.getEmail() == email) {
				return false;
			} 
		}
		CsvWriter output = new CsvWriter(new FileWriter("user.csv", false), ',');
		output.write(String.valueOf(id));
		output.write(email);
		output.write(password);
		output.endRecord();
		output.close();
		return true;
		
	}
	
	public boolean login(String email, String password) throws NumberFormatException, IOException {
		CsvReader reader = new CsvReader("user.csv"); 
		Visitor visitor = new Visitor();
		while(reader.readRecord() && reader.get("id") != ""){ 
			//name,id,email,password
			visitor.setId(Integer.valueOf(reader.get("id")));
			visitor.setEmail(reader.get("email"));
			visitor.setPassword(reader.get("password"));
			if (visitor.getEmail() == email && visitor.getPassword() == password) {
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
