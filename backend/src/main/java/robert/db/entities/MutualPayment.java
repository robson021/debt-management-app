package robert.db.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "MUTUAL_PAYMENT")
@Getter
@Setter
public class MutualPayment extends BasicEntity {

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "mutualPayment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Fee> payedFees;

    public void addFee(Fee fee) {
        if (payedFees == null) {
            payedFees = Collections.singleton(fee);
        } else {
            payedFees.add(fee);
        }
    }
}
