 package d2;

public class PaymentAdapter implements PaymentProcessor{
	
	private PaymentMethod method;
	
	public PaymentAdapter(String type) {
		if (type.equalsIgnoreCase("Credit")) {
			method = new Credit();
		} else if (type.equalsIgnoreCase("Debit")) {
			method = new Debit();
		} else if (type.equalsIgnoreCase("Paypal")) {
			method = new Paypal();
		}
	}

	@Override
	public boolean processPayment(double amountPaid) {
		return method.processPayment(amountPaid);
	}

	@Override
	public boolean processDeposit(double deposit) {
		return method.processDeposit(deposit);
	}

	@Override
	public boolean refundDeposit() {
		return method.refundDeposit();
	}
	
}
