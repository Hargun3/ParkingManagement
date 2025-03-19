package d2;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import com.csvreader.CsvWriter;

public class Credit implements PaymentMethod{
	private int card_number;
	private Date card_expDate;
	private int card_CVV;
	
	public boolean processPayment(double amountPaid) {
		return true;
	}
	
	public boolean processDeposit(double deposit) {
		return true;
	}
	
	public boolean refundDeposit() {
		return true;
	}
	
	private void getCardDetails(int card_Number, Date card_expDate, int card_CVV) throws IOException {
		CsvWriter output = new CsvWriter(new FileWriter("cards.csv", true), ',');
		output.write(String.valueOf(card_Number));
		output.write(card_expDate.toString());
		output.write(String.valueOf(card_CVV));
		output.write("credit");
		output.endRecord();
		output.close();
	}
}
