package robert.web.rest.dto;

public class PaymentDTO {

    private long borrowerId;

	private String borrowerName;

	private String borrowerSurname;

    private double amount;

    private String description;

	public long getBorrowerId() {
		return borrowerId;
	}

	public void setBorrowerId(long borrowerId) {
		this.borrowerId = borrowerId;
	}

	public String getBorrowerName() {
		return borrowerName;
	}

	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

	public String getBorrowerSurname() {
		return borrowerSurname;
	}

	public void setBorrowerSurname(String borrowerSurname) {
		this.borrowerSurname = borrowerSurname;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
