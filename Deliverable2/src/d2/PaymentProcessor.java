package d2;

public interface PaymentProcessor {
	
	public boolean processPayment(double amountPaid);
	public boolean processDeposit(double deposit);
	public boolean refundDeposit();
	
}
