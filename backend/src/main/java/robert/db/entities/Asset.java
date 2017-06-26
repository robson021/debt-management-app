package robert.db.entities;

import java.text.DecimalFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "ASSET")
public class Asset extends BasicEntity {

	@Transient
	private static final DecimalFormat decimalMoneyFormat = new DecimalFormat("#.##");

	@Column(nullable = false)
	private Double amount;

	@Column(nullable = false)
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;

	@Column(/*nullable = false*/)
	private String borrowerEmail;

	@Column(nullable = false)
	private String borrowerName;

	@Column(nullable = false)
	private String borrowerSurname;

	@Column(nullable = false)
	private Long borrowerId;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		String formatted = decimalMoneyFormat.format(amount)
				.replace(',', '.');
		this.amount = Double.valueOf(formatted);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getBorrowerEmail() {
		return borrowerEmail;
	}

	public void setBorrowerEmail(String borrowerEmail) {
		this.borrowerEmail = borrowerEmail;
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

	public long getBorrowerId() {
		return borrowerId;
	}

	public void setBorrowerId(long borrowerId) {
		this.borrowerId = borrowerId;
	}
}
