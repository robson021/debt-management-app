package robert.web.rest.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class PaymentDTO {

    private long id;

    private long borrowerId;

    private String borrowerName;

    private String borrowerSurname;

    private String owner;

    private BigDecimal amount;

    private String description;

    private Date creationDate;

    private List<FeeDTO> fees;

}
