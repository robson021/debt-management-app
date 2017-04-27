package robert.db.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "MUTUAL_PAYMENT")
@Getter
@Setter
public class MutualPayment extends BasicEntity {

	@Column(nullable = false)
	private Double amount;

	@Column(nullable = false)
	private String description;

	@OneToMany(mappedBy = "mutualPayment", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<Fee> payedFees = null;

	public void addFee(Fee fee) {
		if (payedFees == null) {
			payedFees = new HashSet<>(1);
		}
		payedFees.add(fee);
	}
}
