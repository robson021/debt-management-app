package robert.db.entities;

import javax.persistence.*;

@Entity
@Table(name = "FEE")
public class Fee extends BasicEntity {

	@Column(nullable = false)
	private Double payedFee;

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private User user;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MUTUAL_PAYMENT_ID")
	private MutualPayment mutualPayment;

	public Double getPayedFee() {
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
