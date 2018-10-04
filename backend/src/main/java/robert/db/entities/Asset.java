package robert.db.entities;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.util.Date;

@Entity
@Table(name = "ASSET")
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBorrowerEmail() {
        return borrowerEmail;
    }

    public void setBorrowerEmail(String borrowerEmail) {
        this.borrowerEmail = borrowerEmail;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public String getBorrowerSurname() {
        return borrowerSurname;
    }

    public void setBorrowerSurname(String borrowerSurname) {
        this.borrowerSurname = borrowerSurname;
    }

    public long getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(long borrowerId) {
        this.borrowerId = borrowerId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
