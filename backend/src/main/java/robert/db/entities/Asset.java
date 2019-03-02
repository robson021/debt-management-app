package robert.db.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ASSET")
@Getter
@Setter
public class Asset extends BasicEntity {

    @Column(nullable = false, columnDefinition = "decimal", precision = 10, scale = 2)
    private BigDecimal amount;

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

}
