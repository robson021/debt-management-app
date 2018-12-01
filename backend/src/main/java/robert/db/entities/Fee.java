package robert.db.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "FEE")
@Getter
@Setter
public class Fee extends BasicEntity {

    @Column(nullable = false)
    private Double payedFee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MUTUAL_PAYMENT_ID")
    private MutualPayment mutualPayment;

}
