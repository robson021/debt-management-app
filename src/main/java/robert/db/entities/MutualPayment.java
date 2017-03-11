package robert.db.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "MUTUAL_PAYMENT")
public class MutualPayment extends BasicEntity {

	@Column(nullable = false)
	private Double amount;

	@Column(nullable = false)
	private String description;

	@OneToMany(mappedBy = "mutualPayment", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<Fee> payedFees = null;

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Fee> getPayedFees() {
		return payedFees;
	}

	public void setPayedFees(Set<Fee> payedFees) {
		this.payedFees = payedFees;
	}

	public void addFee(Fee fee) {
		if (payedFees == null) {
			payedFees = new HashSet<>(1);
		}
		payedFees.add(fee);
	}
}
