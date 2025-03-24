package d2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import com.csvreader.CsvWriter;

public class Paypal implements PaymentMethod{
	
	private String username;
	private String password;
	
	public boolean processPayment(double amountPaid) {
		return true;
	}
	
	public boolean processDeposit(double deposit) {
		return true;
	}
	
	public boolean refundDeposit() {
		return true;
	}
	
	private void getPaypalAccount(String username, String password) throws IOException {
		CsvWriter output = new CsvWriter(new FileWriter("Deliverable2/paypal.csv", true), ',');
		output.write(username);
		output.write(password);
		output.endRecord();
		output.close();
	}
	
}
