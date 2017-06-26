package robert.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "FEE")
public class Fee extends BasicEntity {

	@Column(nullable = false)
	private Double payedFee;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MUTUAL_PAYMENT_ID")
	private MutualPayment mutualPayment;

	public double getPayedFee() {
		return payedFee;
	}

	public void setPayedFee(Double payedFee) {
		this.payedFee = payedFee;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public MutualPayment getMutualPayment() {
		return mutualPayment;
	}

	public void setMutualPayment(MutualPayment mutualPayment) {
		this.mutualPayment = mutualPayment;
	}
}
