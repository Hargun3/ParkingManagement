package d2;

public interface PaymentMethod {
	
	public boolean processPayment(double amountPaid);
	public boolean processDeposit(double deposit);
	public boolean refundDeposit();

}
