package d2;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Client {
	
	public boolean register(String email, String password, int id) throws FileNotFoundException, NumberFormatException, IOException;
	public boolean login(String email, String password) throws FileNotFoundException, NumberFormatException, IOException;
	public boolean logout();
	
	public double getRate();
	public void initiateBooking();
	public void initiatePayment();
	

}
