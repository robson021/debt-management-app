package robert.db.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.text.DecimalFormat;

@Entity
@Table(name = "ASSET")
@Getter
@Setter
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
	private String borrowerName;

	@Column(nullable = false)
	private String borrowerSurname;

	@Column(nullable = false)
	private Long borrowerId;

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		String formatted = decimalMoneyFormat.format(amount).replace(',', '.');
		this.amount = Double.valueOf(formatted);
	}
}
