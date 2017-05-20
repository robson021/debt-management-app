package robert.db.entities;

import java.text.DecimalFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

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
		String formatted = decimalMoneyFormat.format(amount)
				.replace(',', '.');
		this.amount = Double.valueOf(formatted);
	}
}
