package robert.db.entities;

import javax.persistence.*;
import java.text.DecimalFormat;

@Entity
@Table(name = "ASSET")
public class Asset extends BasicEntity {

	@Transient
	private static final DecimalFormat decimalMoneyFormat = new DecimalFormat("#.##");

	@Column(nullable = false)
	private Double amount;

	@Column(nullable = false)
	private String description;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID")
	private User user;

	@Column(/*nullable = false*/)
	private String borrowerEmail;

	@Column(nullable = false)
	private Long borrowerId;

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = Double.valueOf(decimalMoneyFormat.format(amount)
				.replace(',', '.'));
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

	public Long getBorrowerId() {
		return borrowerId;
	}

	public void setBorrowerId(Long borrowerId) {
		this.borrowerId = borrowerId;
	}
}
