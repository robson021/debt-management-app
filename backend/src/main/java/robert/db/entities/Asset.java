package robert.db.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.util.Date;

@Entity
@Table(name = "ASSET")
@Getter
@Setter
public class Asset extends BasicEntity {

    @Transient
    private static final transient DecimalFormat decimalMoneyFormat = new DecimalFormat("#.##");

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String description;

    @Column
    private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user; // lender

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
}
