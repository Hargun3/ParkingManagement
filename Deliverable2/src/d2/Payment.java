package d2;

public class Payment {
	
	private double amountPaid;
	private double bookingCost;
	private double deposit;
	private PaymentMethod method;
	
	public Payment(double amountPaid, double bookingCost, double deposit, PaymentMethod method) {
		this.amountPaid = amountPaid;
		this.bookingCost = bookingCost;
		this.deposit = deposit;
		this.method = method;
	}
	
	public Payment() {
	
	}

	public double getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}
	public double getBookingCost() {
		return bookingCost;
	}
	public void setBookingCost(double bookingCost) {
		this.bookingCost = bookingCost;
	}
	public double getDeposit() {
		return deposit;
	}
	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}
	public PaymentMethod getMethod() {
		return method;
	}
	public void setMethod(PaymentMethod method) {
		this.method = method;
	}
	
}
